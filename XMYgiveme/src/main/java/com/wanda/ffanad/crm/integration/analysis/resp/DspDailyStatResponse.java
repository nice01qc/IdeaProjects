package com.wanda.ffanad.crm.integration.analysis.resp;

import java.util.List;

/**
 * 类DSPStatisticsResponse.java的实现描述：dsp投放按天统计返回
 * 
 * @author Yao 2016年9月21日 下午3:48:59
 */
public class DspDailyStatResponse {

    private int                            status;

    private String                         message;

    private List<DspDailyStatResponseData> data;

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

    public List<DspDailyStatResponseData> getData() {
        return data;
    }

    public void setData(List<DspDailyStatResponseData> data) {
        this.data = data;
    }
}
