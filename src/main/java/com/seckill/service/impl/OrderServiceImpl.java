package com.seckill.service.impl;

import java.util.Date;
import java.util.Random;

import com.seckill.dao.MiaoShaOrderMapper;
import com.seckill.dao.OrderInfoMapper;
import com.seckill.pojo.MiaoshaOrder;
import com.seckill.pojo.MiaoshaUser;
import com.seckill.pojo.OrderInfo;
import com.seckill.redis.GoodsKey;
import com.seckill.redis.MiaoshaKey;
import com.seckill.redis.RedisService;
import com.seckill.service.IGoodsService;
import com.seckill.service.IOrderService;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service("iOrederService")
public class OrderServiceImpl implements IOrderService{
	
	@Autowired
	OrderInfoMapper orderInfoMapper;

	@Autowired
	MiaoShaOrderMapper miaoShaOrderMapper;

	@Autowired
	IGoodsService iGoodsService;

	@Autowired
	RedisService redisService;

	@Override
	public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		return miaoShaOrderMapper.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
	}

	@Transactional
	public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
		//生成商品订单
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(createOrderId());
		orderInfo.setCreateDate(new Date());
		orderInfo.setDeliveryAddrId(0L);
		orderInfo.setGoodsCount(1);
		orderInfo.setGoodsId(goods.getId());
		orderInfo.setGoodsName(goods.getGoodsName());
		orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
		orderInfo.setOrderChannel(1);
		orderInfo.setStatus(0);
		orderInfo.setUserId(user.getId());
		orderInfoMapper.insert(orderInfo);

		//生成秒杀订单
		MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
		miaoshaOrder.setId(orderInfo.getId());
		miaoshaOrder.setGoodsId(goods.getId());
		miaoshaOrder.setOrderId(orderInfo.getId());
		miaoshaOrder.setUserId(user.getId());
		miaoShaOrderMapper.insert(miaoshaOrder);
		return orderInfo;
	}

	@Transactional
	@Override
	public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
		//减库存 下订单 写入秒杀订单
		boolean result = iGoodsService.reduceStock(goods);
		redisService.decr(GoodsKey.getMiaoshaGoodsStock,""+goods.getId());
		if (result){
			return createOrder(user, goods);
		}else{
			return null;
		}
	}

	@Override
	public long getMiaoshaResult(Long userId, Long goodsId){
		MiaoshaOrder miaoshaOrder = miaoShaOrderMapper.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
		if (miaoshaOrder != null){
			//秒杀成功
			return miaoshaOrder.getOrderId();
		}else{
			boolean isOver = redisService.exists(MiaoshaKey.isGoodsOver,""+goodsId);
			if (isOver){
				return -1;
			}else{
				return 0;
			}
		}
	}

	/**
	 * 创建订单号
	 * @return
	 */
	private Long createOrderId(){
		Random random = new Random();
		int mod = random.nextInt(10)+1;
		return System.currentTimeMillis()+System.currentTimeMillis()%mod;
	}

	
}
