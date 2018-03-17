package com.seckill.controller;

import com.seckill.common.Const;
import com.seckill.common.ResponseCode;
import com.seckill.common.ServerResponse;
import com.seckill.pojo.MiaoShaOrder;
import com.seckill.pojo.MiaoShaUser;
import com.seckill.pojo.OrderInfo;
import com.seckill.redis.RedisService;
import com.seckill.service.IGoodsService;
import com.seckill.service.IOrderService;
import com.seckill.service.IUserService;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

	@Autowired
	IUserService iUserService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	IGoodsService iGoodsService;
	
	@Autowired
	IOrderService iOrderService;

	
	/**
	 * QPS:56
	 * 5000 * 2
	 * */
    @RequestMapping("/do_miaosha")
    public String list(Model model,MiaoShaUser user,
    		@RequestParam("goodsId")long goodsId) {
    	if(user == null) {
    		return "login";
    	}
		model.addAttribute("user", user);
    	//判断库存
    	GoodsVo goods = iGoodsService.getGoodsVoByGoodsId(goodsId);
    	int stock = goods.getStockCount();
    	if(stock <= 0) {
    		model.addAttribute("errmsg", ResponseCode.MIAO_SHA_OVER.getDesc());
    		return "miaosha_fail";
    	}
    	//判断是否已经秒杀到了
    	MiaoShaOrder order = iOrderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
    	if(order != null) {
    		model.addAttribute("errmsg", ResponseCode.REPEATE_MIAOSHA.getDesc());
    		return "miaosha_fail";
    	}
    	//减库存 下订单 写入秒杀订单
    	OrderInfo orderInfo = iOrderService.miaosha(user, goods);
    	if (orderInfo == null){
			return "miaosha_fail";
		}
    	model.addAttribute("orderInfo", orderInfo);
    	model.addAttribute("goods", goods);
        return "order_detail";
    }

    @RequestMapping("/create")
	@ResponseBody
    public ServerResponse createUser(){
    	iUserService.createUser();
    	return ServerResponse.createBySuccess();
	}
}
