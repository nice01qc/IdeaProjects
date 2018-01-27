package com.bjsxt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.mapper.GoodsAttributeMapper;
import com.bjsxt.pojo.GoodsAttribute;
import com.bjsxt.result.BaseResult;
import com.bjsxt.service.GoodsAttributeService;

@Service
public class GoodsAttributeServiceImpl implements GoodsAttributeService {

	@Autowired
	GoodsAttributeMapper goodsAttributeMapper;

	/**
	 * 商品属性-保存
	 */
	@Override
	public BaseResult goodsAttrSave(GoodsAttribute goodsAttribute) {
		//添加至数据库
		int res = goodsAttributeMapper.insertSelective(goodsAttribute);
		if(res==1){
			return BaseResult.success();
		}
		return null;
	}
}
