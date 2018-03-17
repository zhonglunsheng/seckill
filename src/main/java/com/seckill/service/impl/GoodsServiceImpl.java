package com.seckill.service.impl;

import com.seckill.common.ServerResponse;
import com.seckill.dao.GoodsMapper;
import com.seckill.dao.MiaoShaGoodsMapper;
import com.seckill.pojo.Goods;
import com.seckill.pojo.MiaoShaGoods;
import com.seckill.service.IGoodsService;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("iGoodsService")
public class GoodsServiceImpl implements IGoodsService{

    @Autowired
    private MiaoShaGoodsMapper miaoShaGoodsMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public ServerResponse listGoodsVo(){
        return ServerResponse.createBySuccess(miaoShaGoodsMapper.listGoodsVo());
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return miaoShaGoodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    @Override
    @Transactional
    public boolean reduceStock(GoodsVo goodsVo) {

        //减少商品库存
        int result = goodsMapper.reduceStock(goodsVo.getId());

        int msresult = miaoShaGoodsMapper.reduceStock(goodsVo.getId());
        if (result>0 && msresult >0){
            return true;
        }
        return false;
    }

}
