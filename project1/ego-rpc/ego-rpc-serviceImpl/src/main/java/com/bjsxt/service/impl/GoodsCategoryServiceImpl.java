package com.bjsxt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.server.util.SerializeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bjsxt.mapper.GoodsCategoryMapper;
import com.bjsxt.pojo.GoodsCategory;
import com.bjsxt.pojo.GoodsCategoryExample;
import com.bjsxt.result.BaseResult;
import com.bjsxt.service.GoodsCategoryService;
import com.bjsxt.util.SerializeUtil;
import com.bjsxt.vo.GoodsCategoryVo;

import redis.clients.jedis.JedisCluster;

@Service
@com.alibaba.dubbo.config.annotation.Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

	@Autowired
	GoodsCategoryMapper goodsCategoryMapper;

	@Autowired
	JedisCluster jedisCluster;

	@Value("${redis.gc.key}")
	String redisGcKey;

	/**
	 * 查询出所有的分类
	 */
	@Override
	public List<GoodsCategoryVo> selelctGoodsCategoryVoAll() {

		//查询分类
		List<GoodsCategoryVo> result = null;

		//1.从redis中获取数据
		byte[] resultRedis = jedisCluster.get(SerializeUtil.serialize(redisGcKey));
		if(resultRedis!=null && resultRedis.length>0){//2.如果获取到数据，直接返回
			return (List<GoodsCategoryVo>)SerializeUtil.unserialize(resultRedis);
		}else{//3.如果没有获取到数据，从mysql中查询，并将查询的结果添加redis中
			result = selectGoodsCategoryVoByPid((short)0,(byte)1);
			jedisCluster.set(SerializeUtil.serialize(redisGcKey), SerializeUtil.serialize(result));
		}
		return result;
	}

	/**
	 * 递归查询分类 
	 */
	public List<GoodsCategoryVo> selectGoodsCategoryVoByPid(Short parentId,Byte level){
		List<GoodsCategoryVo> result = new ArrayList<GoodsCategoryVo>();
		//创建查询对象
		GoodsCategoryExample example = new GoodsCategoryExample();
		//指定查询条件
		GoodsCategoryExample.Criteria criteria = example.createCriteria();
		//指定parentId
		criteria.andParentIdEqualTo(parentId);
		//指定级别
		criteria.andLevelEqualTo(level);
		//查询
		List<GoodsCategory> gcList = goodsCategoryMapper.selectByExample(example);
		for(GoodsCategory gc:gcList){
			//创建对象
			GoodsCategoryVo gcv = new GoodsCategoryVo();
			//给对象赋值
			gcv.setId(gc.getId());
			gcv.setName(gc.getName());
			if(!"3".equals(gc.getLevel())){//非第三级，进行递归
				gcv.setChildren(selectGoodsCategoryVoByPid(gc.getId(),(byte)(gc.getLevel()+1)));
			}
			result.add(gcv);
		}
		return result;

	}

	/**
	 * 清空redis中的商品分类
	 */
	@Override
	public BaseResult clearGoodsCategoryFromRedis() {
		//清空redis中的商品分类
		jedisCluster.del(SerializeUtil.serialize(redisGcKey));
		return BaseResult.success();
	}

}
