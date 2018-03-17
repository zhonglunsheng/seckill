package com.seckill.service;

import com.seckill.pojo.MiaoShaOrder;
import com.seckill.pojo.MiaoShaUser;
import com.seckill.pojo.OrderInfo;
import com.seckill.vo.GoodsVo;
import org.springframework.transaction.annotation.Transactional;

public interface IOrderService {
    MiaoShaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId);

    @Transactional
    OrderInfo miaosha(MiaoShaUser user, GoodsVo goods);
}
