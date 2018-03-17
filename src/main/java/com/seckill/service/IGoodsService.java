package com.seckill.service;

import com.seckill.common.ServerResponse;
import com.seckill.vo.GoodsVo;
import org.springframework.transaction.annotation.Transactional;

public interface IGoodsService {
    /**
     * 获取秒杀商品列表
     * @return
     */
    ServerResponse listGoodsVo();

    /**
     * 获取秒杀商品信息
     * @param goodsId
     * @return
     */
    GoodsVo getGoodsVoByGoodsId(long goodsId);

    /**
     * 减少秒杀商品库存
     * @param goods
     */
    @Transactional
    boolean reduceStock(GoodsVo goods);
}
