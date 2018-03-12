package com.wanda.ffanad.crm.integration.finance.resp;

import java.io.Serializable;

public class RechargeInfoResp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3716726560859922453L;
    private int                status;
    private String             message;
    private RechargeInfoRespDto data;
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
    public RechargeInfoRespDto getData() {
        return data;
    }
    public void setData(RechargeInfoRespDto data) {
        this.data = data;
    }
    
    
}
