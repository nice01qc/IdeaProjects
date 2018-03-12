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

import com.wanda.ffanad.crm.dto.resp.DspBillCheckDetailRespDto;

/**
 * 接收从数据统计系统中来的数据
 */
public class BillCheckListReceiveResponse {
    private int                             status  = HttpStatus.OK.value();

    private String                          message = HttpStatus.OK.name();

    private List<DspBillCheckDetailRespDto> data;

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

    public List<DspBillCheckDetailRespDto> getData() {
        return data;
    }

    public void setData(List<DspBillCheckDetailRespDto> data) {
        this.data = data;
    }
}
