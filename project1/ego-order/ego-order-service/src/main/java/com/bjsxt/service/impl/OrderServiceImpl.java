package com.bjsxt.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.bjsxt.enums.OrderStatus;
import com.bjsxt.enums.PayStatus;
import com.bjsxt.enums.PromTypeStatus;
import com.bjsxt.enums.SendStatus;
import com.bjsxt.enums.ShippingStatus;
import com.bjsxt.mapper.OrderGoodsMapper;
import com.bjsxt.mapper.OrderMapper;
import com.bjsxt.pojo.Order;
import com.bjsxt.pojo.OrderGoods;
import com.bjsxt.pojo.PreOrder;
import com.bjsxt.result.BaseResult;
import com.bjsxt.service.OrderService;
import com.bjsxt.util.DateUtil;

import redis.clients.jedis.JedisCluster;

public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderMapper orderMapper;
	@Autowired
	OrderGoodsMapper orderGoodsMapper;
	@Autowired
	JedisCluster jedisCluster;
	
	@Value("${redis.order.incrkey}")
	String redisOrderIncrkey;

	/**
	 * 保存预订单
	 * @param preOrderList
	 * @param goodsAllamount
	 * @return
	 */
	@Override
	public BaseResult savePreOrder(List<PreOrder> preOrderList, Double goodsAllamount,Short userId) {
		//订单编号
		String orderSn = "Ego_"+DateUtil.getDateStr(new Date(),"yyyyMMddhhmmss")+jedisCluster.incr(redisOrderIncrkey);
		//创建order对象
		Order order  = new Order();
		//设置订单编号
		//Ego_20170106150236_1;
		order.setOrderSn(orderSn);
		//设置用户编号
		order.setUserId(userId.intValue());
		//设置订单状态(未确认)
		order.setOrderStatus(OrderStatus.no_confirm.getStatus());
		//设置发货状态
		order.setShippingStatus(ShippingStatus.no_send.getStatus());
		//设置支付状态
		order.setPayStatus(PayStatus.no_pay.getStatus());
		//设置商品总价格
		order.setGoodsPrice(new BigDecimal(goodsAllamount));
		//应付款金额
		order.setOrderAmount(new BigDecimal(goodsAllamount));
		//订单总价
		order.setOrderAmount(new BigDecimal(goodsAllamount));
		//下单时间
		Long currentTime = System.currentTimeMillis()/1000;
		order.setAddTime(currentTime.intValue());
		//会返回orderId，封装在order对象中
		orderMapper.insertSelective(order);
		//建议实际生产环境中，使用batch保存
		for(PreOrder preOrder:preOrderList){
			//创建OrderGoods对象
			OrderGoods orderGoods  = new OrderGoods();
			//设置订单编号
			orderGoods.setOrderId(order.getOrderId());
			//商品编号
			orderGoods.setGoodsId(preOrder.getGoodsId().intValue());
			//商品价格
			orderGoods.setGoodsPrice(new BigDecimal(preOrder.getGoodsPrice()));
			//商品名称
			orderGoods.setGoodsName(preOrder.getGoodsName());
			//商品数量
			orderGoods.setGoodsNum(preOrder.getGoodsNum().shortValue());
			//订单方式
			orderGoods.setPromType(PromTypeStatus.normal.getStatus());
			//发货状态
			orderGoods.setIsSend(SendStatus.no_pay.getStatus());
			orderGoodsMapper.insertSelective(orderGoods);
		}
		BaseResult result = BaseResult.success();
		result.setMessage(String.valueOf(order.getOrderId()));
		return result;
	}
}