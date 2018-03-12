package com.wanda.ffanad.crm.integration.analysis.resp;

/**
 * 类DSPStatisticsResponse.java的实现描述：按资源位统计数据
 * 
 * @author Yao 2016年9月21日 下午3:48:59
 */
public class DspResStatResponseData {

    /**
     * 资源位ID
     */
    private Long   resourceId;

    /**
     * 资源位名称
     */
    private String resourceName;

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
     * 带链接的广告消耗的金额
     */
    private Long   linkAmount       = 0L;

    /**
     * 总共消耗的金额
     */
    private Long   amount           = 0L;

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

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
