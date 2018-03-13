package com.wanda.ffanad.crm.integration.finance.req;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class InvoiceInfoCreateReqDto {

    @NotNull(message="充值Id不能为空！")
    private Integer rechargeInfoId;

    @NotBlank(message="发票类型不能为空！")
    private String  invoiceCategory;

    @NotBlank(message="发票内容不能为空！")
    private String  invoiceContent;

    @NotBlank(message="收货人不能为空！")
    private String  consigneeName;

    @NotBlank(message="联系方式不能为空！")
    private String  consigneeTel;

    @NotBlank(message="联系地址不能为空！")
    private String  consigneeAddress;
    
    
    @NotNull(message="申请人不能为空！")
    private Integer applyUserId;
    
    private Integer invoiceId;

    private String expressCompanyName;

    private String expressNumber;

    private Date applyTime;

    private String companyName;

    private String companyAddress;

    private String companyTel;

    private String taxpayerNumber;

    private String bankName;

    private String bankAccount;

    private String businessLicenseId;

    private String organizationCodeCertificateId;

    private String taxRegistrationCertificateId;

    private String bankAccountsPermitsId;

    private String generalTaxpayerQualificationId;

    private Long invoiceAmount;

    private String isDeleted;

    private Integer cancelUserId;

    private Date cancelTime;

    public Integer getRechargeInfoId() {
        return rechargeInfoId;
    }

    public void setRechargeInfoId(Integer rechargeInfoId) {
        this.rechargeInfoId = rechargeInfoId;
    }

    public String getInvoiceCategory() {
        return invoiceCategory;
    }

    public void setInvoiceCategory(String invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeTel() {
        return consigneeTel;
    }

    public void setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public Integer getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(Integer applyUserId) {
        this.applyUserId = applyUserId;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getTaxpayerNumber() {
        return taxpayerNumber;
    }

    public void setTaxpayerNumber(String taxpayerNumber) {
        this.taxpayerNumber = taxpayerNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBusinessLicenseId() {
        return businessLicenseId;
    }

    public void setBusinessLicenseId(String businessLicenseId) {
        this.businessLicenseId = businessLicenseId;
    }

    public String getOrganizationCodeCertificateId() {
        return organizationCodeCertificateId;
    }

    public void setOrganizationCodeCertificateId(String organizationCodeCertificateId) {
        this.organizationCodeCertificateId = organizationCodeCertificateId;
    }

    public String getTaxRegistrationCertificateId() {
        return taxRegistrationCertificateId;
    }

    public void setTaxRegistrationCertificateId(String taxRegistrationCertificateId) {
        this.taxRegistrationCertificateId = taxRegistrationCertificateId;
    }

    public String getBankAccountsPermitsId() {
        return bankAccountsPermitsId;
    }

    public void setBankAccountsPermitsId(String bankAccountsPermitsId) {
        this.bankAccountsPermitsId = bankAccountsPermitsId;
    }

    public String getGeneralTaxpayerQualificationId() {
        return generalTaxpayerQualificationId;
    }

    public void setGeneralTaxpayerQualificationId(String generalTaxpayerQualificationId) {
        this.generalTaxpayerQualificationId = generalTaxpayerQualificationId;
    }

    public Long getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Long invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getCancelUserId() {
        return cancelUserId;
    }

    public void setCancelUserId(Integer cancelUserId) {
        this.cancelUserId = cancelUserId;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

}