package com.bjsxt.result;

import java.io.Serializable;
import java.util.List;

import com.bjsxt.vo.Cart;

/**
 * 购物车返回结果
 * @author zhiduo
 *
 */
public class CartResult implements Serializable {
	private static final long serialVersionUID = 9081550240457920158L;
	//总金额
	private Double allAmount;
	//列表
	List<Cart> cartList;
	public Double getAllAmount() {
		return allAmount;
	}
	public void setAllAmount(Double allAmount) {
		this.allAmount = allAmount;
	}
	public List<Cart> getCartList() {
		return cartList;
	}
	public void setCartList(List<Cart> cartList) {
		this.cartList = cartList;
	}
	@Override
	public String toString() {
		return "CartResult [allAmount=" + allAmount + ", cartList=" + cartList + "]";
	}
}
