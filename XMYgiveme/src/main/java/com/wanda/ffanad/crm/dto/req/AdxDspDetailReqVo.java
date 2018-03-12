/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.dto.req;

import com.wanda.ffanad.base.dal.entities.AccountEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 类AdxDspDetail.java的实现描述：ADX 新增dsp detail
 * 
 * @author liuzhenkai1 2016年10月24日 上午9:20:52
 */
public class AdxDspDetailReqVo {
    /**
     * id
     */
    private String        id;
    /**
     * dsp名称
     */
    @Length
    private String        name;
    /**
     * 接入方式
     */
    private String        accessMode;
    /**
     * 第三方dsp给飞凡分配的appkey
     */
    private String        appkey;
    /**
     * 第三方dsp给飞凡分配的appsecret
     */
    private String        appsecret;

    /**
     * 合同规定的cpm结算价格
     */
    private String        cpm;

    /**
     * feedback地址
     */
    private String        feedbackUrl;

    /**
     * 是否事前创意审核Y/N
     */
    private String        creationReview;

    /**
     * 操作用户
     */
    private AccountEntity account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
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

    public String getCpm() {
        return cpm;
    }

    public void setCpm(String cpm) {
        this.cpm = cpm;
    }

    public String getFeedbackUrl() {
        return feedbackUrl;
    }

    public void setFeedbackUrl(String feedbackUrl) {
        this.feedbackUrl = feedbackUrl;
    }

    public String getCreationReview() {
        return creationReview;
    }

    public void setCreationReview(String creationReview) {
        this.creationReview = creationReview;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
