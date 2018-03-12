/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.integration.finance.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.crm.common.PaginationBo;
import com.wanda.ffanad.crm.dto.FinanceAccountBillCheckDto;
import com.wanda.ffanad.crm.dto.resp.DspBillCheckDetailRespDto;
import com.wanda.ffanad.crm.dto.resp.DspBillCheckSummaryRespDto;

/**
 * 类FinanceAccountBillCheckService.java的实现描述：TODO 类实现描述
 * 
 * @author yanji 2016年6月7日 下午4:25:22
 */
public interface FinanceAccountBillCheckService {
    /* 需求方对账单统计接口(按月) */
    List<FinanceAccountBillCheckDto> requirementAccountBillCheckByMonth(String day, String accountId);

    /* 需求方对账单统计接口(按天) */
    List<FinanceAccountBillCheckDto> requirementAccountBillCheckByDaily(String day, String accountId);

    /* 资源方对账单（按月） */
    List<FinanceAccountBillCheckDto> resourceAccountBillCheckByMonth(String day, String accountId, String userRole);

    /* 资源方对账单（按日,按资源位） */
    Map<String, Map<Long, List<FinanceAccountBillCheckDto>>> resourceAccountBillCheckByDaily(String day, String accountId);

    RestResult updateResourceAccountBillCheckPaysStatus(String day, String accountId);

    /**
     * 第三方dsp对账单汇总
     * 
     * @param dsp
     * @param beginDate
     * @param endDate
     * @return
     */
    DspBillCheckSummaryRespDto getDspBillCheckSummary(Short dsp, Date beginDate, Date endDate);

    /**
     * 第三方dsp对账单分页数据
     * 
     * @param dsp
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    PaginationBo getDspBillCheckPage(Short dsp, Date beginDate, Date endDate, Integer pageNum, Integer pageSize);

    /**
     * 第三方dsp对账单所有数据
     *
     * @param dsp
     * @param beginDate
     * @param endDate
     * @return
     */
    List<DspBillCheckDetailRespDto> getDspBillCheckList(Short dsp, Date beginDate, Date endDate);

}
