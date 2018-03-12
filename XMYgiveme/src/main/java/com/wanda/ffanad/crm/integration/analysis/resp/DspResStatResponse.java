package com.wanda.ffanad.crm.integration.analysis.resp;

import java.util.List;

/**
 * 类DSPStatisticsResponse.java的实现描述：dsp投放按资源位统计返回
 * 
 * @author Yao 2016年9月21日 下午3:48:59
 */
public class DspResStatResponse {

    private int                          status;

    private String                       message;

    private List<DspResStatResponseData> data;

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

    public List<DspResStatResponseData> getData() {
        return data;
    }

    public void setData(List<DspResStatResponseData> data) {
        this.data = data;
    }
}
