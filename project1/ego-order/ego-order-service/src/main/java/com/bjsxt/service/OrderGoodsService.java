package com.bjsxt.service;

import java.util.List;

import com.bjsxt.pojo.OrderGoods;

public interface OrderGoodsService {

	/**
	 * 从购物车中，删除已提交的订单商品
	 * @param orderId
	 * @return
	 */
	List<OrderGoods> clearSubmitOrderGoods(Integer orderId);

}
