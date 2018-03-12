package com.wanda.ffanad.crm.integration.finance.resp;

import java.io.Serializable;

import com.wanda.ffanad.core.common.PaginationBo;

public class RechargeInfoQueryResp implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2560605434473065516L;
    private int                status;
    private String             message;
    private PaginationBo<RechargeInfoRespDto> data;
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
    public PaginationBo<RechargeInfoRespDto> getData() {
        return data;
    }
    public void setData(PaginationBo<RechargeInfoRespDto> data) {
        this.data = data;
    }
    
    
    

}
