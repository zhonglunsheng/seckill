package com.seckill.rabbitmq;

import com.seckill.common.ServerResponse;
import com.seckill.pojo.MiaoshaOrder;
import com.seckill.pojo.MiaoshaUser;
import com.seckill.redis.RedisService;
import com.seckill.service.IGoodsService;
import com.seckill.service.IOrderService;
import com.seckill.util.JsonUtil;
import com.seckill.vo.GoodsDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MQReceiver {

		private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
		
		@Autowired
		RedisService redisService;
		
		@Autowired
		IGoodsService iGoodsService;
		
		@Autowired
	    IOrderService iOrderService;

		
		@RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
		public void receive(String message) {
			log.info("receive message:"+message);
			MiaoshaMessage mm  = JsonUtil.string2Obj(message, MiaoshaMessage.class);
			MiaoshaUser user = mm.getUser();
			long goodsId = mm.getGoodsId();
			
			ServerResponse<GoodsDetailVo> response = iGoodsService.getGoodsVoByGoodsId(goodsId);
	    	int stock = response.getData().getGoods().getStockCount();
	    	if(stock <= 0) {
	    		return;
	    	}
	    	//判断是否已经秒杀到了
			MiaoshaOrder order = iOrderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
	    	if(order != null) {
	    		return;
	    	}
	    	//减库存 下订单 写入秒杀订单
			iOrderService.miaosha(user, response.getData().getGoods());
		}
		
}
