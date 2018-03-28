package com.seckill.redis;

public class ServerResponseKey extends BasePrefix {

    public ServerResponseKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static ServerResponseKey responseKey = new ServerResponseKey(60,"responseKey");
}
