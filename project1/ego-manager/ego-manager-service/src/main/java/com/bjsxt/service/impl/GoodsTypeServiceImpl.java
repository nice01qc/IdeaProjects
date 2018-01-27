package com.bjsxt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.mapper.GoodsTypeMapper;
import com.bjsxt.pojo.GoodsAttribute;
import com.bjsxt.pojo.GoodsType;
import com.bjsxt.pojo.GoodsTypeExample;
import com.bjsxt.result.BaseResult;
import com.bjsxt.service.GoodsTypeService;

@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
	
	@Autowired
	GoodsTypeMapper goodsTypeMapper;

	/**
	 * 查询所有商品规格
	 */
	@Override
	public List<GoodsType> selectGoodsTypeAll() {
		return goodsTypeMapper.selectByExample(new GoodsTypeExample());
	}
}
