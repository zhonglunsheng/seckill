package com.seckill.service;

import com.seckill.common.ServerResponse;
import com.seckill.pojo.MiaoShaUser;
import com.seckill.vo.LoginVo;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IUserService {

    ServerResponse login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo);

    MiaoShaUser getByToken(HttpServletResponse response, String token);

    @Transactional
    void createUser();
}
