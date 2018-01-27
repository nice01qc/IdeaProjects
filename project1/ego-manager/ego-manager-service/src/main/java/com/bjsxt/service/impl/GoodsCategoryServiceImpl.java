package com.bjsxt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.mapper.GoodsCategoryMapper;
import com.bjsxt.pojo.GoodsCategory;
import com.bjsxt.pojo.GoodsCategoryExample;
import com.bjsxt.result.BaseResult;
import com.bjsxt.service.GoodsCategoryService;
import com.bjsxt.vo.GoodsCategoryVo;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

	@Autowired
	GoodsCategoryMapper goodsCategoryMapper;

	/**
	 * 查出所有顶层分类
	 */
	@Override
	public List<GoodsCategory> selectTopCategory() {
		//创建查询对象
		GoodsCategoryExample example = new GoodsCategoryExample();
		//where条件 
		example.createCriteria()
		.andParentIdEqualTo((short)0)
		.andLevelEqualTo((byte)1);
		return goodsCategoryMapper.selectByExample(example);
	}

	/**
	 * 根据父ID 查询子分类
	 */
	@Override
	public List<GoodsCategory> selectCategoryByPid(Short parentId) {
		//创建查询对象
		GoodsCategoryExample example = new GoodsCategoryExample();
		//where条件 
		example.createCriteria().andParentIdEqualTo(parentId);
		return goodsCategoryMapper.selectByExample(example);
	}

	/**
	 * 商品分类-保存
	 */
	@Override
	public BaseResult categorySave(GoodsCategory goodsCategory) {
		int res = goodsCategoryMapper.insertSelective(goodsCategory);
		if(res==1){//成功
			return BaseResult.success();
		}
		return null;
	}

	/**
	 * 商品分类-查询出所有的商品分类 有包含关系
	 */
	@Override
	public List<GoodsCategoryVo> selectCategoryListForView() {
		return selectCategoryListForViewDG((short)0,(byte)1);
	}

	/**
	 * 递归方法
	 */
	public  List<GoodsCategoryVo> selectCategoryListForViewDG(short parentId,byte level){
		//返回的结果
		List<GoodsCategoryVo> result = new ArrayList<GoodsCategoryVo>();
		//创建查询对象
		GoodsCategoryExample example = new GoodsCategoryExample();
		example.createCriteria().andParentIdEqualTo(parentId).andLevelEqualTo(level);
		//查询出分类列表
		List<GoodsCategory> gcList = goodsCategoryMapper.selectByExample(example);
		for(GoodsCategory gc:gcList){
			//创建vo对象
			GoodsCategoryVo gcv = new GoodsCategoryVo();
			//复制属性
			BeanUtils.copyProperties(gc, gcv);
			if(gc.getLevel()!=3){//递归查询
				gcv.setChildren(selectCategoryListForViewDG(gc.getId(),(byte)(gc.getLevel()+1)));
			}
			result.add(gcv);
		}
		return result;
	}

	/**
	 * 常规方法
	 */
	public List<GoodsCategoryVo> selectCategoryListForViewNormal(){
		//返回的结果
		List<GoodsCategoryVo> result = new ArrayList<GoodsCategoryVo>();
		//创建查询对象
		GoodsCategoryExample example = new GoodsCategoryExample();
		example.createCriteria().andParentIdEqualTo((short)0).andLevelEqualTo((byte)1);
		//1.查询第一级
		List<GoodsCategory> gcList1 = goodsCategoryMapper.selectByExample(example);
		for(GoodsCategory gc1:gcList1){
			//创建vo对象
			GoodsCategoryVo gcv1 = new GoodsCategoryVo();
			//复制属性
			BeanUtils.copyProperties(gc1, gcv1);
			//2.查询第二级
			//清空第一次查询的where条件
			example.clear();
			//重新指定where条件
			example.createCriteria().andParentIdEqualTo(gc1.getId()).andLevelEqualTo((byte)2);
			List<GoodsCategory> gcList2 = goodsCategoryMapper.selectByExample(example);
			List<GoodsCategoryVo> gcvList2 = new ArrayList<GoodsCategoryVo>();
			for(GoodsCategory gc2:gcList2){
				//创建vo对象
				GoodsCategoryVo gcv2 = new GoodsCategoryVo();
				//复制属性
				BeanUtils.copyProperties(gc2, gcv2);
				//3.查询第三级
				//清空第一次查询的where条件
				example.clear();
				//重新指定where条件
				example.createCriteria().andParentIdEqualTo(gc2.getId()).andLevelEqualTo((byte)3);
				List<GoodsCategory> gcList3 = goodsCategoryMapper.selectByExample(example);
				List<GoodsCategoryVo> gcvList3 = new ArrayList<GoodsCategoryVo>();
				for(GoodsCategory gc3:gcList3){
					//创建vo对象
					GoodsCategoryVo gcv3 = new GoodsCategoryVo();
					//复制属性
					BeanUtils.copyProperties(gc3, gcv3);
					gcvList3.add(gcv3);
				}
				gcv2.setChildren(gcvList3);
				gcvList2.add(gcv2);
			}
			gcv1.setChildren(gcvList2);
			result.add(gcv1);
		}
		//合并三级分类
		return result;
	}
}
