package com.bjsxt.pojo;

import java.io.Serializable;

public class GoodsImages implements Serializable {
    /** 图片id 自增  img_id **/
    private Integer imgId;

    /** 商品id  goods_id **/
    private Integer goodsId;

    /** 图片地址  image_url **/
    private String imageUrl;

    /**   tableName: t_goods_images   **/
    private static final long serialVersionUID = 1L;

    /**   图片id 自增  img_id   **/
    public Integer getImgId() {
        return imgId;
    }

    /**   图片id 自增  img_id   **/
    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    /**   商品id  goods_id   **/
    public Integer getGoodsId() {
        return goodsId;
    }

    /**   商品id  goods_id   **/
    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    /**   图片地址  image_url   **/
    public String getImageUrl() {
        return imageUrl;
    }

    /**   图片地址  image_url   **/
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", imgId=").append(imgId);
        sb.append(", goodsId=").append(goodsId);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append("]");
        return sb.toString();
    }
}