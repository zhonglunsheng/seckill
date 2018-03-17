package com.seckill.controller;

import com.seckill.common.Const;
import com.seckill.common.ServerResponse;
import com.seckill.redis.RedisPoolFactory;
import com.seckill.redis.RedisService;
import com.seckill.service.IUserService;
import com.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
    RedisPoolFactory redisPoolFactory;

	@Autowired
	RedisService redisService;

	@Autowired
	IUserService iUserService;


    @RequestMapping("/to_login")
    public String toLogin() {
       return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public ServerResponse do_login(HttpServletRequest request,HttpServletResponse response, @Valid LoginVo loginVo){
        return iUserService.login(request,response,loginVo);
    }

}