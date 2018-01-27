package com.bjsxt.vo;

import java.io.Serializable;

/**
 * 商品信息展示
 * @author zhiduo
 *
 */
public class GoodsVo implements Serializable {
	private static final long serialVersionUID = 2093410085988774611L;
	//商品价格
	private Double marketPrice;
	//商品编号
    private Long goodsId;
    //商品名称
    private String goodsName;
    //商品名称高亮信息
    private String goodsNameHl;
    //商品的图片
    private String originalImg;
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
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
	public String getGoodsNameHl() {
		return goodsNameHl;
	}
	public void setGoodsNameHl(String goodsNameHl) {
		this.goodsNameHl = goodsNameHl;
	}
	public String getOriginalImg() {
		return originalImg;
	}
	public void setOriginalImg(String originalImg) {
		this.originalImg = originalImg;
	}
	@Override
	public String toString() {
		return "GoodsVo [marketPrice=" + marketPrice + ", goodsId=" + goodsId + ", goodsName=" + goodsName
				+ ", goodsNameHl=" + goodsNameHl + ", originalImg=" + originalImg + "]";
	}
    
}
