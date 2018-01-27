package com.bjsxt.service;

import com.bjsxt.pojo.Brand;
import com.bjsxt.pojo.Goods;
import com.bjsxt.pojo.GoodsCategory;
import com.bjsxt.result.BaseResult;

public interface GoodsService {

	/**
	 * 商品-保存商品
	 * @param goods
	 * @return
	 */
	BaseResult goodsSave(Goods goods);
	
	/**
	 * 分页查询
	 */
	BaseResult selectGoodsListByPage(Goods goods,Integer current,Integer pageSize);

}
