package com.seckill.service.impl;

import com.seckill.common.Const;
import com.seckill.common.ResponseCode;
import com.seckill.common.ServerResponse;
import com.seckill.config.exception.GlobalException;
import com.seckill.dao.MiaoShaUserMapper;
import com.seckill.pojo.MiaoshaUser;
import com.seckill.redis.MiaoshaUserKey;
import com.seckill.redis.RedisService;
import com.seckill.service.IUserService;
import com.seckill.util.MD5Util;
import com.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService{

    @Autowired
    MiaoShaUserMapper miaoShaUserMapper;

    @Autowired
    RedisService redisService;

    /**
     * 用户登录
     * @param request
     * @param response
     * @param loginVo
     * @return
     */
    @Override
    public ServerResponse login(HttpServletRequest request,HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null) {
            throw new GlobalException(ResponseCode.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.valueOf(mobile));
        if(user == null) {
            throw new GlobalException(ResponseCode.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.MD5EncodeUtf8(formPass,saltDB);
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(ResponseCode.PASSWORD_ERROR);
        }

        String token = UUID.randomUUID().toString().replace("-","");
        addCookie(response,token,user);

        return ServerResponse.createBySuccess();
    }



    /**
     * 获取User 并重置cookie时间
     * @param response
     * @param token
     * @return
     */
    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token){
        if (StringUtils.isEmpty(token)){
            return null;
        }

        MiaoshaUser user = redisService.get(MiaoshaUserKey.token,token,MiaoshaUser.class);
        if (user != null){
            addCookie(response,token,user);
        }
        return user;
    }


    /**
     * 获取用户信息
     * @param id
     * @return
     */
    private MiaoshaUser getById(Long id){
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById,""+id,MiaoshaUser.class);
        if (user == null){
            user = miaoShaUserMapper.selectByPrimaryKey(id);
            if (user != null)
            redisService.set(MiaoshaUserKey.getById,""+id,user);
        }
        return user;
    }

    /**
     * 添加Cookie
     * @param response
     * @param token
     * @param user
     */
    private void addCookie(HttpServletResponse response, String token,MiaoshaUser user){
        redisService.set(MiaoshaUserKey.token,token,user);

        Cookie cookie = new Cookie(Const.COOKIE_NAME_TOKEN,token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }


    /**
     * 创建用户用于压测
     */
    @Transactional
    @Override
    public void createUser(){
        int number = 5000;
        Long id = 17886378987L;
        MiaoshaUser user = null;
        File file = new File("G:/user.txt");
        FileWriter writer = null;
        if (file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
            writer = new FileWriter(file);
            for (int i = 0; i < number; i++) {
                user = new MiaoshaUser();
                user.setId(id+i);
                user.setNickname("lipop");
                user.setSalt("1a2a3a");
                user.setPassword(MD5Util.MD5EncodeUtf8("284F221CD71ADBC73DF7B8D012AB9C1C",user.getSalt()));
                miaoShaUserMapper.insert(user);
                String token = UUID.randomUUID().toString().replace("-","");
                redisService.set(MiaoshaUserKey.token,token,user);
                writer.write(user.getId()+","+token+"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }

}
