package com.wanda.ffanad.crm.integration.account.resp;

import java.io.Serializable;

import com.wanda.ffanad.core.common.PaginationBo;


public class AjustProfitLogResp implements Serializable {

    /**
     * 
     */
    private static final long    serialVersionUID = 6108976026652287367L;

    private int                  status;

    private String               message;

    private PaginationBo<AjustProfitLogRespDto> data;

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

    public PaginationBo<AjustProfitLogRespDto> getData() {
        return data;
    }

    public void setData(PaginationBo<AjustProfitLogRespDto> data) {
        this.data = data;
    }

}
