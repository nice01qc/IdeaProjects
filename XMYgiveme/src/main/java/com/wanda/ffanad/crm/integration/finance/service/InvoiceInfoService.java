/**
 * 
 */
package com.wanda.ffanad.crm.integration.finance.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wanda.ffanad.base.dal.entities.InvoiceInfoEntity;
import com.wanda.ffanad.base.error.AdExceptionCode;
import com.wanda.ffanad.base.error.AdRuntimeException;
import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.common.utils.JsonUtils;
import com.wanda.ffanad.crm.common.RestResponse;
import com.wanda.ffanad.crm.integration.common.ApiUrlBuilder;
import com.wanda.ffanad.crm.integration.finance.resp.InvoiceInfoResp;
import com.wanda.ffanad.crm.integration.finance.resp.InvoiceInfoRespDto;

/**
 * 发票service
 * 
 * @author 姜涛
 */
@Service
public class InvoiceInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceInfoService.class);

    @Autowired
    private RestTemplate        restTemplate;
    @Autowired
    private ApiUrlBuilder       apiUrlBuilder;

    public void confirmInvoice(Integer invoiceId) {
        String restUrl = apiUrlBuilder.buildConfirmInvoiceURL(invoiceId);
        RestResponse resp = restTemplate.postForObject(restUrl, null, RestResponse.class);
        if (resp == null || resp.getStatus() != org.apache.http.HttpStatus.SC_OK) {
            LOGGER.error("请求出错，入参：{},原因：invoiceId:{}", invoiceId, resp == null ? "调用外部系统失败" : resp.getMessage());
            throw new AdRuntimeException(AdExceptionCode.SystemMaintaining, resp == null ? "调用外部系统失败" : resp.getMessage());
        }
    }

    public void sendInvoice(InvoiceInfoEntity entity) {
        String restUrl = apiUrlBuilder.buildSendInvoiceURL(entity);
        RestResponse resp = restTemplate.postForObject(restUrl, entity, RestResponse.class);
        if (resp == null || resp.getStatus() != org.apache.http.HttpStatus.SC_OK) {
            LOGGER.error("请求出错，入参：{},原因：{}", JsonUtils.toJSON(entity), resp == null ? "调用外部系统失败" : resp.getMessage());
            throw new AdRuntimeException(AdExceptionCode.SystemMaintaining, resp == null ? "调用外部系统失败" : resp.getMessage());
        }
    }
    
    public InvoiceInfoRespDto getByRechargeId(Integer rechargeId) {
        String restUrl = apiUrlBuilder.buildInvoiceDetailUrl(rechargeId);
        InvoiceInfoResp resp = restTemplate.getForObject(restUrl, InvoiceInfoResp.class);
        InvoiceInfoRespDto data = null;
        if (resp == null || resp.getStatus() != org.apache.http.HttpStatus.SC_OK || (data = resp.getData()) == null) {
            LOGGER.error("请求出错，入参：rechargeId{},原因：{}", rechargeId, resp == null ? "调用外部系统失败" : resp.getMessage());
            return null;
        }
        data.setInvoiceAmountStr(CurrencyUtils.format(data.getInvoiceAmount()));
        return data;
    }

}
