package com.bjsxt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.result.BaseResult;
import com.bjsxt.service.GoodsCategoryService;
import com.bjsxt.vo.GoodsCategoryVo;

@Controller
@RequestMapping("/goodsCategory")
public class GoodsCategoryController {

	@Autowired
	GoodsCategoryService goodsCategoryService;
	/**
	 * 查询出所有的商品分类
	 */
	@RequestMapping("/getAll")
	@ResponseBody
	public List<GoodsCategoryVo> getGcAll(){
		return goodsCategoryService.selelctGoodsCategoryVoAll();
	}
	
	/**
	 * 清空redis中的商品分类
	 */
	@RequestMapping("/clearGc")
	@ResponseBody
	public BaseResult clearGc(){
		return goodsCategoryService.clearGoodsCategoryFromRedis();
	}
}
