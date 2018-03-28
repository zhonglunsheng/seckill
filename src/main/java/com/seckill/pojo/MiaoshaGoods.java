package com.seckill.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class MiaoshaGoods {
    private Long id;

    private Long goodsId;

    private Integer stockCount;

    private Date endDate;

    private Date startDate;

    private Long miaoshaPrice;

    private BigDecimal goodsPrice;

    public MiaoshaGoods(Long id, Long goodsId, Integer stockCount, Date endDate, Date startDate, Long miaoshaPrice, BigDecimal goodsPrice) {
        this.id = id;
        this.goodsId = goodsId;
        this.stockCount = stockCount;
        this.endDate = endDate;
        this.startDate = startDate;
        this.miaoshaPrice = miaoshaPrice;
        this.goodsPrice = goodsPrice;
    }

    public MiaoshaGoods() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Long getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(Long miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
}