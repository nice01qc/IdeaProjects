/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.dto.req;

import com.wanda.ffanad.base.dal.entities.AccountEntity;

/**
 * 类AdxDspStatusVo.java的实现描述：adx dsp vo
 * 
 * @author liuzhenkai1 2016年10月24日 下午6:45:58
 */
public class AdxDspStatusReqVo {
    /**
     * id
     */
    private String        id;

    /**
     * status
     */
    private String        status;
    /**
     * 操作用户
     */
    private AccountEntity account;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
