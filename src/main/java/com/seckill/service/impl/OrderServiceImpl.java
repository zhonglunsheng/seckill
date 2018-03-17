package com.seckill.service.impl;

import java.util.Date;
import java.util.Random;

import com.seckill.dao.GoodsMapper;
import com.seckill.dao.MiaoShaOrderMapper;
import com.seckill.dao.OrderInfoMapper;
import com.seckill.pojo.MiaoShaOrder;
import com.seckill.pojo.MiaoShaUser;
import com.seckill.pojo.OrderInfo;
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

	@Override
	public MiaoShaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
		return miaoShaOrderMapper.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
	}

	@Transactional
	public OrderInfo createOrder(MiaoShaUser user, GoodsVo goods) {
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
		MiaoShaOrder miaoShaOrder = new MiaoShaOrder();
		miaoShaOrder.setId(orderInfo.getId());
		miaoShaOrder.setGoodsId(goods.getId());
		miaoShaOrder.setOrderId(orderInfo.getId());
		miaoShaOrder.setUserId(user.getId());
		miaoShaOrderMapper.insert(miaoShaOrder);
		return orderInfo;
	}

	@Transactional
	@Override
	public OrderInfo miaosha(MiaoShaUser user, GoodsVo goods) {
		//减库存 下订单 写入秒杀订单

		boolean result = iGoodsService.reduceStock(goods);

		if (result){
			return createOrder(user, goods);
		}else{
			return null;
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
