/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.integration.common;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.wanda.ffanad.base.dal.entities.InvoiceInfoEntity;
import com.wanda.ffanad.base.dal.entities.RechargeInfoEntity;
import com.wanda.ffanad.crm.integration.account.req.AjustProfitLogReqDto;
import com.wanda.ffanad.crm.integration.finance.req.RechargeInfoReqDto;

/**
 * 类ApiUrlBuilder.java的实现描述：TODO 类实现描述
 * 
 * @author yanji 2016年6月7日 下午4:20:37
 */
@Component
public class ApiUrlBuilder {
    @Value(value = "${financeResourceAdURL}")
    private String financeResourceAdURL;

    @Value(value = "${financeResourceDetailAdURL}")
    private String financeResourceDetailAdURL;

    @Value(value = "${financeDetailAdURL}")
    private String financeDetailAdURL;

    @Value(value = "${financeAdURL}")
    private String financeAdURL;
    @Value(value = "${financeResourcePayStatusAdURL}")
    private String financeResourcePayStatusAdURL;

    @Value(value = "${resdashboardAdURL}")
    private String resdashboardAdURL;

    @Value(value = "${resourceURL}")
    private String resourceURL;

    @Value(value = "${ffan.ad.settlement}")
    private String settlementDomain;
    @Value(value = "${settlement.recharge}")
    private String rechargeURL;
    @Value(value = "${settlement.cancelRecharge}")
    private String cancelRechargeURL;
    @Value(value = "${settlement.queryRecharge}")
    private String queryRechargeURL;
    @Value(value = "${settlement.invoiceDetail}")
    private String invoiceDetailURL;
    @Value(value = "${settlement.confirmInvoice}")
    private String confirmInvoiceURL;
    @Value(value = "${settlement.sendInvoice}")
    private String sendInvoiceURL;
    @Value(value = "${settlement.rechargeDetail}")
    private String rechargeDetail;
    @Value(value = "${settlement.updateProfitShare}")
    private String updateProfitShareURL;
    @Value(value = "${settlement.profitShareDetail}")
    private String profitShareDetailURL;
    @Value(value = "${settlement.profitShareList}")
    private String profitShareListURL;
    @Value(value = "${ffan.api.promote.detail}")
    private String adPromoteUri;

    @Value(value = "${statistics.countByAppIds}")
    private String adAppStatisticsURL;

    @Value(value = "${statistics.promote.countByDsp}")
    private String promoteCountByDspURL;

    @Value(value = "${statistics.promote.dailyCount}")
    private String promoteDailyCountUrl;

    @Value(value = "${statistics.promote.countByRes}")
    private String promoteCountByResUrl;

    @Value(value = "${statistics.promote.countByTerminal}")
    private String promoteCountByTerminalUrl;

    @Value(value = "${statistics.promote.countByCity}")
    private String promoteCountByCityUrl;

    @Value(value = "${statistics.countByDateSummary}")
    private String dspPromoteSummaryURL;
    @Value(value = "${statistics.countByDateDetail}")
    private String dspPromoteDetailURL;
    @Value(value = "${dgw.api.queryTagUrl}")
    private String queryTagUrl;

    public String buildProfitShareListUrl(AjustProfitLogReqDto req, int pageNo, int pageSize) {
        StringBuffer url = new StringBuffer(settlementDomain);
        url.append(profitShareListURL);
        MultiValueMap<String, String> params = new HttpHeaders();
        if (!StringUtils.isBlank(req.getStartDate())) {
            params.set("startDate", req.getStartDate());
        }
        if (!StringUtils.isBlank(req.getEndDate())) {
            params.set("endDate", req.getEndDate());
        }
        if (!StringUtils.isBlank(req.getAccountEmail())) {
            params.set("accountEmail", req.getAccountEmail());
        }
        if (!StringUtils.isBlank(req.getAdminEmail())) {
            params.set("adminEmail", req.getAdminEmail());
        }
        Object[] expands = new Object[] { pageNo, pageSize };
        return UriComponentsBuilder.fromHttpUrl(url.toString()).queryParams(params).build().expand(expands).toString();
    }

