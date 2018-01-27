package com.bjsxt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bjsxt.model.PreOrderModel;
import com.bjsxt.pojo.Admin;
import com.bjsxt.pojo.PreOrder;
import com.bjsxt.result.BaseResult;
import com.bjsxt.service.CartService;
import com.bjsxt.service.OrderService;
import com.bjsxt.util.JsonUtil;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	@Autowired
	CartService cartService;
	/**
	 * 保存预订单
	 */
	@RequestMapping("/savePreOrder")
	public String savePreOrder(PreOrderModel preOrderModel,Double goodsAllamount,HttpServletRequest request){
		Admin admin = (Admin) request.getSession().getAttribute("currentUser");
		System.out.println("preOrderModel:"+JsonUtil.object2JsonStr(preOrderModel));
		//保存预订单
		BaseResult result = orderService.savePreOrder(preOrderModel.getPreOrderList(),goodsAllamount,admin.getAdminId());
		if(result.getCode().equals(200)){
			//跳转至预订单页面
			return "redirect:"+request.getServletContext().getAttribute("orderSystemPreOrder")
					+"?orderId="+result.getMessage()+"&goodsAllamount="+goodsAllamount;
		}
		return "preOrder";
	}
}
