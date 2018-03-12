package com.wanda.ffanad.crm.integration.account.req;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AjustProfitLogReqDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6842046570367871190L;

    private String            startDate;
    private String            endDate;
    private Integer           logId;

    private Integer           accountId;

    private BigDecimal        originalShare;

    private BigDecimal        modifyShare;

    private Date              modifyTime;

    private Integer           adminId;
    private String            accountEmail;
    private String            adminEmail;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getOriginalShare() {
        return originalShare;
    }

    public void setOriginalShare(BigDecimal originalShare) {
        this.originalShare = originalShare;
    }

    public BigDecimal getModifyShare() {
        return modifyShare;
    }

    public void setModifyShare(BigDecimal modifyShare) {
        this.modifyShare = modifyShare;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

}
