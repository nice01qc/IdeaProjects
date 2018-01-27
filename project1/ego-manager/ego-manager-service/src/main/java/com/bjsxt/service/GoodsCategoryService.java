package com.bjsxt.service;

import java.util.List;

import com.bjsxt.pojo.GoodsCategory;
import com.bjsxt.result.BaseResult;
import com.bjsxt.vo.GoodsCategoryVo;

public interface GoodsCategoryService {

	/**
	 * 查出所有顶层分类
	 * @return
	 */
	List<GoodsCategory> selectTopCategory();

	/**
	 * 根据父ID 查询子分类
	 * @param parentId
	 * @return
	 */
	List<GoodsCategory> selectCategoryByPid(Short parentId);
	

	/**
	 * 商品分类-保存
	 * @param goodsCategory
	 * @return
	 */
	BaseResult categorySave(GoodsCategory goodsCategory);
	
	/**
	 * 商品分类-查询出所有的商品分类 有包含关系
	 */
	List<GoodsCategoryVo> selectCategoryListForView();

}
