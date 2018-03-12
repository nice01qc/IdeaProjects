package com.wanda.ffanad.crm.integration.analysis.resp;

/**
 * 类DSPStatisticsResponse.java的实现描述：按dsp投放统计
 * 
 * @author Yao 2016年9月21日 下午3:48:59
 */
public class DspStat {

    /**
     * DSP代码，见枚举DSPEnum.java
     */
    private Short  dspCode;

    /**
     * dsp名称
     */
    private String dspName;

    /**
     * 带链接的投放的展示次数
     */
    private Long   linkDisplayCount = 0L;

    /**
     * 总的展示计数
     */
    private Long   displayCount     = 0L;

    /**
     * 点击次数，只有带链接的投放才会有点击量
     */
    private Long   clickCount       = 0L;

    /**
     * 点击率
     */
    private Double clickRate        = 0.00;

    /**
     * 带链接的广告消耗的金额
     */
    private Double linkAmount       = 0.00;

    /**
     * 总共消耗的金额
     */
    private Double amount           = 0.00;

    /**
     * 千次展示成本
     */
    private Double cpmAmount        = 0.00;

    /**
     * 平均点击成本
     */
    private Double cpcAmount        = 0.00;

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

    public Double getLinkAmount() {
        return linkAmount;
    }

    public void setLinkAmount(Double linkAmount) {
        this.linkAmount = linkAmount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getClickRate() {
        return clickRate;
    }

    public void setClickRate(Double clickRate) {
        this.clickRate = clickRate;
    }

    public Double getCpmAmount() {
        return cpmAmount;
    }

    public void setCpmAmount(Double cpmAmount) {
        this.cpmAmount = cpmAmount;
    }

    public Double getCpcAmount() {
        return cpcAmount;
    }

    public void setCpcAmount(Double cpcAmount) {
        this.cpcAmount = cpcAmount;
    }

    public Short getDspCode() {
        return dspCode;
    }

    public void setDspCode(Short dspCode) {
        this.dspCode = dspCode;
    }

    public String getDspName() {
        return dspName;
    }

    public void setDspName(String dspName) {
        this.dspName = dspName;
    }
}
