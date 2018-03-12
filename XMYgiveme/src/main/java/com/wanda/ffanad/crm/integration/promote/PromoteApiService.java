package com.wanda.ffanad.crm.integration.promote;

import java.util.List;

import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.crm.datacenter.bo.AdDaySummaryInfo;
import com.wanda.ffanad.crm.datacenter.bo.AdSummaryInfo;

/**
 * @author huangzhiqiang19
 * @date 2016年5月25日 下午5:57:59
 */
public interface PromoteApiService {

    /**
     * 查询用户投放广告统计信息
     * 
     * @param userId
     * @return 用户投放广告统计信息
     */
    public AdSummaryInfo getAdSummaryInfoByUserId(Long userId);

    /**
     * 根据条件查询用户每日投放广告统计信息
     * 
     * @param userId 用户ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param promoteId 投放广告id
     * @return根据条件查询用户每日投放广告统计信息
     */
    public List<AdDaySummaryInfo> getAdDaySumaryInfo(Long userId, String startDate, String endDate, Long promoteId);

    /**
     * 根据用户分页查询投放统计信息
     * 
     * @param userId
     * @param start
     * @param ende
     * @param startDate
     * @param endDate
     * @param promoteId
     * @return
     */
    public PaginationBo<AdDaySummaryInfo> getAdDaySumaryInfoPage(Long userId, Integer start, Integer ende, String startDate,
                                                                 String endDate, Long promoteId);

}
