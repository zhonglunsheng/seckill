package com.seckill.service;

import com.seckill.common.ServerResponse;
import com.seckill.vo.GoodsDetailVo;
import com.seckill.vo.GoodsVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IGoodsService {
    /**
     * 获取秒杀商品列表
     * @return
     */
    ServerResponse<List<GoodsVo>> listGoodsVo();

    /**
     * 获取秒杀商品信息
     * @param goodsId
     * @return
     */
    ServerResponse<GoodsDetailVo> getGoodsVoByGoodsId(long goodsId);

    /**
     * 减少秒杀商品库存
     * @param goods
     */
    @Transactional
    boolean reduceStock(GoodsVo goods);
}
