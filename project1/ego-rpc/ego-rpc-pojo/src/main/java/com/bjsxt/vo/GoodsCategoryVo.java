package com.bjsxt.vo;

import java.io.Serializable;
import java.util.List;

public class GoodsCategoryVo implements Serializable {
	 /**   tableName: t_goods_category   **/
    private static final long serialVersionUID = 1L;
    /** 商品分类id  id **/
    private Short id;

    /** 商品分类名称  name **/
    private String name;
    
    //子对象
    List<GoodsCategoryVo> children;


    /**   商品分类id  id   **/
    public Short getId() {
        return id;
    }

    /**   商品分类id  id   **/
    public void setId(Short id) {
        this.id = id;
    }

    /**   商品分类名称  name   **/
    public String getName() {
        return name;
    }

    /**   商品分类名称  name   **/
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
	public List<GoodsCategoryVo> getChildren() {
		return children;
	}

	public void setChildren(List<GoodsCategoryVo> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "GoodsCategoryVo [id=" + id + ", name=" + name + ", children=" + children + "]";
	}
}