/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.dto;

import java.io.Serializable;

/**
 * 类FinanceAccountBillCheckDto.java的实现描述：TODO 类实现描述 财务对账单DTO
 * 
 * @author yanji 2016年6月7日 下午4:31:00
 */
public class FinanceAccountBillCheckDto implements Serializable {

    private static final long serialVersionUID = 3307402530035882014L;

    private Long              accountId;                              //账户ID
    private String            accountMail;                            //用户邮箱
    private String            appName;                                //app名称
    private String            company;                                //公司名称
    private String            resourcePostion;                        //资源位名称,仅资源方对账单有值
    private String            date;                                   //账单详情日期.仅详情时有值   
    private Long              effectivePromoteCount;                  //有效投放,仅需求方对账单有值
    private Long              displayCount;                           //Long,展示数
    private Long              clickCount;                             //Long,点击数
    private String            ctr;                                    //总点击/总展示,为百分数
    private Long              cpmDisplayCount;                        //Long,CPM展示数目
    private double            cpmAmount;                              //Long,通过CPM得到的收入(单位：分)
    private Long              cpcClickCount;                          //Long,CPC点击数目
    private double            cpcAmount;                              //Long,通过CPC得到的收入(单位：分)
    private double            occupyAmount;                           //Long,通过独占得到的收入(单位：分)
    private double            amount;                                 //Long,总价(单位：分)
    private int               payStatus;                              //byte,打款状态(0未打款，1已打款) 仅资源方对账单有值

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccountMail() {
        return accountMail;
    }

    public void setAccountMail(String accountMail) {
        this.accountMail = accountMail;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getResourcePostion() {
        return resourcePostion;
    }

    public void setResourcePostion(String resourcePostion) {
        this.resourcePostion = resourcePostion;
    }

    public Long getEffectivePromoteCount() {
        return effectivePromoteCount;
    }

    public void setEffectivePromoteCount(Long effectivePromoteCount) {
        this.effectivePromoteCount = effectivePromoteCount;
    }

    public Long getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(Long displayCount) {
        this.displayCount = displayCount;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Long getCpmDisplayCount() {
        return cpmDisplayCount;
    }

    public void setCpmDisplayCount(Long cpmDisplayCount) {
        this.cpmDisplayCount = cpmDisplayCount;
    }

    public String getCtr() {
        return ctr;
    }

    public void setCtr(String ctr) {
        this.ctr = ctr;
    }

    public double getCpmAmount() {
        return cpmAmount;
    }

    public void setCpmAmount(double cpmAmount) {
        this.cpmAmount = cpmAmount;
    }

    public Long getCpcClickCount() {
        return cpcClickCount;
    }

    public void setCpcClickCount(Long cpcClickCount) {
        this.cpcClickCount = cpcClickCount;
    }

    public double getCpcAmount() {
        return cpcAmount;
    }

    public void setCpcAmount(double cpcAmount) {
        this.cpcAmount = cpcAmount;
    }

    public double getOccupyAmount() {
        return occupyAmount;
    }

    public void setOccupyAmount(double occupyAmount) {
        this.occupyAmount = occupyAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

}
