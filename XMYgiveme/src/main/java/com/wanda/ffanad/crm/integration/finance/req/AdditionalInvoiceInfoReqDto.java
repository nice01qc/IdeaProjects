package com.wanda.ffanad.crm.integration.finance.req;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class AdditionalInvoiceInfoReqDto implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 3396324435641630169L;

    private Integer additionalInvoiceId;

    @NotBlank(message="公司名称不能为空")
    private String companyName;

    @NotBlank(message="公司地址不能为空")
    private String companyAddress;
    
    @NotBlank(message="公司电话不能为空")
    private String companyTel;
    
    @NotBlank(message="纳税人编号不能为空")
    private String taxpayerNumber;

    @NotBlank(message="开户银行不能为空")
    private String bankName;

    @NotBlank(message="开户银行账号不能为空")
    private String bankAccount;

    @NotBlank(message="营业许可证文件不能为空")
    private String businessLicenseId;

    @NotBlank(message="组织机构代码证文件不能为空")
    private String organizationCodeCertificateId;

    @NotBlank(message="税务登记证文件不能为空")
    private String taxRegistrationCertificateId;

    @NotBlank(message="开户银行许可证文件不能为空")
    private String bankAccountsPermitsId;

    @NotBlank(message="一般纳税人资格证文件不能为空")
    private String generalTaxpayerQualificationId;

    @NotBlank(message="发票类型不能为空")
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
    
}
