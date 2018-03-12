package com.wanda.ffanad.crm.integration.finance.resp;

import java.io.Serializable;

public class AdditionalInvoiceInfoRespDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -724901224572959955L;
    
    private String invoiceTypeName;

    private Integer additionalInvoiceId;

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

    private String invoiceType;

    private Integer accountId;


    public Integer getAdditionalInvoiceId() {
        return additionalInvoiceId;
    }

    public void setAdditionalInvoiceId(Integer additionalInvoiceId) {
        this.additionalInvoiceId = additionalInvoiceId;
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

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getInvoiceTypeName() {
        return invoiceTypeName;
    }

    public void setInvoiceTypeName(String invoiceTypeName) {
        this.invoiceTypeName = invoiceTypeName;
    }
}
