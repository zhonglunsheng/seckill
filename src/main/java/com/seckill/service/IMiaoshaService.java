package com.seckill.service;

import com.seckill.pojo.MiaoshaUser;

import java.awt.image.BufferedImage;

public interface IMiaoshaService {
    BufferedImage createVerifyCode(MiaoshaUser user, long goodsId);

    boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode);

    String createMiaoshaPath(MiaoshaUser user, long goodsId);

    boolean checkPath(MiaoshaUser user, long goodsId, String path);
}
