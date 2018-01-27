package com.bjsxt.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bjsxt.util.JsonUtil;
import com.bjsxt.vo.GoodsCategoryVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class GoodsCategoryServiceTest {
	@Autowired
	GoodsCategoryService goodsCategoryService;
	
	@Test
	public void testSelectCategoryListForView(){
		List<GoodsCategoryVo> gcvList = goodsCategoryService.selectCategoryListForView();
		System.out.println(JsonUtil.object2JsonStr(gcvList));
	}
}
