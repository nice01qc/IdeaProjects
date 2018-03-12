package com.wanda.ffanad.crm.integration.account.service;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wanda.ffanad.base.dal.entities.AjustProfitLogEntity;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.crm.common.RestResponse;
import com.wanda.ffanad.crm.integration.account.req.AjustProfitLogReqDto;
import com.wanda.ffanad.crm.integration.account.resp.AjustProfitLogResp;
import com.wanda.ffanad.crm.integration.account.resp.AjustProfitLogRespDto;
import com.wanda.ffanad.crm.integration.common.ApiUrlBuilder;

@Service
public class AjustprofitlogService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ApiUrlBuilder apiUrlBuilder;

	/**
	 * 调整分成
	 * @param ajustProfitLogEntity
	 */
    public void updateProfitShare(AjustProfitLogEntity ajustProfitLogEntity){
        String restUrl = apiUrlBuilder.buildUpdateProfitShareUrl(ajustProfitLogEntity.getAccountId());
        RestResponse resp = restTemplate.postForObject(restUrl, ajustProfitLogEntity, RestResponse.class);
        if(resp == null || resp.getStatus() != HttpStatus.SC_OK ){
            //TODO shiyu 
        }
    }
    
    /**
     * 分成详情
     * @param accountId
     * @return
     */
    public double profitShareDetail(Integer accountId){
        String restUrl = apiUrlBuilder.buildProfitShareDetailUrl(accountId);
        return restTemplate.getForObject(restUrl, Double.class);
    }
    
    /**
     * 
     * 分成日志分页
     * @param ajustprofitlogBo
     * @return
     * @author: 姜涛
     */
    public PaginationBo<AjustProfitLogRespDto> list(AjustProfitLogReqDto req, int pageNo, int pageSize){
        String restUrl = apiUrlBuilder.buildProfitShareListUrl(req, pageNo, pageSize);
        AjustProfitLogResp resp = restTemplate.getForObject(restUrl,AjustProfitLogResp.class);
        if(resp == null || resp.getStatus() != HttpStatus.SC_OK ){
            //TODO shiyu 
        }
        return resp.getData();
    }
    
    
}
