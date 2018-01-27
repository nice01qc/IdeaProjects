package com.bjsxt.service;

import java.util.List;

import com.bjsxt.pojo.PreOrder;
import com.bjsxt.result.BaseResult;

public interface OrderService {

	/**
	 * 保存预订单
	 * @param preOrderList
	 * @param goodsAllamount
	 * @return
	 */
	BaseResult savePreOrder(List<PreOrder> preOrderList, Double goodsAllamount,Short userId);
}
