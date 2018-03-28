package com.seckill.controller;

import com.seckill.common.ResponseCode;
import com.seckill.common.ServerResponse;
import com.seckill.config.exception.GlobalException;
import com.seckill.pojo.MiaoshaOrder;
import com.seckill.pojo.MiaoshaUser;
import com.seckill.rabbitmq.MQSender;
import com.seckill.rabbitmq.MiaoshaMessage;
import com.seckill.redis.GoodsKey;
import com.seckill.redis.MiaoshaKey;
import com.seckill.redis.RedisService;
import com.seckill.service.IGoodsService;
import com.seckill.service.IMiaoshaService;
import com.seckill.service.IOrderService;
import com.seckill.service.IUserService;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean{

	@Autowired
	IUserService iUserService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	IGoodsService iGoodsService;
	
	@Autowired
	IOrderService iOrderService;

	@Autowired
	IMiaoshaService iMiaoshaService;

	@Autowired
	MQSender mqSender;

	private Map<Long, Boolean> localOverMap =  new HashMap<>();

	/**
	 * 系统初始化
	 * */
	public void afterPropertiesSet() throws Exception {
		ServerResponse<List<GoodsVo>> response = iGoodsService.listGoodsVo();
		if(response.getData() == null) {
			return;
		}
		for(GoodsVo goods : response.getData()) {
			redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), goods.getStockCount());
			localOverMap.put(goods.getId(), false);
		}
	}

	/**
	 * QPS:56
	 * 5000 * 2
	 * */
    @RequestMapping(value = "/{path}/do_miaosha",method = RequestMethod.POST)
	@ResponseBody
    public ServerResponse miaosha(Model model,MiaoshaUser user,
								  @RequestParam("goodsId")long goodsId,
								  @PathVariable("path") String path) {
    	model.addAttribute("user",user);
    	if(user == null) {
    		throw new GlobalException(ResponseCode.SERVER_ERROR);
    	}


		//验证path
		boolean check = iMiaoshaService.checkPath(user, goodsId, path);
		if(!check){
			throw new GlobalException(ResponseCode.REQUEST_ILLEGAL);
		}

		//内存标记，减少redis访问
		boolean over = localOverMap.get(goodsId);
		if(over) {
			redisService.set(MiaoshaKey.isGoodsOver,""+goodsId,"true");
			throw new GlobalException(ResponseCode.MIAO_SHA_OVER);
		}

		//预减库存
		long stock = redisService.get(GoodsKey.getMiaoshaGoodsStock, ""+goodsId,Long.class);//10
		if(stock <= 0) {
			localOverMap.put(goodsId, true);
			throw  new GlobalException(ResponseCode.MIAO_SHA_OVER);
		}

    	//判断是否已经秒杀到了
    	MiaoshaOrder order = iOrderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		throw new GlobalException(ResponseCode.REPEATE_MIAOSHA);
    	}

		//入队
		MiaoshaMessage mm = new MiaoshaMessage();
		mm.setUser(user);
		mm.setGoodsId(goodsId);
		mqSender.sendMiaoshaMessage(mm);
		return ServerResponse.createBySuccess();//排队中
    }

	/**
	 * orderId：成功
	 * -1：秒杀失败
	 * 0： 排队中
	 * */
	@RequestMapping(value="/result", method = RequestMethod.GET)
	@ResponseBody
	public ServerResponse<Long> miaoshaResult(Model model,MiaoshaUser user,
									  @RequestParam("goodsId")long goodsId) {
		model.addAttribute("user", user);
		if(user == null) {
			throw new GlobalException(ResponseCode.SESSION_ERROR);
		}
		long result  =iOrderService.getMiaoshaResult(user.getId(), goodsId);
		return ServerResponse.createBySuccess(result);
	}

    @RequestMapping("/create")
	@ResponseBody
    public ServerResponse createUser(){
    	iUserService.createUser();
    	return ServerResponse.createBySuccess();
	}

	/**
	 * 获取验证码
	 * @param response
	 * @param user
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value="/verifyCode", method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> getMiaoshaVerifyCod(HttpServletResponse response, MiaoshaUser user,
													  @RequestParam("goodsId")long goodsId) {
		if(user == null) {
			throw new GlobalException(ResponseCode.SESSION_ERROR);
		}
		try {
			BufferedImage image  = iMiaoshaService.createVerifyCode(user, goodsId);
			OutputStream out = response.getOutputStream();
			ImageIO.write(image, "JPEG", out);
			out.flush();
			out.close();
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			throw new GlobalException(ResponseCode.MIAOSHA_FAIL);
		}
	}

	@RequestMapping(value="/path", method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> getMiaoshaPath(HttpServletRequest request, MiaoshaUser user,
												 @RequestParam("goodsId")long goodsId,
												 @RequestParam(value="verifyCode", defaultValue="0")int verifyCode
	) {
		if(user == null) {
			throw new GlobalException(ResponseCode.SESSION_ERROR);
		}
		boolean check = iMiaoshaService.checkVerifyCode(user, goodsId, verifyCode);
		if(!check) {
			throw new GlobalException(ResponseCode.VERIFY_CODE_ERROR);
		}
		String path  =iMiaoshaService.createMiaoshaPath(user, goodsId);
		return ServerResponse.createBySuccess(path);
	}
}