    public String buildProfitShareDetailUrl(Integer accountId) {
        StringBuffer url = new StringBuffer(settlementDomain);
        url.append(profitShareDetailURL);
        return UriComponentsBuilder.fromHttpUrl(url.toString()).build().expand(accountId).toString();
    }

    public String buildUpdateProfitShareUrl(Integer accountId) {
        StringBuffer url = new StringBuffer(settlementDomain);
        url.append(updateProfitShareURL);
        return UriComponentsBuilder.fromHttpUrl(url.toString()).build().expand(accountId).toString();
    }

    public String buildRechargeDetailUrl(Integer rechargeId) {
        StringBuffer url = new StringBuffer(settlementDomain);
        url.append(rechargeDetail);
        return UriComponentsBuilder.fromHttpUrl(url.toString()).build().expand(rechargeId).toString();
    }

    public String buildCancelRechargeUrl(RechargeInfoEntity recharge) {
        StringBuffer url = new StringBuffer(settlementDomain);
        url.append(cancelRechargeURL);
        return UriComponentsBuilder.fromHttpUrl(url.toString()).build().expand(recharge.getRechargeId()).toString();
    }

    public String buildRechargeUrl(RechargeInfoEntity recharge) {
        StringBuffer url = new StringBuffer(settlementDomain);
        url.append(rechargeURL);
        return UriComponentsBuilder.fromHttpUrl(url.toString()).build().toString();
    }

    public String buildQueryRechargeUrl(RechargeInfoReqDto dto, int pageNo, int pageSize) {
        StringBuffer url = new StringBuffer(settlementDomain);
        url.append(queryRechargeURL);
        MultiValueMap<String, String> params = new HttpHeaders();
        if (!StringUtils.isBlank(dto.getRechargeStartDate())) {
            params.set("rechargeStartDate", dto.getRechargeStartDate());
        }
        if (!StringUtils.isBlank(dto.getRechargeEndDate())) {
            params.set("rechargeEndDate", dto.getRechargeEndDate());
        }
        if (!StringUtils.isBlank(dto.getAccountEmail())) {
            params.set("accountEmail", dto.getAccountEmail());
        }
        if (!StringUtils.isBlank(dto.getOperator())) {
            params.set("operator", dto.getOperator());
        }
        if (!StringUtils.isBlank(dto.getStatus())) {
            params.set("status", dto.getStatus());
        }
        if (dto.getRechargeId() != null) {
            params.set("rechargeId", dto.getRechargeId().toString());
        }
        Object[] expands = new Object[] { pageNo, pageSize };
        return UriComponentsBuilder.fromHttpUrl(url.toString()).queryParams(params).build().expand(expands).toString();
    }

    public String buildInvoiceDetailUrl(Integer rechargeId) {
        StringBuffer url = new StringBuffer(settlementDomain);
        url.append(invoiceDetailURL);
        return UriComponentsBuilder.fromHttpUrl(url.toString()).build().expand(rechargeId).toString();
    }

    public String buildConfirmInvoiceURL(Integer invoiceId) {
        StringBuffer url = new StringBuffer(settlementDomain);
        url.append(confirmInvoiceURL);
        return UriComponentsBuilder.fromHttpUrl(url.toString()).build().expand(invoiceId).toString();
    }

    public String buildSendInvoiceURL(InvoiceInfoEntity entity) {
        StringBuffer url = new StringBuffer(settlementDomain);
        url.append(sendInvoiceURL);
        MultiValueMap<String, String> params = new HttpHeaders();
        if (!StringUtils.isBlank(entity.getExpressCompanyName())) {
            params.set("expressCompanyName", entity.getExpressCompanyName());
        }
        if (!StringUtils.isBlank(entity.getExpressNumber())) {
            params.set("expressNumber", entity.getExpressNumber());
        }
        return UriComponentsBuilder.fromHttpUrl(url.toString()).queryParams(params).build().expand(entity.getInvoiceId())
                .toString();
    }

