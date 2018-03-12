/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.common;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.wanda.ffanad.crm.integration.finance.resp.FinanceAccountBillCheckRespDto;
import com.wanda.ffanad.crm.integration.finance.resp.FinanceResourceReceiveRespDto;

/**
 * 类FinanceResourceReceiveResponse.java的实现描述：TODO 类实现描述
 * 
 * @author yanji 2016年6月12日 下午1:41:13
 */
public class FinanceResourceReceiveResponse {
    private int                              status  = HttpStatus.OK.value();

    private String                           message = HttpStatus.OK.name();

    private List<FinanceResourceReceiveRespDto> data;

     
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FinanceResourceReceiveRespDto> getData() {
        return data;
    }

    public void setData(List<FinanceResourceReceiveRespDto> data) {
        this.data = data;
    }

    
}
