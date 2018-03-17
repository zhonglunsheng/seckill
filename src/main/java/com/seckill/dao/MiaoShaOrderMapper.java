package com.seckill.dao;

import com.seckill.pojo.MiaoShaOrder;
import org.apache.ibatis.annotations.Param;

public interface MiaoShaOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiaoShaOrder record);

    int insertSelective(MiaoShaOrder record);

    MiaoShaOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiaoShaOrder record);

    int updateByPrimaryKey(MiaoShaOrder record);

    MiaoShaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId")Long userId,@Param("goodsId")Long goodsId);

}