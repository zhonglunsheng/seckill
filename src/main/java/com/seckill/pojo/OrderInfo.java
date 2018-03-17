package com.seckill.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class OrderInfo {
    private Long id;

    private Long userId;

    private Long goodsId;

    private Long deliveryAddrId;

    private String goodsName;

    private Integer orderChannel;

    private Integer status;

    private Date createDate;

    private Date payDate;

    private BigDecimal goodsPrice;

    private Integer goodsCount;

    public OrderInfo(Long id, Long userId, Long goodsId, Long deliveryAddrId, String goodsName, Integer orderChannel, Integer status, Date createDate, Date payDate, BigDecimal goodsPrice, Integer goodsCount) {
        this.id = id;
        this.userId = userId;
        this.goodsId = goodsId;
        this.deliveryAddrId = deliveryAddrId;
        this.goodsName = goodsName;
        this.orderChannel = orderChannel;
        this.status = status;
        this.createDate = createDate;
        this.payDate = payDate;
        this.goodsPrice = goodsPrice;
        this.goodsCount = goodsCount;
    }

    public OrderInfo() {
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

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getDeliveryAddrId() {
        return deliveryAddrId;
    }

    public void setDeliveryAddrId(Long deliveryAddrId) {
        this.deliveryAddrId = deliveryAddrId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Integer getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }
}