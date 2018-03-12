package com.wanda.ffanad.crm.integration.analysis;

import java.util.Date;
import java.util.List;

import com.wanda.ffanad.crm.integration.analysis.resp.DspCityStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspDailyStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspResStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspStatSummary;
import com.wanda.ffanad.crm.integration.analysis.resp.DspTerminalStat;

/**
 * 类PromoteAnalysisApiService.java的实现描述：投放分析api封装接口
 * 
 * @author Yao 2016年9月22日 下午7:13:51
 */
public interface PromoteAnalysisApiService {

    /**
     * 投放分析汇总信息
     * 
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @return
     */
    public DspStatSummary summaryData(Short dspCode, Date beginDate, Date endDate);

    /**
     * 按dsp统计投放信息
     * 
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @return
     */
    public List<DspStat> countByDsp(Short dspCode, Date beginDate, Date endDate);

    /**
     * 按天统计
     * 
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<DspDailyStat> dailyCount(Short dspCode, Date beginDate, Date endDate);

    /**
     * 按资源位统计
     * 
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<DspResStat> countByResource(Short dspCode, Date beginDate, Date endDate);

    /**
     * 按终端统计
     * 
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<DspTerminalStat> countByTerminal(Short dspCode, Date beginDate, Date endDate);

    /**
     * 按城市统计
     * 
     * @param dspCode
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<DspCityStat> countByCity(Short dspCode, Date beginDate, Date endDate);

}
