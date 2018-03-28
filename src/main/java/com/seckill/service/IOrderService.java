package com.seckill.service;

import com.seckill.pojo.MiaoshaOrder;
import com.seckill.pojo.MiaoshaUser;
import com.seckill.pojo.OrderInfo;
import com.seckill.vo.GoodsVo;
import org.springframework.transaction.annotation.Transactional;

public interface IOrderService {
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId);

    @Transactional
    OrderInfo miaosha(MiaoshaUser user, GoodsVo goods);

    long getMiaoshaResult(Long userId, Long goodsId);
}
