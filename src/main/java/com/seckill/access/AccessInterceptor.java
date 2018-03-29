package com.seckill.access;

import com.seckill.common.Const;
import com.seckill.common.ResponseCode;
import com.seckill.config.exception.GlobalException;
import com.seckill.pojo.MiaoshaUser;
import com.seckill.redis.AccessKey;
import com.seckill.redis.RedisService;
import com.seckill.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class AccessInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    IUserService iUserService;

    @Autowired
    RedisService redisService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        MiaoshaUser user = getUser(request,response);
        UserContext.setUser(user);
        if (handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            AccessLimit accessLimit = method.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null){
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();

            if (needLogin){
                if (user == null){
                    throw new GlobalException(ResponseCode.NEED_LOGIN);
                }
            }
            AccessKey accessKey = AccessKey.withExpire(maxCount);
            Integer count = redisService.get(accessKey,key,Integer.class);
            if (count == null){
                redisService.set(accessKey,key,1);
            }else if (count < maxCount){
                redisService.incr(accessKey,key);
            }else{
                throw new GlobalException(ResponseCode.ACCESS_LIMIT_REACHED);
            }
        }
        return true;
    }

    private MiaoshaUser getUser(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(Const.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, Const.COOKIE_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return null;
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return iUserService.getByToken(response, token);
    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
