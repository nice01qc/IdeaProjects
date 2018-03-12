package com.wanda.ffanad.crm.dto.resp;

import com.wanda.ffanad.common.utils.CurrencyUtils;

import java.io.Serializable;

/**
 * 第三方dsp对账单汇总 Created by kevin on 16/9/23.
 */
public class DspBillCheckSummaryRespDto implements Serializable {

    /**
     * 时间区间
     */
    private String dateRange;

    /**
     * 请求数
     */
    private Long   requestCount          = 0L;

    /**
     * 有效请求数
     */
    private Long   effectiveRequestCount = 0L;

    /**
     * 展示次数
     */
    private Long   displayCount          = 0L;

    /**
     * 点击次数
     */
    private Long   clickCount            = 0L;

    /**
     * 金额
     */
    private Long   amount                = 0L;

    private String amountStr             = "-";

    /**
     * 余额
     */
    private String balance               = "-";

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public Long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Long requestCount) {
        this.requestCount = requestCount;
    }

    public Long getEffectiveRequestCount() {
        return effectiveRequestCount;
    }

    public void setEffectiveRequestCount(Long effectiveRequestCount) {
        this.effectiveRequestCount = effectiveRequestCount;
    }

    public Long getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(Long displayCount) {
        this.displayCount = displayCount;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getAmountStr() {
        if (amount != null && amount != 0L) {
            return CurrencyUtils.format(amount);
        }
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
