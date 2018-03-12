package com.wanda.ffanad.crm.dto.req;

import javax.validation.constraints.NotNull;

/**
 * 类PromoteAnalysisRequest.java的实现描述：统计分析查询条件封装
 * 
 * @author Yao 2016年9月23日 下午1:26:41
 */
public class PromoteAnalysisRequest {
    /**
     * dsp代码
     */
    @NotNull(message = "投放DSP不能为空")
    private Short  dspCode;

    /**
     * 开始时间
     */
    @NotNull(message = "开始日期不能为空")
    private String beginDate;

    /**
     * 结束日期
     */
    @NotNull(message = "结束日期不能为空")
    private String endDate;

    public Short getDspCode() {
        return dspCode;
    }

    public void setDspCode(Short dspCode) {
        this.dspCode = dspCode;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
