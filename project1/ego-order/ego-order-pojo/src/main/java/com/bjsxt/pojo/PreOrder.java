package com.bjsxt.pojo;

import java.io.Serializable;
import java.util.Date;

public class PreOrder implements Serializable {
	private static final long serialVersionUID = 9173805030353471900L;
	//商品编号
	private Long goodsId;
	//商品名称
	private String goodsName;
	//商品图片
	private String goodsImage;
	//商品价格
	private Double goodsPrice;
	//商品数量
	private Integer goodsNum;
	//提交时间
	private Date submitTime;
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
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(Integer goodsNum) {
		this.goodsNum = goodsNum;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	@Override
	public String toString() {
		return "PreOrder [goodsId=" + goodsId + ", goodsName=" + goodsName + ", goodsImage=" + goodsImage
				+ ", goodsPrice=" + goodsPrice + ", goodsNum=" + goodsNum + ", submitTime=" + submitTime + "]";
	}
}
