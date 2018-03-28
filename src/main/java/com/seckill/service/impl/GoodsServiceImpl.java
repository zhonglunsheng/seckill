package com.seckill.service.impl;

import com.seckill.common.ServerResponse;
import com.seckill.dao.GoodsMapper;
import com.seckill.dao.MiaoShaGoodsMapper;
import com.seckill.service.IGoodsService;
import com.seckill.vo.GoodsDetailVo;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("iGoodsService")
public class GoodsServiceImpl implements IGoodsService{

    @Autowired
    private MiaoShaGoodsMapper miaoShaGoodsMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public ServerResponse<List<GoodsVo>> listGoodsVo(){
        return ServerResponse.createBySuccess(miaoShaGoodsMapper.listGoodsVo());
    }

    @Override
    public ServerResponse<GoodsDetailVo> getGoodsVoByGoodsId(long goodsId) {
        GoodsVo goods = miaoShaGoodsMapper.getGoodsVoByGoodsId(goodsId);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){//秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        GoodsDetailVo goodsDetailVo = assembleGoodsDetailVo(goods,miaoshaStatus,remainSeconds);
        return  ServerResponse.createBySuccess(goodsDetailVo);
    }

    private GoodsDetailVo assembleGoodsDetailVo(GoodsVo goodsVo, int miaoshaStatus, int remainSeconds) {
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setGoods(goodsVo);
        goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
        goodsDetailVo.setRemainSeconds(remainSeconds);
        return goodsDetailVo;
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
