package com.bjsxt.service;

import com.bjsxt.pojo.GoodsAttribute;
import com.bjsxt.result.BaseResult;

public interface GoodsAttributeService {

	/**
	 * 商品属性-保存
	 * @param goodsAttribute
	 * @return
	 */
	BaseResult goodsAttrSave(GoodsAttribute goodsAttribute);

}
