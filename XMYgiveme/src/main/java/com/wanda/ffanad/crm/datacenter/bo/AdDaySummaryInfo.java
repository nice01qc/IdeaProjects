package com.wanda.ffanad.crm.datacenter.bo;

import java.io.Serializable;

/**
 *  广告每日统计数据
 * 类AdSummaryInfo.java的实现描述：TODO 类实现描述 
 * @author czs 2016年6月7日 下午1:40:00
 */
public class AdDaySummaryInfo implements Serializable{
    
    private Long  showNum;//广告展示 量 
    private Long  clickNum;//广告点击 量               
    private Double  clickRate;//广告 量点击 率 
    private Double  cpm;//千 次点击 成本
    private Double   cpc;//单次点击 成本 
    private String  time;//统计时间  
    private Double   consume;//花费
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
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public Double getConsume() {
        return consume;
    }
    public void setConsume(Double consume) {
        this.consume = consume;
    }
    
    
}
