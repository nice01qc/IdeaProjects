package com.bjsxt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.mapper.GoodsMapper;
import com.bjsxt.pojo.Brand;
import com.bjsxt.pojo.Goods;
import com.bjsxt.pojo.GoodsCategory;
import com.bjsxt.pojo.GoodsExample;
import com.bjsxt.result.BaseResult;
import com.bjsxt.service.GoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class GoodsServiceImpl implements GoodsService {
	
	@Autowired
	GoodsMapper goodsMapper;

	/**
	 * 商品-保存商品
	 */
	@Override
	public BaseResult goodsSave(Goods goods) {
		int res = goodsMapper.insertSelective(goods);
		if(res==1){
			BaseResult result = BaseResult.success();
			result.setMessage(String.valueOf(goods.getGoodsId()));
			return result;
		}
		return null;
	}

	/**
	 * 根据条件进行分页查询
	 */
	@Override
	public BaseResult selectGoodsListByPage(Goods goods, Integer current,Integer pageSize) {
		
		//1.调用分页工具进行分页
		PageHelper.startPage(current, pageSize);
		
		//查询
		//查询对象
		GoodsExample example = new GoodsExample();
		//where条件
		GoodsExample.Criteria criteria = example.createCriteria();
		//分类
		if(goods.getCatId()!=null){
			criteria.andCatIdEqualTo(goods.getCatId());
		}
		//品牌
		if(goods.getBrandId()!=null){
			criteria.andBrandIdEqualTo(goods.getBrandId());
		}
		//关键词
		if(goods.getGoodsName()!=null){
			criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
		}
		//进行查询
		List<Goods> goodsList = goodsMapper.selectByExample(example);
		//封装成pageInfo对象
		PageInfo<Goods> pageInfo = new PageInfo<>(goodsList);
		return BaseResult.success(pageInfo);
	}


}
