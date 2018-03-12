package com.wanda.ffanad.crm.integration.finance.resp;

import java.io.Serializable;

public class AdditionalInvoiceInfoResp implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8605263819854044069L;
    
    private int status;
    private String message;
    private AdditionalInvoiceInfoRespDto data = new AdditionalInvoiceInfoRespDto();
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
    public AdditionalInvoiceInfoRespDto getData() {
        return data;
    }
    public void setData(AdditionalInvoiceInfoRespDto data) {
        this.data = data;
    }
    
    

}
