/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.dto.resp;

/**
 * 类AdxDspListResVo.java的实现描述：ADX DSP管理列表页vo
 * 
 * @author liuzhenkai1 2016年10月24日 下午4:28:47
 */
public class AdxDspListResVo {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCanUpdateToken() {
        return canUpdateToken;
    }

    public void setCanUpdateToken(String canUpdateToken) {
        this.canUpdateToken = canUpdateToken;
    }
}
