package com.seckill.redis;

public class GoodsKey extends BasePrefix{

    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    //商品列表键
    public static GoodsKey goodsList = new GoodsKey(60,"goodsList");
    public static GoodsKey goodsDetail = new GoodsKey(60,"goodsDetail");
    public static GoodsKey getMiaoshaGoodsStock= new GoodsKey(0, "goodsStock");
}
