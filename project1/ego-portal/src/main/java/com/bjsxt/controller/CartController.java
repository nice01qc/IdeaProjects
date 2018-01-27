package com.bjsxt.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.pojo.Admin;
import com.bjsxt.result.BaseResult;
import com.bjsxt.result.CartResult;
import com.bjsxt.service.CartService;
import com.bjsxt.vo.Cart;

/**
 * 购物车控制器
 * @author zhiduo
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartService cartService;
	
	/**
	 * 添加至购物车
	 */
	@RequestMapping("/addToCart")
	@ResponseBody
	public BaseResult addToCart(Cart cart,HttpServletRequest request){
		cart.setAddTime(new Date());
		Admin admin  = (Admin)request.getSession().getAttribute("currentUser");
		return cartService.addToCart(cart, admin);
	}
	
	/**
	 * 获取购物车列表
	 */
	@RequestMapping("/getCartInfo")
	public String getCartInfo(HttpServletRequest request,ModelMap modelMap){
		Admin admin  = (Admin)request.getSession().getAttribute("currentUser");
		CartResult result = cartService.getCartInfo(admin);
		if(result!=null){
			modelMap.addAttribute("carList", result.getCartList());
			modelMap.addAttribute("allAmount", result.getAllAmount());
		}
		return "cart/list";
	}
	
	/**
	 * 获取购物车数量
	 */
	@RequestMapping("/getCartNum")
	@ResponseBody
	public Integer getCartNum(HttpServletRequest request){
		//获取当前用户
		Admin admin  = (Admin)request.getSession().getAttribute("currentUser");
		return cartService.getCartNum(admin);
	}
}
