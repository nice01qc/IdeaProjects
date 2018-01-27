package com.bjsxt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bjsxt.pojo.Admin;
import com.bjsxt.pojo.OrderGoods;
import com.bjsxt.result.BaseResult;
import com.bjsxt.service.CartService;
import com.bjsxt.service.OrderGoodsService;

@Controller
@RequestMapping("order")
public class OrderController {

	@Autowired
	OrderGoodsService orderGoodsService;
	@Autowired
	CartService cartService;
	/**
	 * 比如：前台传welcome 跳转至/WEB-INF/pages/welcom.jsp
	 * 比如：前台传login 跳转至/WEB-INF/pages/login.jsp
	 * 公共的页面跳转 restful风格
	 */
	@RequestMapping("/preOrder")
	public String preOrder(Integer orderId,String goodsAllamount,ModelMap modelMap){
		modelMap.addAttribute("orderId",orderId);
		modelMap.addAttribute("goodsAllamount",goodsAllamount);
		return "preOrder";
	}
	/**
	 * 确认订单
	 */
	@RequestMapping("/confirmOrder")
	public String confirmOrder(Integer orderId,HttpServletRequest request){
		//获取当前用户
		Admin admin = (Admin)request.getSession().getAttribute("currentUser");
		//根据订单编号 查询出已提交的订单商品
		List<OrderGoods> orderGoodsList = orderGoodsService.clearSubmitOrderGoods(orderId);
		//指定长度数组
		Long[] goodsIds = new Long[orderGoodsList.size()];
		//遍历
		for(int i=0;i<orderGoodsList.size();i++){
			goodsIds[i]=(long)orderGoodsList.get(i).getGoodsId();
		}
		//从购物车中，删除已提交的订单商品
		cartService.delCartInfo(admin, goodsIds);
		return "confirmOrder";
	}
}
