package com.wanda.ffanad.crm.integration.account.resp;

import java.math.BigDecimal;
import java.util.Date;

public class AjustProfitLogRespDto {

    private static final long serialVersionUID = -6572232986858742015L;

    private Integer           logId;

    private Integer           accountId;

    private BigDecimal        originalShare;

    private BigDecimal        modifyShare;

    private Date              modifyTime;

    private Integer           adminId;
    /** 被调整email **/
    private String            accountEmail;
    private String            accountCompany;
    /**
     * 操作人email
     */
    private String            adminEmail;

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

    public String getAccountCompany() {
        return accountCompany;
    }

    public void setAccountCompany(String accountCompany) {
        this.accountCompany = accountCompany;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

}
