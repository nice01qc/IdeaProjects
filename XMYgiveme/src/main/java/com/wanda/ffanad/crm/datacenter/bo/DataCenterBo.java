package com.wanda.ffanad.crm.datacenter.bo;

import java.io.Serializable;
import java.util.List;

import com.wanda.ffanad.core.common.PaginationBo;

/**
 * 用户数据中心bo 类DataCenter.java的实现描述：TODO 类实现描述
 * 
 * @author czs 2016年6月7日 上午11:18:17
 */
public class DataCenterBo implements Serializable {

    private Long                           userId;          //用户ID

    private String                         countType;       //展示类型

    private Long                           promoteId;       //投放id

    private Integer                        limit  = 20;     //一页 多少条 

    private Integer                        offset = 0;      //页码   

    private String                         promoteName;     //投放名称

    private Integer                        days;            //查询时间范围

    private List<Promote>                  adData;          //审核通过的 投放 

    private AdAccountInfo                  adAccountData;   //广告账户信息  

    private AdSummaryInfo                  adSummaryData;   //用户广告统计数据 

    private PaginationBo<AdDaySummaryInfo> adDaySummaryDataPage;//用户广告每日统计数据分页查询 
    
    private List<AdDaySummaryInfo> adDaySummaryData;//用户广告每日统计数据图表数据

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public Long getPromoteId() {
        return promoteId;
    }

    public void setPromoteId(Long promoteId) {
        this.promoteId = promoteId;
    }

    

    public String getPromoteName() {
        return promoteName;
    }

    public void setPromoteName(String promoteName) {
        this.promoteName = promoteName;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public List<Promote> getAdData() {
        return adData;
    }

    public void setAdData(List<Promote> adData) {
        this.adData = adData;
    }

    public AdAccountInfo getAdAccountData() {
        return adAccountData;
    }

    public void setAdAccountData(AdAccountInfo adAccountData) {
        this.adAccountData = adAccountData;
    }

    public AdSummaryInfo getAdSummaryData() {
        return adSummaryData;
    }

    public void setAdSummaryData(AdSummaryInfo adSummaryData) {
        this.adSummaryData = adSummaryData;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public PaginationBo<AdDaySummaryInfo> getAdDaySummaryDataPage() {
        return adDaySummaryDataPage;
    }

    public void setAdDaySummaryDataPage(PaginationBo<AdDaySummaryInfo> adDaySummaryDataPage) {
        this.adDaySummaryDataPage = adDaySummaryDataPage;
    }

    public List<AdDaySummaryInfo> getAdDaySummaryData() {
        return adDaySummaryData;
    }

    public void setAdDaySummaryData(List<AdDaySummaryInfo> adDaySummaryData) {
        this.adDaySummaryData = adDaySummaryData;
    }

     

}
