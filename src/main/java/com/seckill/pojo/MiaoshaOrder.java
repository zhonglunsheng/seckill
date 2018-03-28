package com.seckill.pojo;

public class MiaoshaOrder {
    private Long id;

    private Long userId;

    private Long orderId;

    private Long goodsId;

    public MiaoshaOrder(Long id, Long userId, Long orderId, Long goodsId) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.goodsId = goodsId;
    }

    public MiaoshaOrder() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}