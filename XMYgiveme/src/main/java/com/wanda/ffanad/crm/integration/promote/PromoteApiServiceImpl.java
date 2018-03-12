package com.wanda.ffanad.crm.integration.promote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.common.utils.DateUtil;
import com.wanda.ffanad.common.utils.MathUtils;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.crm.datacenter.bo.AdDaySummaryInfo;
import com.wanda.ffanad.crm.datacenter.bo.AdSummaryInfo;
import com.wanda.ffanad.crm.integration.common.ApiUrlBuilder;
import com.wanda.ffanad.crm.integration.promote.respdto.PromoteStatistic;
import com.wanda.ffanad.crm.integration.promote.respdto.StatisticsResponse;
import com.wanda.ffanad.crm.integration.promote.respdto.StatisticsResponseList;
import com.wanda.ffanad.crm.integration.promote.respdto.StatisticsResponsePage;

/**
 * @author huangzhiqiang19
 * @date 2016年5月25日 下午6:02:30
 */
@Service
public class PromoteApiServiceImpl implements PromoteApiService {

    @Autowired
    private ApiUrlBuilder ApiUrlBuilder;

    @Autowired
    private RestTemplate  restTemplate;

    @Override
    public AdSummaryInfo getAdSummaryInfoByUserId(Long userId) {
        String adSummaryInfoUrl = ApiUrlBuilder.buildAdPromoteUrl("summary", userId, null, null, null, null, null);
        ResponseEntity<StatisticsResponse> statisticsResponse = restTemplate.getForEntity(adSummaryInfoUrl,
                StatisticsResponse.class);
        PromoteStatistic promoteStatistics = (PromoteStatistic) statisticsResponse.getBody().getData();
        AdSummaryInfo adSummaryInfo = new AdSummaryInfo();
        if (promoteStatistics != null) {
            adSummaryInfo.setShowNum(promoteStatistics.getDisplayCount() == null ? 0 : promoteStatistics.getDisplayCount());
            adSummaryInfo.setClickNum(promoteStatistics.getClickCount() == null ? 0 : promoteStatistics.getClickCount());
            if (promoteStatistics.getDisplayCount() == null || promoteStatistics.getDisplayCount() == 0) {
                adSummaryInfo.setClickRate(0d);
            } else {
                adSummaryInfo
                        .setClickRate(
                                MathUtils
                                        .formatToPrice(MathUtils.divide(
                                                (promoteStatistics.getClickCount() == null ? 0
                                                        : promoteStatistics.getClickCount()) * 100,
                                                promoteStatistics.getDisplayCount()))
                                        .doubleValue());
            }
            adSummaryInfo.setCpc(CurrencyUtils.convert(promoteStatistics.getCpc() == null ? 0 : promoteStatistics.getCpc()));
            adSummaryInfo.setCpm(CurrencyUtils.convert(promoteStatistics.getCpm() == null ? 0 : promoteStatistics.getCpm()));
        }
        return adSummaryInfo;
    }

    @Override
    public List<AdDaySummaryInfo> getAdDaySumaryInfo(Long userId, String startDate, String endDate, Long promoteId) {
        String adSummaryInfoUrl = ApiUrlBuilder.buildAdPromoteUrl("daily", userId, startDate, endDate, promoteId, null, null);
        ResponseEntity<StatisticsResponseList> statisticsResponse = restTemplate.getForEntity(adSummaryInfoUrl,
                StatisticsResponseList.class);
        List<PromoteStatistic> promoteStatistic = statisticsResponse.getBody().getData();
        List<AdDaySummaryInfo> adDaySummaryInfo = new ArrayList<AdDaySummaryInfo>();

        if (promoteStatistic != null) {
            for (PromoteStatistic p : promoteStatistic) {
                AdDaySummaryInfo adSummaryInfo = new AdDaySummaryInfo();
                adSummaryInfo.setShowNum(p.getDisplayCount());
                adSummaryInfo.setClickNum(p.getClickCount());
                if (p.getDisplayCount() == 0) {
                    adSummaryInfo.setClickRate(0d);
                } else {
                    adSummaryInfo.setClickRate(MathUtils.formatToPrice(
                            MathUtils.divide((p.getClickCount() == null ? 0 : p.getClickCount()) * 100, p.getDisplayCount()))
                            .doubleValue());
                }

                adSummaryInfo.setCpc(CurrencyUtils.convert(p.getCpc() == null ? 0 : p.getCpc()));
                adSummaryInfo.setCpm(CurrencyUtils.convert(p.getCpm() == null ? 0 : p.getCpm()));
                adSummaryInfo.setConsume(CurrencyUtils.convert(p.getAmount() == null ? 0 : p.getAmount()));
                adSummaryInfo.setTime(DateUtil
                        .dateToString(new Date(StringUtils.isBlank(p.getDate()) ? 0 : Long.valueOf(p.getDate())), "yyyy-MM-dd"));
                adDaySummaryInfo.add(adSummaryInfo);
            }

        }
        return adDaySummaryInfo;
    }

    @Override
    public PaginationBo<AdDaySummaryInfo> getAdDaySumaryInfoPage(Long userId, Integer start, Integer end, String startDate,
                                                                 String endDate, Long promoteId) {
        String adSummaryInfoUrl = ApiUrlBuilder.buildAdPromoteUrl("dailypage", userId, startDate, endDate, promoteId, start, end);
        ResponseEntity<StatisticsResponsePage> statisticsResponse = restTemplate.getForEntity(adSummaryInfoUrl,
                StatisticsResponsePage.class);
        PaginationBo<PromoteStatistic> promoteStatisticPage = (PaginationBo<PromoteStatistic>) statisticsResponse.getBody()
                .getData();
        PaginationBo<AdDaySummaryInfo> result = new PaginationBo<AdDaySummaryInfo>();
        List<AdDaySummaryInfo> adDaySummaryInfos = new ArrayList<AdDaySummaryInfo>();
        result.setTotal(promoteStatisticPage.getTotal());
        if (promoteStatisticPage != null) {
            for (PromoteStatistic p : promoteStatisticPage.getRows()) {
                AdDaySummaryInfo adDaySummaryInfo = new AdDaySummaryInfo();
                adDaySummaryInfo.setShowNum(p.getDisplayCount());
                adDaySummaryInfo.setClickNum(p.getClickCount());
                if (p.getDisplayCount() == 0) {
                    adDaySummaryInfo.setClickRate(0d);
                } else {
                    adDaySummaryInfo.setClickRate(MathUtils.formatToPrice(
                            MathUtils.divide((p.getClickCount() == null ? 0 : p.getClickCount()) * 100, p.getDisplayCount()))
                            .doubleValue());
                }
                adDaySummaryInfo.setTime(DateUtil
                        .dateToString(new Date(StringUtils.isBlank(p.getDate()) ? 0 : Long.valueOf(p.getDate())), "yyyy-MM-dd"));
                
                adDaySummaryInfo.setCpc(CurrencyUtils.convert(p.getCpc() == null ? 0 : p.getCpc()));
                adDaySummaryInfo.setCpm(CurrencyUtils.convert(p.getCpm() == null ? 0 : p.getCpm()));
                adDaySummaryInfo.setConsume(CurrencyUtils.convert(p.getAmount() == null ? 0 : p.getAmount()));
                adDaySummaryInfos.add(adDaySummaryInfo);
            }

        }
        result.setRows(adDaySummaryInfos);
        return result;
    }

}
