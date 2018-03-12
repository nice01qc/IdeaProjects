package com.wanda.ffanad.crm.integration.promote.respdto;

/**
 * 广告统计response封装
 * 
 * @author xuyao16
 * @date 2016年6月7日下午3:21:38
 */
public class StatisticsResponse {

    private int              status;

    private String           message;

    private  PromoteStatistic data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PromoteStatistic getData() {
        return data;
    }

    public void setData(PromoteStatistic data) {
        this.data = data;
    }

    
    

}
