package com.bjsxt.controller;

import java.io.UnsupportedEncodingException;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bjsxt.result.EgoPageInfo;
import com.bjsxt.service.SearchService;
import com.bjsxt.vo.GoodsVo;

/**
 * 搜索控制器
 * @author zhiduo
 *
 */

@RequestMapping("/search")
@Controller
public class SearchController {

	@Autowired
	SearchService searchService;
	/**
	 * 跳转至搜索页
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(String searchStr,ModelMap modelMap) throws UnsupportedEncodingException{
		//解决get请求乱码问题
		searchStr = new String(searchStr.getBytes("iso-8859-1"),"UTF-8");
		modelMap.addAttribute("searchStr", searchStr);
		return "search/doSearch";
	}
	
	/**
	 * 执行搜索
	 * @throws SolrServerException 
	 */
	@RequestMapping(value="/searchGoodsInfo",method=RequestMethod.POST)
	@ResponseBody
	public EgoPageInfo<GoodsVo> searchGoodsInfo(String searchStr,Integer currentPage,Integer rows) throws SolrServerException{
		//执行搜索
		return searchService.doSearch(searchStr, currentPage, rows);
	}
}
