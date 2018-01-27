package com.bjsxt.service;

import java.util.List;

import com.bjsxt.result.BaseResult;
import com.bjsxt.vo.GoodsCategoryVo;

public interface GoodsCategoryService {
	/**
	 * 查询出所有的分类
	 * @return
	 */
	List<GoodsCategoryVo> selelctGoodsCategoryVoAll();

	/**
	 * 清空redis中的商品分类
	 * @return
	 */
	BaseResult clearGoodsCategoryFromRedis();
}
