package com.seckill.vo;

import com.seckill.pojo.Goods;

import java.math.BigDecimal;
import java.util.Date;


public class GoodsVo {
	private Long id;
	private String goodsName;
	private String goodsTitle;
	private String goodsImg;
	private String goodsDetail;
	private Integer goodsStock;
	private BigDecimal miaoshaPrice;
	private BigDecimal goodsPrice;
	private Integer stockCount;
	private Date startDate;
	private Date endDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public String getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(String goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public Integer getGoodsStock() {
		return goodsStock;
	}

	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}

	public BigDecimal getMiaoshaPrice() {
		return miaoshaPrice;
	}

	public void setMiaoshaPrice(BigDecimal miaoshaPrice) {
		this.miaoshaPrice = miaoshaPrice;
	}

	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public GoodsVo(Long id, String goodsName, String goodsTitle, String goodsImg, String goodsDetail, Integer goodsStock, BigDecimal miaoshaPrice, BigDecimal goodsPrice, Integer stockCount, Date startDate, Date endDate) {
		this.id = id;
		this.goodsName = goodsName;
		this.goodsTitle = goodsTitle;
		this.goodsImg = goodsImg;
		this.goodsDetail = goodsDetail;
		this.goodsStock = goodsStock;
		this.miaoshaPrice = miaoshaPrice;
		this.goodsPrice = goodsPrice;
		this.stockCount = stockCount;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public GoodsVo() {
	}
}
