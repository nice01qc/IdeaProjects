/**
 * 
 */
package com.wanda.ffanad.crm.integration.finance.req;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 姜涛
 */
public class RechargeInfoReqDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5948326254611103916L;
    private String              rechargeStartDate;
    private String              rechargeEndDate;
    private String            accountEmail;
    private String            operator;

    private Integer           rechargeId;

    private Long              invoiceAmount;

    private Date              rechargeTime;

    private Date              revokerTime;

    private Long              proceedsAmount;

    private Integer           accountId;

    private Integer           operatorId;

    private Integer           revokerId;

    private String           status;

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


    
    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRechargeStartDate() {
        return rechargeStartDate;
    }

    public void setRechargeStartDate(String rechargeStartDate) {
        this.rechargeStartDate = rechargeStartDate;
    }

    public String getRechargeEndDate() {
        return rechargeEndDate;
    }

    public void setRechargeEndDate(String rechargeEndDate) {
        this.rechargeEndDate = rechargeEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
