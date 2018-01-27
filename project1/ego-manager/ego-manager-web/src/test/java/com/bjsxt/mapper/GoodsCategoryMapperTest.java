package com.bjsxt.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bjsxt.pojo.GoodsCategory;
import com.bjsxt.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class GoodsCategoryMapperTest {

	@Autowired
	GoodsCategoryMapper goodsCategoryMapper;
	
	@Test
	public void testInsert(){
		GoodsCategory gc = new GoodsCategory();
		gc.setName("506夹克");
		//res用<=0时，表示添加失败 获取id 需要gc.getId（）
		int res = goodsCategoryMapper.insertSelective(gc);
		
		System.out.println("id:"+gc.getId());
	}
	
	@Test
	public void testSelectById(){
		GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey((short)1);
		System.out.println("goodsCategory:"+JsonUtil.object2JsonStr(goodsCategory));
	}
	
	@Test
	public void testUpdate(){
		GoodsCategory goodsCategory = new GoodsCategory();
		goodsCategory.setId((short)1);
		goodsCategory.setName("手机 、 数码 、 通信");
		goodsCategoryMapper.updateByPrimaryKeySelective(goodsCategory);
	}
}
