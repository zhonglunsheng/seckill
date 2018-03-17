package com.seckill.dao;

import com.seckill.pojo.MiaoShaUser;

public interface MiaoShaUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiaoShaUser record);

    int insertSelective(MiaoShaUser record);

    MiaoShaUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiaoShaUser record);

    int updateByPrimaryKey(MiaoShaUser record);
}