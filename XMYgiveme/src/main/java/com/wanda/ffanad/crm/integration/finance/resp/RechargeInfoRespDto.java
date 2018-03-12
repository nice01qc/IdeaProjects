package com.wanda.ffanad.crm.integration.finance.resp;

import java.io.Serializable;
import java.util.Date;

public class RechargeInfoRespDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2481062487883338309L;

    private Integer           rechargeId;
    private Long              invoiceAmount;
    private String            invoiceAmountStr;
    private Date              rechargeTime;
    private Date              revokerTime;
    private Long              proceedsAmount;
    private String            proceedsAmountStr;
    private Integer           accountId;
    private Integer           operatorId;
    private Integer           revokerId;
    private String            status;
    private String            statusName;
    private String            operator;
    private String            accountEmail;
    private String            company;
    private String            revoker;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Integer rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Long getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Long invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Date getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(Date rechargeTime) {
        this.rechargeTime = rechargeTime;
    }

    public Date getRevokerTime() {
        return revokerTime;
    }

    public void setRevokerTime(Date revokerTime) {
        this.revokerTime = revokerTime;
    }

    public Long getProceedsAmount() {
        return proceedsAmount;
    }

    public void setProceedsAmount(Long proceedsAmount) {
        this.proceedsAmount = proceedsAmount;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getRevokerId() {
        return revokerId;
    }

    public void setRevokerId(Integer revokerId) {
        this.revokerId = revokerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInvoiceAmountStr() {
        return invoiceAmountStr;
    }

    public void setInvoiceAmountStr(String invoiceAmountStr) {
        this.invoiceAmountStr = invoiceAmountStr;
    }

    public String getProceedsAmountStr() {
        return proceedsAmountStr;
    }

    public void setProceedsAmountStr(String proceedsAmountStr) {
        this.proceedsAmountStr = proceedsAmountStr;
    }

    public String getRevoker() {
        return revoker;
    }

    public void setRevoker(String revoker) {
        this.revoker = revoker;
    }
}
