package com.wanda.ffanad.crm.integration.promote.respdto;

import java.util.List;

/**
 * 广告统计response封装
 * 
 * @author xuyao16
 * @date 2016年6月7日下午3:21:38
 */
public class AppStatisticsResponse {

    private int                    status;

    private String                 message;

    private List<PromoteStatistic> data;

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

    public List<PromoteStatistic> getData() {
        return data;
    }

    public void setData(List<PromoteStatistic> data) {
        this.data = data;
    }
}
