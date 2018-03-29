package com.seckill.access;

import com.seckill.pojo.MiaoshaUser;

public class UserContext {

    private static ThreadLocal<MiaoshaUser> threadLocal = new ThreadLocal<>();

    private MiaoshaUser user;

    public static MiaoshaUser getUser() {
        return threadLocal.get();
    }

    public static void setUser(MiaoshaUser user) {
        threadLocal.set(user);
    }
}
