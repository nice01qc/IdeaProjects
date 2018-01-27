package com.bjsxt.service.rpc;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bjsxt.service.GoodsCategoryService;
import com.bjsxt.service.HelloService;
import com.bjsxt.util.JsonUtil;
import com.bjsxt.vo.GoodsCategoryVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class GoodsCategoryServiceTest {
	
	@Autowired
	GoodsCategoryService goodsCategoryService;
	
	@Test
	public void testSelelctGoodsCategoryVoAll(){
		List<GoodsCategoryVo> gcvList = goodsCategoryService.selelctGoodsCategoryVoAll();
		System.out.println("结果："+JsonUtil.object2JsonStr(gcvList));
	}

}