    public String buildResdashboardAdURL(String sspUserType, String accountId, String terminal, String appType, String appKey,
                                         String beginDate, String endDate) {
        MultiValueMap<String, String> params = new HttpHeaders();
        //StringUtils.isBlank 更安全
        if (!StringUtils.isBlank(sspUserType)) {
            params.set("sspUserType", sspUserType);
        }
        if (!StringUtils.isBlank(accountId)) {
            params.set("accountId", accountId);
        }
        if (!StringUtils.isBlank(terminal)) {
            params.set("terminal", terminal);
        }
        if (!StringUtils.isBlank(appType)) {
            params.set("appType", appType);
        }
        if (!StringUtils.isBlank(appKey)) {
            params.set("appKey", appKey);
        }
        if (!StringUtils.isBlank(beginDate)) {
            params.set("beginDate", beginDate);
        }
        if (!StringUtils.isBlank(endDate)) {
            params.set("endDate", endDate);
        }

        return UriComponentsBuilder.fromHttpUrl(resdashboardAdURL).queryParams(params).build().toString();
    }

    public String buildResourceURL(String resourceId, String beginDate, String endDate) {
        MultiValueMap<String, String> params = new HttpHeaders();
        //StringUtils.isBlank 更安全
        if (!StringUtils.isBlank(resourceId)) {
            params.set("resourceId", resourceId);
        }
        if (!StringUtils.isBlank(beginDate)) {
            params.set("beginDate", beginDate);
        }
        if (!StringUtils.isBlank(endDate)) {
            params.set("endDate", endDate);
        }

        return UriComponentsBuilder.fromHttpUrl(resourceURL).queryParams(params).build().expand(resourceId).toString();
    }

    public String buildFinanceAdUrl(String accountId, String year, String month) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("accountId", accountId);
        params.set("year", year);
        params.set("month", month);

