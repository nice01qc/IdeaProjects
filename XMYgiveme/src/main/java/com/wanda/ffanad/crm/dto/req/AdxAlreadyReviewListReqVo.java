/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.dto.req;

/**
 * 类AdxAlreadyReviewListReqVo.java的实现描述：已审核列表页参数
 * 
 * @author liuzhenkai1 2016年10月26日 下午6:43:56
 */
public class AdxAlreadyReviewListReqVo {

    /**
     * 审核状态1成功2退回
     */
    private String reviewStatus;
    /**
     * dsp名称
     */
    private String dspName;

    /**
     * 是否选择更新时间
     */
    private String hasUpdateTime;

    /**
     * 更新开始时间
     */
    private String updateTimeStart;

    /**
     * 更新结束时间
     */
    private String updateTimeEnd;

    /**
     * 是否选择投放时间，0未选择1选择
     */
    private String hasThrowTime;

    /**
     * 投放开始时间
     */
    private String throwTimeStart;

    /**
     * 投放结束时间
     */
    private String throwTimeEnd;

    /**
     * 图片链接
     */
    private String url;

    /**
     * 点击跳转链接
     */
    private String landingPageUrl;

    /**
     * 是否选择审核时间0未1有
     */
    private String hasReviewTime;
    /**
     * 审核时间开始
     */
    private String reviewTimeStart;
    /**
     * 审核时间结束
     */
    private String reviewTimeEnd;
    private int    page  = 1;
    private int    limit = 20;
    private int    pageStartNo;

    public String getDspName() {
        return dspName;
    }

    public void setDspName(String dspName) {
        this.dspName = dspName;
    }

    public String getHasUpdateTime() {
        return hasUpdateTime;
    }

    public void setHasUpdateTime(String hasUpdateTime) {
        this.hasUpdateTime = hasUpdateTime;
    }

    public String getUpdateTimeStart() {
        return updateTimeStart;
    }

    public void setUpdateTimeStart(String updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    public String getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    public void setUpdateTimeEnd(String updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    public String getHasThrowTime() {
        return hasThrowTime;
    }

    public void setHasThrowTime(String hasThrowTime) {
        this.hasThrowTime = hasThrowTime;
    }

    public String getThrowTimeStart() {
        return throwTimeStart;
    }

    public void setThrowTimeStart(String throwTimeStart) {
        this.throwTimeStart = throwTimeStart;
    }

    public String getThrowTimeEnd() {
        return throwTimeEnd;
    }

    public void setThrowTimeEnd(String throwTimeEnd) {
        this.throwTimeEnd = throwTimeEnd;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPageStartNo() {
        return pageStartNo;
    }

    public void setPageStartNo(int pageStartNo) {
        this.pageStartNo = pageStartNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLandingPageUrl() {
        return landingPageUrl;
    }

    public void setLandingPageUrl(String landingPageUrl) {
        this.landingPageUrl = landingPageUrl;
    }

    public String getHasReviewTime() {
        return hasReviewTime;
    }

    public void setHasReviewTime(String hasReviewTime) {
        this.hasReviewTime = hasReviewTime;
    }

    public String getReviewTimeStart() {
        return reviewTimeStart;
    }

    public void setReviewTimeStart(String reviewTimeStart) {
        this.reviewTimeStart = reviewTimeStart;
    }

    public String getReviewTimeEnd() {
        return reviewTimeEnd;
    }

    public void setReviewTimeEnd(String reviewTimeEnd) {
        this.reviewTimeEnd = reviewTimeEnd;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
