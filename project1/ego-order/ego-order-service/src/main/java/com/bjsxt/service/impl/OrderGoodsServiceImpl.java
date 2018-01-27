package com.bjsxt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.mapper.OrderGoodsMapper;
import com.bjsxt.pojo.OrderGoods;
import com.bjsxt.pojo.OrderGoodsExample;
import com.bjsxt.service.OrderGoodsService;

@Service
public class OrderGoodsServiceImpl implements OrderGoodsService {
	@Autowired
	OrderGoodsMapper orderGoodsMapper;

	@Override
	public List<OrderGoods> clearSubmitOrderGoods(Integer orderId) {
		//创建查询对象
		OrderGoodsExample example = new OrderGoodsExample();
		//指定查询条件
		example.createCriteria().andOrderIdEqualTo(orderId);
		//根据订单编号 查询出已提交的商品
		return orderGoodsMapper.selectByExample(example);
	}
}
