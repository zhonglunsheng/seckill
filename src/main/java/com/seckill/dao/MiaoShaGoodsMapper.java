package com.seckill.dao;

import com.seckill.pojo.MiaoshaGoods;
import com.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MiaoShaGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiaoshaGoods record);

    int insertSelective(MiaoshaGoods record);

    MiaoshaGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiaoshaGoods record);

    int updateByPrimaryKey(MiaoshaGoods record);

    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") Long goodsId);

    int updateMiaoShaGoodsStock(@Param("stockCount")int stockCount,@Param("goodsId")Long goodsId);

    int reduceStock(@Param("goodsId")Long goodsId);

}