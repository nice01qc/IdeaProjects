package com.wanda.ffanad.crm.integration.finance.resp;

import java.io.Serializable;

public class InvoiceInfoResp implements Serializable {

    /**
     * 
     */
    private static final long  serialVersionUID = 4998484055077651166L;

    private int                status;
    private String             message;
    private InvoiceInfoRespDto data;

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

    public InvoiceInfoRespDto getData() {
        return data;
    }

    public void setData(InvoiceInfoRespDto data) {
        this.data = data;
    }

}
