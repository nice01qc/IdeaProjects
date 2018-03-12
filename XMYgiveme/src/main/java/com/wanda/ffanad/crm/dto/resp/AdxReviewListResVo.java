/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.dto.resp;

import java.util.ArrayList;
import java.util.List;

/**
 * 类AdxReviewListResVo.java的实现描述：审核vo
 * 
 * @author liuzhenkai1 2016年10月25日 下午3:56:41
 */
public class AdxReviewListResVo {
    /**
     * 图片链接 对应表中URL字段
     */
    private String       url;

    /**
     * 创意的副本URL
     */
    private String       copyUrl;

    /**
     * 点击跳转链接 对应表中landingPageUrl
     */
    private String       landingPageUrl;

    /**
     * 曝光检测链接
     */
    private List<String> showDetectUrlList  = new ArrayList<String>();

    /**
     * 点击监测链接
     */
    private List<String> clickDetectUrlList = new ArrayList<String>();

    /**
     * 更新时间
     */
    private String       updateTime;

    /**
     * 第三方dsp 名称
     */
    private String       dspName;

    /**
     * 审核备注
     */
    private String       reviewMessage;

    /**
     * 创意审核状态
     */
    private String       reviewStatus;

    /**
     * id
     */
    private String       id;

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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDspName() {
        return dspName;
    }

    public void setDspName(String dspName) {
        this.dspName = dspName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getShowDetectUrlList() {
        return showDetectUrlList;
    }

    public void setShowDetectUrlList(List<String> showDetectUrlList) {
        this.showDetectUrlList = showDetectUrlList;
    }

    public List<String> getClickDetectUrlList() {
        return clickDetectUrlList;
    }

    public void setClickDetectUrlList(List<String> clickDetectUrlList) {
        this.clickDetectUrlList = clickDetectUrlList;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

    public void setReviewMessage(String reviewMessage) {
        this.reviewMessage = reviewMessage;
    }

    public String getCopyUrl() {
        return copyUrl;
    }

    public void setCopyUrl(String copyUrl) {
        this.copyUrl = copyUrl;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
