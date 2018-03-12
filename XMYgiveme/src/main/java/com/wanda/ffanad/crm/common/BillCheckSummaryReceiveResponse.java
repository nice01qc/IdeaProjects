/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.common;

import com.wanda.ffanad.crm.dto.resp.DspBillCheckSummaryRespDto;
import org.springframework.http.HttpStatus;

/**
 * 接收从数据统计系统中来的数据
 */
public class BillCheckSummaryReceiveResponse {
    private int                       status  = HttpStatus.OK.value();

    private String                    message = HttpStatus.OK.name();

    private DspBillCheckSummaryRespDto data;

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

    public DspBillCheckSummaryRespDto getData() {
        return data;
    }

    public void setData(DspBillCheckSummaryRespDto data) {
        this.data = data;
    }
}
