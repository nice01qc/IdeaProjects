package com.bjsxt.service;

import java.util.List;

import com.bjsxt.pojo.GoodsAttribute;
import com.bjsxt.pojo.GoodsType;
import com.bjsxt.result.BaseResult;

public interface GoodsTypeService {

	/**
	 * 查询所有商品规格
	 * @return
	 */
	List<GoodsType> selectGoodsTypeAll();
}
