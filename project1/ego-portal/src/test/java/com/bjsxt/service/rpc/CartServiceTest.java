package com.bjsxt.service.rpc;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bjsxt.pojo.Admin;
import com.bjsxt.result.BaseResult;
import com.bjsxt.service.CartService;
import com.bjsxt.util.JsonUtil;
import com.bjsxt.vo.Cart;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class CartServiceTest {
	
	@Autowired
	CartService cartService;
	
	/**
	 * 测试添加购物车
	 */
	@Test
	public void testAddToCart(){
		Admin admin = new Admin();
		admin.setAdminId((short)10001);
		
		//实例化购物车信息
		Cart cart  = new Cart();
		cart.setGoodsId((long)1234567);
		cart.setAddTime(new Date());
		cart.setGoodsName("506超级手机第一代");
		cart.setGoodsNum(998);
		cart.setGoodsPrice((double)998);
		BaseResult result = cartService.addToCart(cart, admin);
		System.out.println(result.getCode());
	}
	
	/**
	 * 测试获取购物车信息
	 */
	@Test
	public void testGetCartList(){
		Admin admin = new Admin();
		admin.setAdminId((short)10001);
		List<Cart> cartList = cartService.getCartInfo(admin);
		System.out.println(JsonUtil.object2JsonStr(cartList));
	}
	
	
	/**
	 * 测试获取购物车信息
	 */
	@Test
	public void testGetCartNum(){
		Admin admin = new Admin();
		admin.setAdminId((short)10001);
		Integer cartNum = cartService.getCartNum(admin);
		System.out.println("cartNum:"+cartNum);
	}

}
