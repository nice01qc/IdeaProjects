package com.bjsxt.service.rpc;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bjsxt.result.EgoPageInfo;
import com.bjsxt.service.HelloService;
import com.bjsxt.service.SearchService;
import com.bjsxt.util.JsonUtil;
import com.bjsxt.vo.GoodsVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-*.xml")
public class SearchServiceTest {
	
	@Autowired
	SearchService searchService;
	
	@Test
	public void testDoSearch() throws SolrServerException{
		EgoPageInfo<GoodsVo> pageInfo = searchService.doSearch("手机",0, 10);
		System.out.println("pageInfo:"+JsonUtil.object2JsonStr(pageInfo));
	}

}
