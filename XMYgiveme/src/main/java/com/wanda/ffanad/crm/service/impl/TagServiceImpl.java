package com.wanda.ffanad.crm.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wanda.ffanad.crm.integration.common.ApiUrlBuilder;
import com.wanda.ffanad.crm.integration.userprofile.resp.TagDto;
import com.wanda.ffanad.crm.integration.userprofile.resp.TagInfoResp;
import com.wanda.ffanad.crm.service.TagService;

/**
 * 类TagService.java的实现描述：标签接口服务类
 * 
 * @author Yao 2017年4月20日 上午9:52:42
 */
@Service
public class TagServiceImpl implements TagService {
	private static final Logger logger = LoggerFactory.getLogger(TagServiceImpl.class);

	@Autowired
	private ApiUrlBuilder apiUrlBuilder;
	@Autowired
	private RestTemplate restTemplate;

	private LoadingCache<String, TagDto> cache;

	@PostConstruct
	public void init() {
		cache = CacheBuilder.newBuilder().refreshAfterWrite(3, TimeUnit.HOURS).expireAfterAccess(3, TimeUnit.HOURS)
				.maximumSize(1000).build(new CacheLoader<String, TagDto>() {
					@Override
					public TagDto load(String name) {
						return queryTagByName(name);
					}
				});
	}

	/**
	 * 通过标签机器名获取标签信息
	 * 
	 * @param name
	 * @return
	 */
	public TagDto getTagByName(String name) {
		TagDto tag = null;
		try {
			tag = cache.get(name);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return tag;
	}

	/**
	 * 通过标签机器名查询标签信息
	 * 
	 * @param name
	 * @return
	 */
	public TagDto queryTagByName(String name) {
		String restUrl = apiUrlBuilder.buildQueryTagUrl(name);
		ResponseEntity<TagInfoResp> resp = restTemplate.getForEntity(restUrl, TagInfoResp.class);
		TagInfoResp body = resp.getBody();
		if (!resp.getStatusCode().equals(HttpStatus.OK) || body.getStatus() != org.apache.http.HttpStatus.SC_OK) {
			logger.error("请求出错，入参：name:{}", name);
			return null;
		}
		return body.getData();
	}
}
