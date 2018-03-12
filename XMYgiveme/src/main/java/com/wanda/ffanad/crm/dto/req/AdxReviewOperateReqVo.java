/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.dto.req;

import java.util.List;

import com.wanda.ffanad.base.dal.entities.AccountEntity;

/**
 * 类AdxReviewOperateVo.java的实现描述：操作审核
 * 
 * @author liuzhenkai1 2016年10月24日 下午7:44:23
 */
public class AdxReviewOperateReqVo {

    /**
     * id拼接而成的字符串，   , 分隔
     */
    private String        idString;

    /**
     * 主键ID
     */
    private List<String>  idList;
    /**
     * 状态，1审核通过2审核不通过'
     */
    private String        status;

    /**
     * 不通过原因
     */
    private String        noAdoptMsg;

    /**
     * 问题分类
     */
    private String        questionClass;

    /**
     * 问题
     */
    private String        question;

    /**
     * 操作用户
     */
    private AccountEntity account;

    /**
     * 审核人ID
     */
    private String        accountId;
    /**
     * 审核人名称（用邮箱）
     */
    private String        accountName;

    public List<String> getIdList() {
        return idList;
    }

    public void setIdList(List<String> idList) {
        this.idList = idList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public String getNoAdoptMsg() {
        return noAdoptMsg;
    }

    public void setNoAdoptMsg(String noAdoptMsg) {
        this.noAdoptMsg = noAdoptMsg;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getQuestionClass() {
        return questionClass;
    }

    public void setQuestionClass(String questionClass) {
        this.questionClass = questionClass;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }
}