        return UriComponentsBuilder.fromHttpUrl(financeAdURL).queryParams(params).build().toString();
    }

    public String buildFinanceDetailAdUrl(String accountId, String year, String month) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("accountId", accountId);
        params.set("year", year);
        params.set("month", month);

        return UriComponentsBuilder.fromHttpUrl(financeDetailAdURL).queryParams(params).build().toString();
    }

    public String buildFinanceResourceAdUrl(String accountId, String year, String month, String userRole) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("accountId", accountId);
        params.set("year", year);
        params.set("month", month);
        params.set("userRole", userRole);
        return UriComponentsBuilder.fromHttpUrl(financeResourceAdURL).queryParams(params).build().toString();
    }

    public String buildFinanceResourceDetailAdUrl(String appKey, String year, String month) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("appKey", appKey);
        params.set("year", year);
        params.set("month", month);

        return UriComponentsBuilder.fromHttpUrl(financeResourceDetailAdURL).queryParams(params).build().expand(appKey).toString();
    }

    public String buildFinanceResourcePayStatusAdUrl(String accountId, String year, String month) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("accountId", accountId);
        params.set("year", year);
        params.set("month", month);

        return UriComponentsBuilder.fromHttpUrl(financeResourcePayStatusAdURL).queryParams(params).build().toString();
    }

    public String buildAdPromoteUrl(String suffix, Long userId, String startDate, String endDate, Long promoteId, Integer start,
                                    Integer end) {
        StringBuffer url = new StringBuffer(adPromoteUri);
        MultiValueMap<String, String> params = new HttpHeaders();
        if (userId != null) {
            params.set("userId", userId.toString());
        }
        if (start != null) {
            params.set("start", start.toString());
        }
        if (end != null) {
            params.set("end", end.toString());
        }
        if (startDate != null) {
            params.set("startDate", startDate.toString());
        }
        if (endDate != null) {
            params.set("endDate", endDate.toString());
        }
        if (promoteId != null) {
            params.set("promoteId", promoteId.toString());
        }
        url.append("/");
        url.append(suffix);
        return UriComponentsBuilder.fromHttpUrl(url.toString()).queryParams(params).build().toString();
    }

    public String buildAppStatisticsURL() {
        return adAppStatisticsURL;
    }

    /**
     * 第三方dsp对账单汇总
     *
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @return
     */
    public String buildDspPromoteSummaryUrl(Short dspCode, Long beginDate, Long endDate) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("dspCode", dspCode.toString());
        params.set("beginDate", beginDate.toString());
        params.set("endDate", endDate.toString());

        return UriComponentsBuilder.fromHttpUrl(dspPromoteSummaryURL).queryParams(params).build().expand(dspCode).toString();
    }

    /**
     * 第三方dsp对账单详情分页数据
     *
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    public String buildDspPromoteDetailUrl(Short dspCode, Long beginDate, Long endDate, Integer pageNum, Integer pageSize,
                                           Boolean isPageable) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("dspCode", dspCode.toString());
        params.set("beginDate", beginDate.toString());
        params.set("endDate", endDate.toString());
        if (pageNum != null)
            params.set("pageNum", pageNum.toString());
        if (pageSize != null)
            params.set("pageSize", pageSize.toString());
        if (isPageable != null) {
            params.set("isPageable", isPageable.toString());
        }

        return UriComponentsBuilder.fromHttpUrl(dspPromoteDetailURL).queryParams(params).build().expand(dspCode).toString();
    }

    /**
     * 投放分析按DSP统计api请求url组装
     *
     * @param dspCode dsp代码
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public String buildCountByDspURL(Short dspCode, String beginDate, String endDate) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("beginDate", beginDate);
        params.set("endDate", endDate);

        return UriComponentsBuilder.fromHttpUrl(promoteCountByDspURL).queryParams(params).build().expand(dspCode).toString();
    }

    /**
     * 投放分析按日统计api请求url组装
     *
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @return
     */
    public String buildDailyCount(Short dspCode, String beginDate, String endDate) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("beginDate", beginDate);
        params.set("endDate", endDate);

        return UriComponentsBuilder.fromHttpUrl(promoteDailyCountUrl).queryParams(params).build().expand(dspCode).toString();
    }

    /**
     * 按资源位统计api请求url组装
     *
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @return
     */
    public String buildCountByResUrl(Short dspCode, String beginDate, String endDate) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("beginDate", beginDate);
        params.set("endDate", endDate);

        return UriComponentsBuilder.fromHttpUrl(promoteCountByResUrl).queryParams(params).build().expand(dspCode).toString();
    }

    /**
     * 按终端统计api请求url组装
     *
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @return
     */
    public String buildCountByTerminalUrl(Short dspCode, String beginDate, String endDate) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("beginDate", beginDate);
        params.set("endDate", endDate);

        return UriComponentsBuilder.fromHttpUrl(promoteCountByTerminalUrl).queryParams(params).build().expand(dspCode).toString();
    }

    /**
     * 按城市统计api请求url组装
     *
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @return
     */
    public String buildCountByCityUrl(Short dspCode, String beginDate, String endDate) {
        MultiValueMap<String, String> params = new HttpHeaders();
        params.set("beginDate", beginDate);
        params.set("endDate", endDate);

        return UriComponentsBuilder.fromHttpUrl(promoteCountByCityUrl).queryParams(params).build().expand(dspCode).toString();
    }
    
    public String buildQueryTagUrl(String name) {
        StringBuffer url = new StringBuffer(queryTagUrl);
        MultiValueMap<String, String> params = new HttpHeaders();
        if (name != null) {
            params.set("tagName", name.toString());
        }
        return UriComponentsBuilder.fromHttpUrl(url.toString()).queryParams(params).build().toString();
    }
}
