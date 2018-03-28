package com.seckill.dao;

import com.seckill.pojo.MiaoshaOrder;
import org.apache.ibatis.annotations.Param;

public interface MiaoShaOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiaoshaOrder record);

    int insertSelective(MiaoshaOrder record);

    MiaoshaOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiaoshaOrder record);

    int updateByPrimaryKey(MiaoshaOrder record);

    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId")Long userId, @Param("goodsId")Long goodsId);

}