package com.bjsxt.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderGoods implements Serializable {
    /** 表id自增  rec_id **/
    private Integer recId;

    /** 订单id  order_id **/
    private Integer orderId;

    /** 商品id  goods_id **/
    private Integer goodsId;

    /** 视频名称  goods_name **/
    private String goodsName;

    /** 商品货号  goods_sn **/
    private String goodsSn;

    /** 购买数量  goods_num **/
    private Short goodsNum;

    /** 市场价  market_price **/
    private BigDecimal marketPrice;

    /** 本店价  goods_price **/
    private BigDecimal goodsPrice;

    /** 商品成本价  cost_price **/
    private BigDecimal costPrice;

    /** 会员折扣价  member_goods_price **/
    private BigDecimal memberGoodsPrice;

    /** 购买商品赠送积分  give_integral **/
    private Integer giveIntegral;

    /** 商品规格key  spec_key **/
    private String specKey;

    /** 规格对应的中文名字  spec_key_name **/
    private String specKeyName;

    /** 条码  bar_code **/
    private String barCode;

    /** 是否评价  is_comment **/
    private Byte isComment;

    /** 0 普通订单,1 限时抢购, 2 团购 , 3 促销优惠  prom_type **/
    private Byte promType;

    /** 活动id  prom_id **/
    private Integer promId;

    /** 0未发货，1已发货，2已换货，3已退货  is_send **/
    private Byte isSend;

    /** 发货单ID  delivery_id **/
    private Integer deliveryId;

    /** sku  sku **/
    private String sku;

    /**   tableName: t_order_goods   **/
    private static final long serialVersionUID = 1L;

    /**   表id自增  rec_id   **/
    public Integer getRecId() {
        return recId;
    }

    /**   表id自增  rec_id   **/
    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    /**   订单id  order_id   **/
    public Integer getOrderId() {
        return orderId;
    }

    /**   订单id  order_id   **/
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**   商品id  goods_id   **/
    public Integer getGoodsId() {
        return goodsId;
    }

    /**   商品id  goods_id   **/
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**   视频名称  goods_name   **/
    public String getGoodsName() {
        return goodsName;
    }

    /**   视频名称  goods_name   **/
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    /**   商品货号  goods_sn   **/
    public String getGoodsSn() {
        return goodsSn;
    }

    /**   商品货号  goods_sn   **/
    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn == null ? null : goodsSn.trim();
    }

    /**   购买数量  goods_num   **/
    public Short getGoodsNum() {
        return goodsNum;
    }

    /**   购买数量  goods_num   **/
    public void setGoodsNum(Short goodsNum) {
        this.goodsNum = goodsNum;
    }

    /**   市场价  market_price   **/
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**   市场价  market_price   **/
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**   本店价  goods_price   **/
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    /**   本店价  goods_price   **/
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**   商品成本价  cost_price   **/
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**   商品成本价  cost_price   **/
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**   会员折扣价  member_goods_price   **/
    public BigDecimal getMemberGoodsPrice() {
        return memberGoodsPrice;
    }

    /**   会员折扣价  member_goods_price   **/
    public void setMemberGoodsPrice(BigDecimal memberGoodsPrice) {
        this.memberGoodsPrice = memberGoodsPrice;
    }

    /**   购买商品赠送积分  give_integral   **/
    public Integer getGiveIntegral() {
        return giveIntegral;
    }

    /**   购买商品赠送积分  give_integral   **/
    public void setGiveIntegral(Integer giveIntegral) {
        this.giveIntegral = giveIntegral;
    }

    /**   商品规格key  spec_key   **/
    public String getSpecKey() {
        return specKey;
    }

    /**   商品规格key  spec_key   **/
    public void setSpecKey(String specKey) {
        this.specKey = specKey == null ? null : specKey.trim();
    }

    /**   规格对应的中文名字  spec_key_name   **/
    public String getSpecKeyName() {
        return specKeyName;
    }

    /**   规格对应的中文名字  spec_key_name   **/
    public void setSpecKeyName(String specKeyName) {
        this.specKeyName = specKeyName == null ? null : specKeyName.trim();
    }

    /**   条码  bar_code   **/
    public String getBarCode() {
        return barCode;
    }

    /**   条码  bar_code   **/
    public void setBarCode(String barCode) {
        this.barCode = barCode == null ? null : barCode.trim();
    }

    /**   是否评价  is_comment   **/
    public Byte getIsComment() {
        return isComment;
    }

    /**   是否评价  is_comment   **/
    public void setIsComment(Byte isComment) {
        this.isComment = isComment;
    }

    /**   0 普通订单,1 限时抢购, 2 团购 , 3 促销优惠  prom_type   **/
    public Byte getPromType() {
        return promType;
    }

    /**   0 普通订单,1 限时抢购, 2 团购 , 3 促销优惠  prom_type   **/
    public void setPromType(Byte promType) {
        this.promType = promType;
    }

    /**   活动id  prom_id   **/
    public Integer getPromId() {
        return promId;
    }

    /**   活动id  prom_id   **/
    public void setPromId(Integer promId) {
        this.promId = promId;
    }

    /**   0未发货，1已发货，2已换货，3已退货  is_send   **/
    public Byte getIsSend() {
        return isSend;
    }

    /**   0未发货，1已发货，2已换货，3已退货  is_send   **/
    public void setIsSend(Byte isSend) {
        this.isSend = isSend;
    }

    /**   发货单ID  delivery_id   **/
    public Integer getDeliveryId() {
        return deliveryId;
    }

    /**   发货单ID  delivery_id   **/
    public void setDeliveryId(Integer deliveryId) {
        this.deliveryId = deliveryId;
    }

    /**   sku  sku   **/
    public String getSku() {
        return sku;
    }

    /**   sku  sku   **/
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recId=").append(recId);
        sb.append(", orderId=").append(orderId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsSn=").append(goodsSn);
        sb.append(", goodsNum=").append(goodsNum);
        sb.append(", marketPrice=").append(marketPrice);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", costPrice=").append(costPrice);
        sb.append(", memberGoodsPrice=").append(memberGoodsPrice);
        sb.append(", giveIntegral=").append(giveIntegral);
        sb.append(", specKey=").append(specKey);
        sb.append(", specKeyName=").append(specKeyName);
        sb.append(", barCode=").append(barCode);
        sb.append(", isComment=").append(isComment);
        sb.append(", promType=").append(promType);
        sb.append(", promId=").append(promId);
        sb.append(", isSend=").append(isSend);
        sb.append(", deliveryId=").append(deliveryId);
        sb.append(", sku=").append(sku);
        sb.append("]");
        return sb.toString();
    }
}