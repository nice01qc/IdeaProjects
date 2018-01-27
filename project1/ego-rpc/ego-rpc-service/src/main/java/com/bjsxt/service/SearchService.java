package com.bjsxt.service;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

import com.bjsxt.result.EgoPageInfo;
import com.bjsxt.vo.GoodsVo;

public interface SearchService {
	/**
	 * 执行搜索
	 * @param search
	 * @param start
	 * @param rows
	 * @return
	 * @throws SolrServerException
	 */
	EgoPageInfo<GoodsVo> doSearch(String search,Integer currentPage,Integer rows)  throws SolrServerException;

}
