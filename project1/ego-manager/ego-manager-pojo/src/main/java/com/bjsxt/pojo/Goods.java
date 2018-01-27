package com.bjsxt.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class Goods implements Serializable {
    /** 商品id  goods_id **/
    private Integer goodsId;

    /** 分类id  cat_id **/
    private Integer catId;

    /** 扩展分类id  extend_cat_id **/
    private Integer extendCatId;

    /** 商品编号  goods_sn **/
    private String goodsSn;

    /** 商品名称  goods_name **/
    private String goodsName;

    /** 点击数  click_count **/
    private Integer clickCount;

    /** 品牌id  brand_id **/
    private Short brandId;

    /** 库存数量  store_count **/
    private Short storeCount;

    /** 商品评论数  comment_count **/
    private Short commentCount;

    /** 商品重量克为单位  weight **/
    private Integer weight;

    /** 市场价  market_price **/
    private BigDecimal marketPrice;

    /** 本店价  shop_price **/
    private BigDecimal shopPrice;

    /** 商品成本价  cost_price **/
    private BigDecimal costPrice;

    /** 商品关键词  keywords **/
    private String keywords;

    /** 商品简单描述  goods_remark **/
    private String goodsRemark;

    /** 商品上传原始图  original_img **/
    private String originalImg;

    /** 是否为实物  is_real **/
    private Byte isReal;

    /** 是否上架  is_on_sale **/
    private Byte isOnSale;

    /** 是否包邮0否1是  is_free_shipping **/
    private Byte isFreeShipping;

    /** 商品上架时间  on_time **/
    private Integer onTime;

    /** 商品排序  sort **/
    private Short sort;

    /** 是否推荐  is_recommend **/
    private Byte isRecommend;

    /** 是否新品  is_new **/
    private Byte isNew;

    /** 是否热卖  is_hot **/
    private Byte isHot;

    /** 最后更新时间  last_update **/
    private Integer lastUpdate;

    /** 商品所属类型id，取值表goods_type的cat_id  goods_type **/
    private Short goodsType;

    /** 商品规格类型，取值表goods_type的cat_id  spec_type **/
    private Short specType;

    /** 购买商品赠送积分  give_integral **/
    private Integer giveIntegral;

    /** 积分兑换：0不参与积分兑换，积分和现金的兑换比例见后台配置  exchange_integral **/
    private Integer exchangeIntegral;

    /** 供货商ID  suppliers_id **/
    private Short suppliersId;

    /** 商品销量  sales_sum **/
    private Integer salesSum;

    /** 0 普通订单,1 限时抢购, 2 团购 , 3 促销优惠  prom_type **/
    private Byte promType;

    /** 优惠活动id  prom_id **/
    private Integer promId;

    /** 佣金用于分销分成  commission **/
    private BigDecimal commission;

    /** SPU  spu **/
    private String spu;

    /** SKU  sku **/
    private String sku;

    /** 商品详细描述  goods_content **/
    private String goodsContent;

    /**   tableName: t_goods   **/
    private static final long serialVersionUID = 1L;

    /**   商品id  goods_id   **/
    public Integer getGoodsId() {
        return goodsId;
    }

    /**   商品id  goods_id   **/
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**   分类id  cat_id   **/
    public Integer getCatId() {
        return catId;
    }

    /**   分类id  cat_id   **/
    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    /**   扩展分类id  extend_cat_id   **/
    public Integer getExtendCatId() {
        return extendCatId;
    }

    /**   扩展分类id  extend_cat_id   **/
    public void setExtendCatId(Integer extendCatId) {
        this.extendCatId = extendCatId;
    }

    /**   商品编号  goods_sn   **/
    public String getGoodsSn() {
        return goodsSn;
    }

    /**   商品编号  goods_sn   **/
    public void setGoodsSn(String goodsSn) {
        this.goodsSn = goodsSn == null ? null : goodsSn.trim();
    }

    /**   商品名称  goods_name   **/
    public String getGoodsName() {
        return goodsName;
    }

    /**   商品名称  goods_name   **/
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    /**   点击数  click_count   **/
    public Integer getClickCount() {
        return clickCount;
    }

    /**   点击数  click_count   **/
    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    /**   品牌id  brand_id   **/
    public Short getBrandId() {
        return brandId;
    }

    /**   品牌id  brand_id   **/
    public void setBrandId(Short brandId) {
        this.brandId = brandId;
    }

    /**   库存数量  store_count   **/
    public Short getStoreCount() {
        return storeCount;
    }

    /**   库存数量  store_count   **/
    public void setStoreCount(Short storeCount) {
        this.storeCount = storeCount;
    }

    /**   商品评论数  comment_count   **/
    public Short getCommentCount() {
        return commentCount;
    }

    /**   商品评论数  comment_count   **/
    public void setCommentCount(Short commentCount) {
        this.commentCount = commentCount;
    }

    /**   商品重量克为单位  weight   **/
    public Integer getWeight() {
        return weight;
    }

    /**   商品重量克为单位  weight   **/
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**   市场价  market_price   **/
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**   市场价  market_price   **/
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**   本店价  shop_price   **/
    public BigDecimal getShopPrice() {
        return shopPrice;
    }

    /**   本店价  shop_price   **/
    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }

    /**   商品成本价  cost_price   **/
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    /**   商品成本价  cost_price   **/
    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**   商品关键词  keywords   **/
    public String getKeywords() {
        return keywords;
    }

    /**   商品关键词  keywords   **/
    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    /**   商品简单描述  goods_remark   **/
    public String getGoodsRemark() {
        return goodsRemark;
    }

    /**   商品简单描述  goods_remark   **/
    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark == null ? null : goodsRemark.trim();
    }

    /**   商品上传原始图  original_img   **/
    public String getOriginalImg() {
        return originalImg;
    }

    /**   商品上传原始图  original_img   **/
    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg == null ? null : originalImg.trim();
    }

    /**   是否为实物  is_real   **/
    public Byte getIsReal() {
        return isReal;
    }

    /**   是否为实物  is_real   **/
    public void setIsReal(Byte isReal) {
        this.isReal = isReal;
    }

    /**   是否上架  is_on_sale   **/
    public Byte getIsOnSale() {
        return isOnSale;
    }

    /**   是否上架  is_on_sale   **/
    public void setIsOnSale(Byte isOnSale) {
        this.isOnSale = isOnSale;
    }

    /**   是否包邮0否1是  is_free_shipping   **/
    public Byte getIsFreeShipping() {
        return isFreeShipping;
    }

    /**   是否包邮0否1是  is_free_shipping   **/
    public void setIsFreeShipping(Byte isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    /**   商品上架时间  on_time   **/
    public Integer getOnTime() {
        return onTime;
    }

    /**   商品上架时间  on_time   **/
    public void setOnTime(Integer onTime) {
        this.onTime = onTime;
    }

    /**   商品排序  sort   **/
    public Short getSort() {
        return sort;
    }

    /**   商品排序  sort   **/
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**   是否推荐  is_recommend   **/
    public Byte getIsRecommend() {
        return isRecommend;
    }

    /**   是否推荐  is_recommend   **/
    public void setIsRecommend(Byte isRecommend) {
        this.isRecommend = isRecommend;
    }

    /**   是否新品  is_new   **/
    public Byte getIsNew() {
        return isNew;
    }

    /**   是否新品  is_new   **/
    public void setIsNew(Byte isNew) {
        this.isNew = isNew;
    }

    /**   是否热卖  is_hot   **/
    public Byte getIsHot() {
        return isHot;
    }

    /**   是否热卖  is_hot   **/
    public void setIsHot(Byte isHot) {
        this.isHot = isHot;
    }

    /**   最后更新时间  last_update   **/
    public Integer getLastUpdate() {
        return lastUpdate;
    }

    /**   最后更新时间  last_update   **/
    public void setLastUpdate(Integer lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**   商品所属类型id，取值表goods_type的cat_id  goods_type   **/
    public Short getGoodsType() {
        return goodsType;
    }

    /**   商品所属类型id，取值表goods_type的cat_id  goods_type   **/
    public void setGoodsType(Short goodsType) {
        this.goodsType = goodsType;
    }

    /**   商品规格类型，取值表goods_type的cat_id  spec_type   **/
    public Short getSpecType() {
        return specType;
    }

    /**   商品规格类型，取值表goods_type的cat_id  spec_type   **/
    public void setSpecType(Short specType) {
        this.specType = specType;
    }

    /**   购买商品赠送积分  give_integral   **/
    public Integer getGiveIntegral() {
        return giveIntegral;
    }

    /**   购买商品赠送积分  give_integral   **/
    public void setGiveIntegral(Integer giveIntegral) {
        this.giveIntegral = giveIntegral;
    }

    /**   积分兑换：0不参与积分兑换，积分和现金的兑换比例见后台配置  exchange_integral   **/
    public Integer getExchangeIntegral() {
        return exchangeIntegral;
    }

    /**   积分兑换：0不参与积分兑换，积分和现金的兑换比例见后台配置  exchange_integral   **/
    public void setExchangeIntegral(Integer exchangeIntegral) {
        this.exchangeIntegral = exchangeIntegral;
    }

    /**   供货商ID  suppliers_id   **/
    public Short getSuppliersId() {
        return suppliersId;
    }

    /**   供货商ID  suppliers_id   **/
    public void setSuppliersId(Short suppliersId) {
        this.suppliersId = suppliersId;
    }

    /**   商品销量  sales_sum   **/
    public Integer getSalesSum() {
        return salesSum;
    }

    /**   商品销量  sales_sum   **/
    public void setSalesSum(Integer salesSum) {
        this.salesSum = salesSum;
    }

    /**   0 普通订单,1 限时抢购, 2 团购 , 3 促销优惠  prom_type   **/
    public Byte getPromType() {
        return promType;
    }

    /**   0 普通订单,1 限时抢购, 2 团购 , 3 促销优惠  prom_type   **/
    public void setPromType(Byte promType) {
        this.promType = promType;
    }

    /**   优惠活动id  prom_id   **/
    public Integer getPromId() {
        return promId;
    }

    /**   优惠活动id  prom_id   **/
    public void setPromId(Integer promId) {
        this.promId = promId;
    }

    /**   佣金用于分销分成  commission   **/
    public BigDecimal getCommission() {
        return commission;
    }

    /**   佣金用于分销分成  commission   **/
    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    /**   SPU  spu   **/
    public String getSpu() {
        return spu;
    }

    /**   SPU  spu   **/
    public void setSpu(String spu) {
        this.spu = spu == null ? null : spu.trim();
    }

    /**   SKU  sku   **/
    public String getSku() {
        return sku;
    }

    /**   SKU  sku   **/
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    /**   商品详细描述  goods_content   **/
    public String getGoodsContent() {
        return goodsContent;
    }

    /**   商品详细描述  goods_content   **/
    public void setGoodsContent(String goodsContent) {
        this.goodsContent = goodsContent == null ? null : goodsContent.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", goodsId=").append(goodsId);
        sb.append(", catId=").append(catId);
        sb.append(", extendCatId=").append(extendCatId);
        sb.append(", goodsSn=").append(goodsSn);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", clickCount=").append(clickCount);
        sb.append(", brandId=").append(brandId);
        sb.append(", storeCount=").append(storeCount);
        sb.append(", commentCount=").append(commentCount);
        sb.append(", weight=").append(weight);
        sb.append(", marketPrice=").append(marketPrice);
        sb.append(", shopPrice=").append(shopPrice);
        sb.append(", costPrice=").append(costPrice);
        sb.append(", keywords=").append(keywords);
        sb.append(", goodsRemark=").append(goodsRemark);
        sb.append(", originalImg=").append(originalImg);
        sb.append(", isReal=").append(isReal);
        sb.append(", isOnSale=").append(isOnSale);
        sb.append(", isFreeShipping=").append(isFreeShipping);
        sb.append(", onTime=").append(onTime);
        sb.append(", sort=").append(sort);
        sb.append(", isRecommend=").append(isRecommend);
        sb.append(", isNew=").append(isNew);
        sb.append(", isHot=").append(isHot);
        sb.append(", lastUpdate=").append(lastUpdate);
        sb.append(", goodsType=").append(goodsType);
        sb.append(", specType=").append(specType);
        sb.append(", giveIntegral=").append(giveIntegral);
        sb.append(", exchangeIntegral=").append(exchangeIntegral);
        sb.append(", suppliersId=").append(suppliersId);
        sb.append(", salesSum=").append(salesSum);
        sb.append(", promType=").append(promType);
        sb.append(", promId=").append(promId);
        sb.append(", commission=").append(commission);
        sb.append(", spu=").append(spu);
        sb.append(", sku=").append(sku);
        sb.append(", goodsContent=").append(goodsContent);
        sb.append("]");
        return sb.toString();
    }
}