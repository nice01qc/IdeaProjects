/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.dto.resp;

/**
 * 类LandingPageDetailDto.java的实现描述：着陆页详情dto
 * 
 * @author liuzhenkai1 2016年11月30日 上午11:37:20
 */
public class LandingPageDetailDto {
    /**
     * 创意名称
     */
    private String creationName;
    /**
     * 资源位类型
     */
    private String resourceType;
    /**
     * 着陆页标题
     */
    private String pageTitle;
    /**
     * 着陆页正文
     */
    private String pageBody;
    /**
     * 着陆页Url
     */
    private String pageUrl;
    /**
     * 创建人id
     */
    private String userId;
    /**
     * 着陆页状态
     */
    private String status;
    /**
     * 审核状态
     */
    private String auditStatus;
    /**
     * 审核意见
     */
    private String auditReason;
    /**
     * 创建人
     */
    private String userName;
    

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageBody() {
        return pageBody;
    }

    public void setPageBody(String pageBody) {
        this.pageBody = pageBody;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
