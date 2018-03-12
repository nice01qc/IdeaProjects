/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.dto.resp;

/**
 * 类AdxDspDetailResVo.java的实现描述：dsp明细相应值
 * 
 * @author liuzhenkai1 2016年10月27日 下午3:30:41
 */
public class AdxDspDetailResVo {
    /**
     * id
     */
    private String id;
    /**
     * dsp名称
     */
    private String name;
    /**
     * dspId
     */
    private String dspId;

    /**
     * token
     */
    private String token;
    /**
     * 接入方式
     */
    private String accessMode;
    /**
     * 协议价
     */
    private String cpm;
    /**
     * 是否需要事先审核
     */
    private String creationReview;
    /**
     * 是否启用
     */
    private String status;

    /**
     * 是否可以更新token
     */
    private String canUpdateToken;

    /**
     * 第三方dsp给飞凡分配的appkey
     */
    private String appkey;
    /**
     * 第三方dsp给飞凡分配的appsecret
     */
    private String appsecret;

    /**
     * feedback地址
     */
    private String feedbackUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDspId() {
        return dspId;
    }

    public void setDspId(String dspId) {
        this.dspId = dspId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    public String getCpm() {
        return cpm;
    }

    public void setCpm(String cpm) {
        this.cpm = cpm;
    }

    public String getCreationReview() {
        return creationReview;
    }

    public void setCreationReview(String creationReview) {
        this.creationReview = creationReview;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCanUpdateToken() {
        return canUpdateToken;
    }

    public void setCanUpdateToken(String canUpdateToken) {
        this.canUpdateToken = canUpdateToken;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getFeedbackUrl() {
        return feedbackUrl;
    }

    public void setFeedbackUrl(String feedbackUrl) {
        this.feedbackUrl = feedbackUrl;
    }

}
