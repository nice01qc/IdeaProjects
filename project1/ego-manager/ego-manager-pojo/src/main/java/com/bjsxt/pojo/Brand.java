package com.bjsxt.pojo;

import java.io.Serializable;

public class Brand implements Serializable {
    /** 品牌表  id **/
    private Short id;

    /** 品牌名称  name **/
    private String name;

    /** 品牌logo  logo **/
    private String logo;

    /** 品牌地址  url **/
    private String url;

    /** 排序  sort **/
    private Byte sort;

    /** 品牌分类  cat_name **/
    private String catName;

    /** 分类id  parent_cat_id **/
    private Integer parentCatId;

    /** 分类id  cat_id **/
    private Integer catId;

    /** 是否推荐  is_hot **/
    private Byte isHot;

    /** 品牌描述  desc **/
    private String desc;

    /**   tableName: t_brand   **/
    private static final long serialVersionUID = 1L;

    /**   品牌表  id   **/
    public Short getId() {
        return id;
    }

    /**   品牌表  id   **/
    public void setId(Short id) {
        this.id = id;
    }

    /**   品牌名称  name   **/
    public String getName() {
        return name;
    }

    /**   品牌名称  name   **/
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**   品牌logo  logo   **/
    public String getLogo() {
        return logo;
    }

    /**   品牌logo  logo   **/
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**   品牌地址  url   **/
    public String getUrl() {
        return url;
    }

    /**   品牌地址  url   **/
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**   排序  sort   **/
    public Byte getSort() {
        return sort;
    }

    /**   排序  sort   **/
    public void setSort(Byte sort) {
        this.sort = sort;
    }

    /**   品牌分类  cat_name   **/
    public String getCatName() {
        return catName;
    }

    /**   品牌分类  cat_name   **/
    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    /**   分类id  parent_cat_id   **/
    public Integer getParentCatId() {
        return parentCatId;
    }

    /**   分类id  parent_cat_id   **/
    public void setParentCatId(Integer parentCatId) {
        this.parentCatId = parentCatId;
    }

    /**   分类id  cat_id   **/
    public Integer getCatId() {
        return catId;
    }

    /**   分类id  cat_id   **/
    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    /**   是否推荐  is_hot   **/
    public Byte getIsHot() {
        return isHot;
    }

    /**   是否推荐  is_hot   **/
    public void setIsHot(Byte isHot) {
        this.isHot = isHot;
    }

    /**   品牌描述  desc   **/
    public String getDesc() {
        return desc;
    }

    /**   品牌描述  desc   **/
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", logo=").append(logo);
        sb.append(", url=").append(url);
        sb.append(", sort=").append(sort);
        sb.append(", catName=").append(catName);
        sb.append(", parentCatId=").append(parentCatId);
        sb.append(", catId=").append(catId);
        sb.append(", isHot=").append(isHot);
        sb.append(", desc=").append(desc);
        sb.append("]");
        return sb.toString();
    }
}