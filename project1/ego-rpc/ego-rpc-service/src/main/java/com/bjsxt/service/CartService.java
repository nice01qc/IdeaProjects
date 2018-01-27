package com.bjsxt.service;

import java.util.List;

import com.bjsxt.pojo.Admin;
import com.bjsxt.result.BaseResult;
import com.bjsxt.result.CartResult;
import com.bjsxt.vo.Cart;

/**
 * 购物车服务
 * @author zhiduo
 *
 */
public interface CartService {
	
	/**
	 * 添加至购物车
	 */
	BaseResult addToCart(Cart cart,Admin admin);
	/**
	 * 查询购物车信息
	 */
	CartResult getCartInfo(Admin admin);
	/**
	 * 获取购物车数量
	 */
	Integer getCartNum(Admin admin);
	
	/**
	 * 删除购物车信息
	 */
	public BaseResult delCartInfo(Admin admin,Long... goodsIds);
}
