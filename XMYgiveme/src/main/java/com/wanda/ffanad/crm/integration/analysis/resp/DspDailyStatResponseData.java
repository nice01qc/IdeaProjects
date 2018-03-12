package com.wanda.ffanad.crm.integration.analysis.resp;

/**
 * 类DSPStatisticsResponse.java的实现描述：按天统计数据
 * 
 * @author Yao 2016年9月21日 下午3:48:59
 */
public class DspDailyStatResponseData {

    /**
     * 数据日期(毫秒数)
     */
    private Long date;

    /**
     * 带链接的投放的展示次数
     */
    private Long linkDisplayCount = 0L;

    /**
     * 总的展示计数
     */
    private Long displayCount     = 0L;

    /**
     * 点击次数，只有带链接的投放才会有点击量
     */
    private Long clickCount       = 0L;

    /**
     * 带链接的广告消耗的金额
     */
    private Long linkAmount       = 0L;

    /**
     * 总共消耗的金额
     */
    private Long amount           = 0L;

    public Long getLinkDisplayCount() {
        return linkDisplayCount;
    }

    public void setLinkDisplayCount(Long linkDisplayCount) {
        this.linkDisplayCount = linkDisplayCount;
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

    public Long getLinkAmount() {
        return linkAmount;
    }

    public void setLinkAmount(Long linkAmount) {
        this.linkAmount = linkAmount;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
