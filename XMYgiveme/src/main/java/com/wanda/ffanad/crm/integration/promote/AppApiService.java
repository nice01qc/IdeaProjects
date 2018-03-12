package com.wanda.ffanad.crm.integration.promote;

import com.wanda.ffanad.crm.integration.promote.respdto.PromoteStatistic;
import com.wanda.ffanad.crm.integration.promote.respdto.StatisticsResponse;

import java.util.List;

/**
 * 调用app的统计接口 Created by liujie136 on 2016-8-12.
 */
public interface AppApiService {

    /**
     * 查询app的投放情况
     * 
     * @param appIds
     * @return
     */
    List<PromoteStatistic> queryAppStatistics(List<Integer> appIds);
}
