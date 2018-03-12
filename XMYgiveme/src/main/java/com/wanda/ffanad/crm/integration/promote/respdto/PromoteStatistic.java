package com.wanda.ffanad.crm.integration.promote.respdto;

/**
 * 投放统计信息 类PromoteStatistic.java的实现描述：TODO 类实现描述
 * 
 * @author czs 2016年6月11日 下午12:58:41
 */
public class PromoteStatistic {
    private Integer appId;
    /**
     * 日期 （毫秒）
     */
    private String  date;

    /**
     * 请求次数
     */
    private Long    requestCount;

    /**
     * 展示次数
     */
    private Long    displayCount;

    /**
     * 点击次数
     */
    private Long    clickCount;

    /**
     * 总价（单位：分）
     */
    private Long    amount;

    /**
     * 千次展示成本（单位：分）
     */
    private Long    cpm;

    /**
     * 千次点击成本（单位：分）
     */
    private Long    cpc;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Long requestCount) {
        this.requestCount = requestCount;
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

    public Long getCpm() {
        return cpm;
    }

    public void setCpm(Long cpm) {
        this.cpm = cpm;
    }

    public Long getCpc() {
        return cpc;
    }

    public void setCpc(Long cpc) {
        this.cpc = cpc;
    }
}
