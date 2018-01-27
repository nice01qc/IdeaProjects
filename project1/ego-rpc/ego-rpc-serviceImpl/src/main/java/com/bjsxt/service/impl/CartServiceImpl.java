package com.bjsxt.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.bjsxt.pojo.Admin;
import com.bjsxt.result.BaseResult;
import com.bjsxt.result.CartResult;
import com.bjsxt.service.CartService;
import com.bjsxt.util.SerializeUtil;
import com.bjsxt.vo.Cart;
import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import redis.clients.jedis.JedisCluster;

public class CartServiceImpl implements CartService {

	@Autowired
	JedisCluster jedisCluster;
	@Value("${userCart}")
	String userCart;
	/**
	 * 添加至购物车
	 */
	@Override
	public BaseResult addToCart(Cart cart,Admin admin) {
		//获取用户的编号
		Short userId = admin.getAdminId();
		//购物车信息
		Map<byte[],byte[]> cartMap = getUserCart(admin);
		if(cartMap!=null && !cartMap.isEmpty()){//表示查询出购物车信息
			//根据编号获取 购物详细信息
			byte[] cartByte = cartMap.get(SerializeUtil.serialize(cart.getGoodsId()));
			if(cartByte!=null && cartByte.length>0){
				//将byte类型，返序列化为Cart对象
				Cart cartRedis = (Cart) SerializeUtil.unserialize(cartByte);
				//修改cartRedis信息
				if(cart.getGoodsNum()!=null){
					cartRedis.setGoodsNum(cartRedis.getGoodsNum()+cart.getGoodsNum());
					cartRedis.setGoodsPrice(cart.getGoodsPrice());
				}
				//将cartRedis重新 添加至map中
				cartMap.put(SerializeUtil.serialize(cart.getGoodsId()), SerializeUtil.serialize(cartRedis));
			}else{
				cartMap.put(SerializeUtil.serialize(cart.getGoodsId()), SerializeUtil.serialize(cart));
			}

		}else{
			//构建map对象		
			cartMap  = new HashMap<byte[],byte[]>();
			cartMap.put(SerializeUtil.serialize(cart.getGoodsId()),SerializeUtil.serialize(cart));
		}
		//添加至购物车
		jedisCluster.hmset(SerializeUtil.serialize(userCart+userId),cartMap);

		return BaseResult.success();
	}

	/**
	 * 查询购物车信息
	 */
	@Override
	public CartResult getCartInfo(Admin admin) {
		//返回结果
		CartResult result = new CartResult();
		//总金额
		BigDecimal allmount = new BigDecimal(0);
		//返回列表
		List<Cart> resultList = null;
		//查询当前用户的购物车信息
		Map<byte[],byte[]> cartMap = getUserCart(admin);
		if(cartMap!=null && !cartMap.isEmpty()){//表示查询出购物车信息
			//创建list对象
			resultList = new ArrayList<Cart>();
			for(Entry<byte[],byte[]> entry:cartMap.entrySet()){
				//获取cart对象
				Cart cart = (Cart) SerializeUtil.unserialize(entry.getValue());
				//添加至列表
				resultList.add(cart);
				//计算金额begin
				//单价
				Double price = cart.getGoodsPrice();
				//数量
				Integer num = cart.getGoodsNum();

				//单价BigDecimal
				BigDecimal priceBd = new BigDecimal(price);
				//数量
				BigDecimal numBd = new BigDecimal(num);
				//计算(单价*数量)
				allmount = allmount.add(priceBd.multiply(numBd));
				//计算金额end
			}
			result.setAllAmount(allmount.doubleValue());
		}
		result.setCartList(resultList);
		return result;
	}

	/**
	 * 获取购物车数量
	 */
	@Override
	public Integer getCartNum(Admin admin) {
		//返回的数量
		Integer result = 0;
		//查询当前用户的购物车信息
		Map<byte[],byte[]> cartMap = getUserCart(admin);
		if(cartMap!=null && !cartMap.isEmpty()){//表示查询出购物车信息
			for(Entry<byte[],byte[]> entry:cartMap.entrySet()){
				//获取cart对象
				Cart cart = (Cart) SerializeUtil.unserialize(entry.getValue());
				result+=cart.getGoodsNum();
			}
		}
		return result;
	}

	/**
	 * 查询当前用户的购物车信息
	 */
	public Map<byte[],byte[]> getUserCart(Admin admin){
		//获取用户的编号
		Short userId = admin.getAdminId();
		//查询当前用户的购物车信息
		return jedisCluster.hgetAll(SerializeUtil.serialize(userCart+userId));
	}

	/**
	 * 删除购物车信息
	 */
	@Override
	public BaseResult delCartInfo(Admin admin,Long... goodsIds) {
		//获取用户的编号
		Short userId = admin.getAdminId();
		//构建byte[]
		for(Long goodsId:goodsIds){
			byte[] goodsIdByte = SerializeUtil.serialize(goodsId);
			//执行删除操作
			jedisCluster.hdel(SerializeUtil.serialize(userCart+userId), goodsIdByte);
		}
		return BaseResult.success();
	}

}
