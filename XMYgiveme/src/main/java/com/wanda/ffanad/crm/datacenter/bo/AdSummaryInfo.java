package com.wanda.ffanad.crm.datacenter.bo;

import java.io.Serializable;

/**
 * 用户广告统计数据 类AdSummaryInfo.java的实现描述：TODO 类实现描述
 * 
 * @author czs 2016年6月7日 下午1:40:00
 */
public class AdSummaryInfo implements Serializable {

    private Long   showNum   = 0L; //广告展示 量
    private Long   clickNum  = 0L; //广告点击 量
    private Double clickRate = 0d;//广告 量点击 率 
    private Double cpm       = 0d; //千 次点击 成本
    private Double cpc       = 0d; //单次点击 成本 
    private Double consume   = 0d; //花费
    private String time;           //统计时间 

    public Long getShowNum() {
        return showNum;
    }

    public void setShowNum(Long showNum) {
        this.showNum = showNum;
    }

    public Long getClickNum() {
        return clickNum;
    }

    public void setClickNum(Long clickNum) {
        this.clickNum = clickNum;
    }

    public Double getClickRate() {
        return clickRate;
    }

    public void setClickRate(Double clickRate) {
        this.clickRate = clickRate;
    }

    public Double getCpm() {
        return cpm;
    }

    public void setCpm(Double cpm) {
        this.cpm = cpm;
    }

    public Double getCpc() {
        return cpc;
    }

    public void setCpc(Double cpc) {
        this.cpc = cpc;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
