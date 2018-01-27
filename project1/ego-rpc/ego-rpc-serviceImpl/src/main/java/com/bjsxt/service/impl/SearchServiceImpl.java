package com.bjsxt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;

import com.bjsxt.result.EgoPageInfo;
import com.bjsxt.service.SearchService;
import com.bjsxt.vo.GoodsVo;

public class SearchServiceImpl implements SearchService {

	@Autowired
	CloudSolrServer solrServer;

	/**
	 * 执行搜索
	 */
	@Override
	public EgoPageInfo<GoodsVo> doSearch(String search, Integer currentPage, Integer rows) throws SolrServerException {
		//创建查询对象
		SolrQuery query = new SolrQuery();
		//指定条件
		query.setQuery("goodsName:"+search);
		//指定分页查询起始条数
		currentPage = currentPage<=1?1:currentPage;
		query.setStart((currentPage-1)*rows);
		//指定rows
		query.setRows(rows);
		//开启高亮
		query.setHighlight(true);
		//指定高亮字段
		query.addHighlightField("goodsName");
		//设置样式前缀
		query.setHighlightSimplePre("<font style='color:red'>");
		//设置样式后缀
		query.setHighlightSimplePost("</font>");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//获取结果集群
		SolrDocumentList docs = response.getResults();
		//获取高亮信息
		Map<String, Map<String, List<String>>>  hlMap = response.getHighlighting();
		//获取PageInfo对象
		EgoPageInfo<GoodsVo> pageInfo = getEgoPageInfo(docs, hlMap, currentPage, rows);
		return pageInfo;
	}

	//根据结果集和高亮信息，得到pageInfo对象
	public EgoPageInfo<GoodsVo> getEgoPageInfo(SolrDocumentList docs,Map<String, Map<String, List<String>>>  hlMap,
			Integer currentPage,Integer rows){
		//获取总条数
		Long count = docs.getNumFound();
		//构造分页对象
		EgoPageInfo<GoodsVo> pageInfo = new EgoPageInfo<GoodsVo>(currentPage, rows, count.intValue());
		//初始化结果集
		List<GoodsVo> goodsVoList = new ArrayList<GoodsVo>();
		//遍历结果集，构造vo对象
		for(SolrDocument doc:docs){
			//构造vo对象
			GoodsVo goodsVo = new GoodsVo();
			//商品编号
			Long goodsId = (Long) doc.getFieldValue("goodsId");
			goodsVo.setGoodsId(goodsId);
			//商品名称
			goodsVo.setGoodsName((String) doc.getFieldValue("goodsName"));
			//商品价格
			goodsVo.setMarketPrice((Double) doc.getFieldValue("marketPrice"));
			//商品图片
			goodsVo.setOriginalImg((String)doc.getFieldValue("originalImg"));
			//设置高亮信息begin
			//					String goodsNameHl = hlMap.get(goodsId).get("goodsName").get(0);
			//根据goodsId得到高亮的map集合
			Map<String, List<String>> map = hlMap.get(String.valueOf(goodsId));
			//根据key（goodsName）得到高亮的列表集合
			List<String> hlList = map.get("goodsName");
			//取得第一条数据(所有的高亮列表，都只有一条数据)
			String goodsNameHl = hlList.get(0);
			//添加高亮的结果，添加至vo对象中
			goodsVo.setGoodsNameHl(goodsNameHl);
			//设置高亮信息end
			goodsVoList.add(goodsVo);
		}

		//设置pageInfo结果集
		pageInfo.setResult(goodsVoList);
		return pageInfo;
	}
}
