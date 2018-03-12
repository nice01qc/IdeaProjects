/**
 * 
 */
package com.wanda.ffanad.crm.integration.finance.service;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.wanda.ffanad.base.dal.entities.RechargeInfoEntity;
import com.wanda.ffanad.base.error.AdExceptionCode;
import com.wanda.ffanad.base.error.AdRuntimeException;
import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.common.utils.JsonUtils;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.crm.common.RestResponse;
import com.wanda.ffanad.crm.integration.common.ApiUrlBuilder;
import com.wanda.ffanad.crm.integration.finance.req.RechargeInfoReqDto;
import com.wanda.ffanad.crm.integration.finance.resp.RechargeInfoQueryResp;
import com.wanda.ffanad.crm.integration.finance.resp.RechargeInfoResp;
import com.wanda.ffanad.crm.integration.finance.resp.RechargeInfoRespDto;

/**
 * @author 姜涛
 */
@Service
public class RechargeInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RechargeInfoService.class);

    @Autowired
    private RestTemplate        restTemplate;
    @Autowired
    private ApiUrlBuilder       apiUrlBuilder;

    public void recharge(RechargeInfoEntity recharge) {
        String restUrl = apiUrlBuilder.buildRechargeUrl(recharge);
        RestResponse resp = restTemplate.postForObject(restUrl, recharge, RestResponse.class, recharge.getAccountId());
        if (resp == null || resp.getStatus() != org.apache.http.HttpStatus.SC_OK) {
            LOGGER.error("请求出错，入参：{},原因：{}", JsonUtils.toJSON(recharge), resp == null ? "调用外部系统失败" : resp.getMessage());
            throw new AdRuntimeException(AdExceptionCode.SystemMaintaining,
                    resp == null ? "调用外部系统失败" : resp.getMessage());
        }
    }

    public void cancelRecharge(RechargeInfoEntity recharge) {
        String restUrl = apiUrlBuilder.buildCancelRechargeUrl(recharge);
        RestResponse resp = restTemplate.postForObject(restUrl, recharge, RestResponse.class);
        if (resp == null || resp.getStatus() != org.apache.http.HttpStatus.SC_OK) {
            LOGGER.error("请求出错，入参：{},原因：{}", JsonUtils.toJSON(recharge), resp == null ? "调用外部系统失败" : resp.getMessage());
            throw new AdRuntimeException(AdExceptionCode.SystemMaintaining,
                    resp == null ? "调用外部系统失败" : resp.getMessage());
        }
    }

    public PaginationBo<RechargeInfoRespDto> queryRecharge(RechargeInfoReqDto recharge, @PathVariable("pageNumber") int pageNo,
                                                           @PathVariable("pageSize") int pageSize) {
        String restUrl = apiUrlBuilder.buildQueryRechargeUrl(recharge, pageNo, pageSize);
        RechargeInfoQueryResp resp = restTemplate.getForObject(restUrl, RechargeInfoQueryResp.class);
        if (resp == null || resp.getStatus() != HttpStatus.SC_OK) {
            LOGGER.error("请求出错，入参：{},原因：{}", JsonUtils.toJSON(recharge), resp == null ? "调用外部系统失败" : resp.getMessage());
            throw new AdRuntimeException(AdExceptionCode.SystemMaintaining, resp == null ? "调用外部系统失败" : resp.getMessage());
        }
        PaginationBo<RechargeInfoRespDto> data = resp.getData();
        for (RechargeInfoRespDto dto : data.getRows()) {
            dto.setInvoiceAmountStr(CurrencyUtils.format(dto.getInvoiceAmount()));
            dto.setProceedsAmountStr(CurrencyUtils.format(dto.getProceedsAmount()));
        }
        
        return data;
    }

    /**
     * 
     * 通过主键查询充值信息
     * 
     * @param rechargeId
     * @return
     * @author: 姜涛
     */
    public RechargeInfoRespDto get(Integer rechargeId) {
        String restUrl = apiUrlBuilder.buildRechargeDetailUrl(rechargeId);
        RechargeInfoResp resp = restTemplate.getForObject(restUrl, RechargeInfoResp.class);
        return resp.getData();
    }
}
