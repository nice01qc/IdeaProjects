package com.wanda.ffanad.crm.integration.promote.respdto;

import com.wanda.ffanad.core.common.PaginationBo;

/**
 * 广告统计response封装
 * 
 * @author xuyao16
 * @date 2016年6月7日下午3:21:38
 */
public class StatisticsResponsePage {

    private int                            status;

    private String                         message;

    private PaginationBo<PromoteStatistic> data;

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

    public PaginationBo<PromoteStatistic> getData() {
        return data;
    }

    public void setData(PaginationBo<PromoteStatistic> data) {
        this.data = data;
    }

}
