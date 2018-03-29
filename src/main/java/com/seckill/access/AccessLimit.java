package com.seckill.access;

public @interface AccessLimit {
    int seconds();
    int maxCount();
    boolean needLogin() default true;
}
