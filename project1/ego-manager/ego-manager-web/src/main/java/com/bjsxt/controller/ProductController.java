package com.bjsxt.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import com.bjsxt.pojo.Brand;
import com.bjsxt.pojo.Goods;
import com.bjsxt.pojo.GoodsAttribute;
import com.bjsxt.pojo.GoodsCategory;
import com.bjsxt.pojo.GoodsType;
import com.bjsxt.result.BaseResult;
import com.bjsxt.result.FileResult;
import com.bjsxt.service.FileUploadService;
import com.bjsxt.service.GoodsAttributeService;
import com.bjsxt.service.GoodsCategoryService;
import com.bjsxt.service.GoodsImagesService;
import com.bjsxt.service.GoodsService;
import com.bjsxt.service.GoodsTypeService;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	private static Logger logger  = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	GoodsCategoryService goodsCategoryService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	GoodsImagesService goodsImagesService;
	
	@Autowired
	FileUploadService fileUploadService;
	
	@Autowired
	GoodsTypeService goodsTypeService;
	
	@Autowired
	GoodsAttributeService goodsAttributeService;

	/**
	 * 商品-添加商品
	 */
	@RequestMapping("/add")
	public String productAdd(ModelMap modelMap){
		//查出所有顶层分类
		List<GoodsCategory> gcList = goodsCategoryService.selectTopCategory();
		modelMap.addAttribute("gcList", gcList);
		return "product/product-add";
	}
	
	/**
	 * 商品-保存商品
	 */
	@RequestMapping("/save")
	@ResponseBody
	public BaseResult productSave(Goods goods){
		//获取商品详情
		String goodsContent = goods.getGoodsContent();
		//转义
		goodsContent = HtmlUtils.htmlEscape(goodsContent);
		//重新添加至goods对象中
		goods.setGoodsContent(goodsContent);
		//goods.setGoodsContent(HtmlUtils.htmlEscape(goods.getGoodsContent()));
		return goodsService.goodsSave(goods);
	}

	/**
	 * 跳转至 商品-列表
	 * @return
	 */
	@RequestMapping("/list")
	public String productList(){
		return "product/product-list";
	}
	
	/**
	 * 获取商品列表 数据
	 * return json
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public BaseResult getProductData(Goods goods,Integer current,Integer pageSize){
		//进行分页查询
		return goodsService.selectGoodsListByPage(goods, current, pageSize);
	}

	/**
	 * 商品分类-添加分类
	 */
	@RequestMapping("/category/add")
	public String categoryAdd(ModelMap modelMap){
		//查出所有顶层分类
		List<GoodsCategory> gcList = goodsCategoryService.selectTopCategory();
		modelMap.addAttribute("gcList", gcList);
		return "product/category-add";
	}

	/**
	 * 商品分类-根据父ID 查询子分类
	 */
	@RequestMapping(value="/category/getByPid")
	@ResponseBody
	public List<GoodsCategory> categoryGetByPid(@RequestParam(defaultValue="0") Short parentId){
		return goodsCategoryService.selectCategoryByPid(parentId);
	}

	/**
	 * 商品分类-保存
	 * @return
	 */
	@RequestMapping("/category/save")
	@ResponseBody
	public BaseResult categorySave(GoodsCategory goodsCategory){
		return goodsCategoryService.categorySave(goodsCategory);
	}

	/**
	 * 商品分类-列表
	 * @return
	 */
	@RequestMapping("/category/list")
	public String categoryList(ModelMap modelMap){
		modelMap.addAttribute("goodsCategoryList",goodsCategoryService.selectCategoryListForView());
		return "product/category-list";
	}
	
	/**
	 * 商品分类-列表
	 * @return
	 */
	@RequestMapping("/goodsImages/save/")
	@ResponseBody
	public FileResult goodsImagesSave(MultipartFile goodsImage,Integer goodsId){
		FileResult reuslt = null;
		try {
			//保存图片
			reuslt = fileUploadService.fileUploadSave(goodsImage.getOriginalFilename(), goodsImage.getInputStream());
			//将图片的url保存至数据
			goodsImagesService.goodsImagesSave(reuslt.getFileUrl(),goodsId);
		} catch (IOException e) {
			logger.error("文件上传失败："+e.getMessage());
		}
		return reuslt;
	}
	
	/**
	 * 跳转至商品属性列表
	 */
	@RequestMapping("/attr/list")
	public String goodsAttrList(){
		return "product/attr-list";
	}
	
	/**
	 * 跳转至商品属性-添加
	 */
	@RequestMapping("/attr/add")
	public String goodsAttradd(ModelMap modelMap){
		//查询所有商品规格，并添加至作用域
		modelMap.addAttribute("goodsTypeList", goodsTypeService.selectGoodsTypeAll());
		return "product/attr-add";
	}
	
	/**
	 * 商品属性-保存
	 */
	@RequestMapping("/attr/save")
	@ResponseBody
	public BaseResult goodsAttrsave(GoodsAttribute goodsAttribute){
		//商品属性-保存
		return goodsAttributeService.goodsAttrSave(goodsAttribute);
	}
}
