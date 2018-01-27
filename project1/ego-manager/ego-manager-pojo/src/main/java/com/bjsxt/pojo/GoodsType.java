package com.bjsxt.pojo;

import java.io.Serializable;

public class GoodsType implements Serializable {
    /** id自增  id **/
    private Short id;

    /** 类型名称  name **/
    private String name;

    /**   tableName: t_goods_type   **/
    private static final long serialVersionUID = 1L;

    /**   id自增  id   **/
    public Short getId() {
        return id;
    }

    /**   id自增  id   **/
    public void setId(Short id) {
        this.id = id;
    }

    /**   类型名称  name   **/
    public String getName() {
        return name;
    }

    /**   类型名称  name   **/
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append("]");
        return sb.toString();
    }
}