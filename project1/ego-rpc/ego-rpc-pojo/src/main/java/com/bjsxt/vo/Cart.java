package com.bjsxt.vo;

import java.io.Serializable;
import java.util.Date;

public class Cart implements Serializable {
	private static final long serialVersionUID = -6333730476414328290L;
	//商品编号
	private Long goodsId;
	//商品名称
	private String goodsName;
	//商品图片
	private String goodsImage;
	//商品价格
	private Double goodsPrice;
	//添加日期
	private Date addTime;
	//商品数量
	private Integer goodsNum;
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public Double getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	@Override
	public String toString() {
		return "Cart [goodsId=" + goodsId + ", goodsName=" + goodsName + ", goodsImage=" + goodsImage
				+ ", goodsPrice=" + goodsPrice + ", addTime=" + addTime + ", goodsNum=" + goodsNum + "]";
	}
	
	
}
