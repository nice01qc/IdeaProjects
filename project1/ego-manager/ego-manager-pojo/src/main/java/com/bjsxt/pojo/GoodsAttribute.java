package com.bjsxt.pojo;

import java.io.Serializable;

public class GoodsAttribute implements Serializable {
    /** 属性id  attr_id **/
    private Integer attrId;

    /** 属性名称  attr_name **/
    private String attrName;

    /** 属性分类id  type_id **/
    private Short typeId;

    /** 0不需要检索 1关键字检索 2范围检索  attr_index **/
    private Byte attrIndex;

    /** 0唯一属性 1单选属性 2复选属性  attr_type **/
    private Byte attrType;

    /**  0 手工录入 1从列表中选择 2多行文本框  attr_input_type **/
    private Byte attrInputType;

    /** 属性排序  order **/
    private Byte order;

    /** 可选值列表  attr_values **/
    private String attrValues;

    /**   tableName: t_goods_attribute   **/
    private static final long serialVersionUID = 1L;

    /**   属性id  attr_id   **/
    public Integer getAttrId() {
        return attrId;
    }

    /**   属性id  attr_id   **/
    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    /**   属性名称  attr_name   **/
    public String getAttrName() {
        return attrName;
    }

    /**   属性名称  attr_name   **/
    public void setAttrName(String attrName) {
        this.attrName = attrName == null ? null : attrName.trim();
    }

    /**   属性分类id  type_id   **/
    public Short getTypeId() {
        return typeId;
    }

    /**   属性分类id  type_id   **/
    public void setTypeId(Short typeId) {
        this.typeId = typeId;
    }

    /**   0不需要检索 1关键字检索 2范围检索  attr_index   **/
    public Byte getAttrIndex() {
        return attrIndex;
    }

    /**   0不需要检索 1关键字检索 2范围检索  attr_index   **/
    public void setAttrIndex(Byte attrIndex) {
        this.attrIndex = attrIndex;
    }

    /**   0唯一属性 1单选属性 2复选属性  attr_type   **/
    public Byte getAttrType() {
        return attrType;
    }

    /**   0唯一属性 1单选属性 2复选属性  attr_type   **/
    public void setAttrType(Byte attrType) {
        this.attrType = attrType;
    }

    /**    0 手工录入 1从列表中选择 2多行文本框  attr_input_type   **/
    public Byte getAttrInputType() {
        return attrInputType;
    }

    /**    0 手工录入 1从列表中选择 2多行文本框  attr_input_type   **/
    public void setAttrInputType(Byte attrInputType) {
        this.attrInputType = attrInputType;
    }

    /**   属性排序  order   **/
    public Byte getOrder() {
        return order;
    }

    /**   属性排序  order   **/
    public void setOrder(Byte order) {
        this.order = order;
    }

    /**   可选值列表  attr_values   **/
    public String getAttrValues() {
        return attrValues;
    }

    /**   可选值列表  attr_values   **/
    public void setAttrValues(String attrValues) {
        this.attrValues = attrValues == null ? null : attrValues.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", attrId=").append(attrId);
        sb.append(", attrName=").append(attrName);
        sb.append(", typeId=").append(typeId);
        sb.append(", attrIndex=").append(attrIndex);
        sb.append(", attrType=").append(attrType);
        sb.append(", attrInputType=").append(attrInputType);
        sb.append(", order=").append(order);
        sb.append(", attrValues=").append(attrValues);
        sb.append("]");
        return sb.toString();
    }
}