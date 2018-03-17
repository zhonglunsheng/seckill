package com.seckill.dao;

import com.seckill.pojo.MiaoShaGoods;
import com.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MiaoShaGoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiaoShaGoods record);

    int insertSelective(MiaoShaGoods record);

    MiaoShaGoods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiaoShaGoods record);

    int updateByPrimaryKey(MiaoShaGoods record);

    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") Long goodsId);

    int updateMiaoShaGoodsStock(@Param("stockCount")int stockCount,@Param("goodsId")Long goodsId);

    int reduceStock(@Param("goodsId")Long goodsId);

}