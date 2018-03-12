package com.wanda.ffanad.crm.integration.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.common.utils.MathUtils;
import com.wanda.ffanad.common.utils.NumericUtils;
import com.wanda.ffanad.core.services.api.RegionService;
import com.wanda.ffanad.core.vo.RegionVo;
import com.wanda.ffanad.crm.integration.analysis.resp.DspStatResponseData;
import com.wanda.ffanad.crm.integration.analysis.resp.DspStatResponse;
import com.wanda.ffanad.crm.integration.analysis.resp.DspCityStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspCityStatResponse;
import com.wanda.ffanad.crm.integration.analysis.resp.DspCityStatResponseData;
import com.wanda.ffanad.crm.integration.analysis.resp.DspDailyStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspDailyStatResponse;
import com.wanda.ffanad.crm.integration.analysis.resp.DspDailyStatResponseData;
import com.wanda.ffanad.crm.integration.analysis.resp.DspResStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspResStatResponse;
import com.wanda.ffanad.crm.integration.analysis.resp.DspResStatResponseData;
import com.wanda.ffanad.crm.integration.analysis.resp.DspStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspStatSummary;
import com.wanda.ffanad.crm.integration.analysis.resp.DspTerminalStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspTerminalStatResponse;
import com.wanda.ffanad.crm.integration.analysis.resp.DspTerminalStatResponseData;
import com.wanda.ffanad.crm.integration.common.ApiUrlBuilder;

/**
 * 类PromoteAnalysisApiServiceImpl.java的实现描述：投放分析接口实现
 * 
 * @author Yao 2016年9月22日 下午7:18:57
 */
@Service
public class PromoteAnalysisApiServiceImpl implements PromoteAnalysisApiService {

    private static final Logger logger = LoggerFactory.getLogger(PromoteAnalysisApiServiceImpl.class);

    @Autowired
    private ApiUrlBuilder       ApiUrlBuilder;

    @Autowired
    private RestTemplate        restTemplate;

    @Autowired
    private RegionService       RegionService;

    @Override
    public DspStatSummary summaryData(Short dspCode, Date beginDate, Date endDate) {
        String countByDspApiUrl = ApiUrlBuilder.buildCountByDspURL(dspCode, String.valueOf(beginDate.getTime()),
                String.valueOf(endDate.getTime()));
        ResponseEntity<DspStatResponse> statisticsResponse = restTemplate.getForEntity(countByDspApiUrl, DspStatResponse.class);
        if (statisticsResponse.getBody().getStatus() != 200) {
            logger.error("调用统计接口出错，error={}", statisticsResponse.getBody().getMessage());
            return new DspStatSummary();
        }
        List<DspStatResponseData> dspStatDatas = statisticsResponse.getBody().getData();
        //汇总统计信息
        DspStatSummary dspSummaryData = new DspStatSummary();
        Long tmpAmount = 0L;
        Long tmpLinkAmount = 0L;
        for (DspStatResponseData dspStatData : dspStatDatas) {
            tmpAmount = NumericUtils.adds(tmpAmount, dspStatData.getAmount());
            tmpLinkAmount = NumericUtils.adds(tmpLinkAmount, dspStatData.getLinkAmount());

            dspSummaryData.setClickCount(NumericUtils.adds(dspSummaryData.getClickCount(), dspStatData.getClickCount()));
            dspSummaryData.setDisplayCount(NumericUtils.adds(dspSummaryData.getDisplayCount(), dspStatData.getDisplayCount()));
            dspSummaryData.setLinkDisplayCount(
                    NumericUtils.adds(dspSummaryData.getLinkDisplayCount(), dspStatData.getLinkDisplayCount()));
        }
        //单独计算点击率 展示成本 点击成本,并转换费用单位为元
        dspSummaryData.setAmount(CurrencyUtils.convert(tmpAmount));
        dspSummaryData.setLinkAmount(CurrencyUtils.convert(tmpLinkAmount));
        dspSummaryData.setClickRate(calcClickRate(dspSummaryData.getClickCount(), dspSummaryData.getLinkDisplayCount()));
        dspSummaryData.setCpmAmount(calcCpmAmount(dspSummaryData.getAmount(), dspSummaryData.getDisplayCount()));
        dspSummaryData.setCpcAmount(calcCpcAmount(dspSummaryData.getLinkAmount(), dspSummaryData.getClickCount()));

        return dspSummaryData;
    }

    @Override
    public List<DspStat> countByDsp(Short dspCode, Date beginDate, Date endDate) {
        String countByDspApiUrl = ApiUrlBuilder.buildCountByDspURL(dspCode, String.valueOf(beginDate.getTime()),
                String.valueOf(endDate.getTime()));
        ResponseEntity<DspStatResponse> statisticsResponse = restTemplate.getForEntity(countByDspApiUrl, DspStatResponse.class);
        if (statisticsResponse.getBody().getStatus() != 200) {
            logger.error("调用统计接口出错，error={}", statisticsResponse.getBody().getMessage());
            return Collections.emptyList();
        }
        List<DspStatResponseData> dspStatDatas = statisticsResponse.getBody().getData();
        List<DspStat> result = new ArrayList<>();
        for (DspStatResponseData data : dspStatDatas) {
            DspStat dspStatData = new DspStat();
            BeanUtils.copyProperties(data, dspStatData);
            //处理需要单独处理的数据
            dspStatData.setAmount(CurrencyUtils.convert(data.getAmount()));
            dspStatData.setLinkAmount(CurrencyUtils.convert(data.getLinkAmount()));
            dspStatData.setClickRate(calcClickRate(dspStatData.getClickCount(), dspStatData.getLinkDisplayCount()));
            dspStatData.setCpmAmount(calcCpmAmount(dspStatData.getAmount(), dspStatData.getDisplayCount()));
            dspStatData.setCpcAmount(calcCpcAmount(dspStatData.getLinkAmount(), dspStatData.getClickCount()));

            result.add(dspStatData);
        }
        return result;
    }

    @Override
    public List<DspDailyStat> dailyCount(Short dspCode, Date beginDate, Date endDate) {
        String dailyCountApiUrl = ApiUrlBuilder.buildDailyCount(dspCode, String.valueOf(beginDate.getTime()),
                String.valueOf(endDate.getTime()));
        ResponseEntity<DspDailyStatResponse> dailyStatResp = restTemplate.getForEntity(dailyCountApiUrl,
                DspDailyStatResponse.class);
        if (dailyStatResp.getBody().getStatus() != 200) {
            logger.error("调用统计接口出错，error={}", dailyStatResp.getBody().getMessage());
            return Collections.emptyList();
        }
        List<DspDailyStatResponseData> dailyStatDatas = dailyStatResp.getBody().getData();
        List<DspDailyStat> result = new ArrayList<>();
        for (DspDailyStatResponseData data : dailyStatDatas) {
            DspDailyStat dailyStat = new DspDailyStat();
            BeanUtils.copyProperties(data, dailyStat);
            //处理需要单独处理的数据
            dailyStat.setAmount(CurrencyUtils.convert(data.getAmount()));
            dailyStat.setLinkAmount(CurrencyUtils.convert(data.getLinkAmount()));
            dailyStat.setClickRate(calcClickRate(dailyStat.getClickCount(), dailyStat.getLinkDisplayCount()));
            dailyStat.setCpmAmount(calcCpmAmount(dailyStat.getAmount(), dailyStat.getDisplayCount()));
            dailyStat.setCpcAmount(calcCpcAmount(dailyStat.getLinkAmount(), dailyStat.getClickCount()));

            result.add(dailyStat);
        }
        return result;
    }

    @Override
    public List<DspResStat> countByResource(Short dspCode, Date beginDate, Date endDate) {
        String countByResApiUrl = ApiUrlBuilder.buildCountByResUrl(dspCode, String.valueOf(beginDate.getTime()),
                String.valueOf(endDate.getTime()));
        ResponseEntity<DspResStatResponse> resStatResp = restTemplate.getForEntity(countByResApiUrl, DspResStatResponse.class);
        if (resStatResp.getBody().getStatus() != 200) {
            logger.error("调用统计接口出错，error={}", resStatResp.getBody().getMessage());
            return Collections.emptyList();
        }
        List<DspResStatResponseData> resStatDatas = resStatResp.getBody().getData();
        List<DspResStat> result = new ArrayList<>();
        for (DspResStatResponseData data : resStatDatas) {
            DspResStat resStat = new DspResStat();
            BeanUtils.copyProperties(data, resStat);
            //处理需要单独处理的数据
            resStat.setAmount(CurrencyUtils.convert(data.getAmount()));
            resStat.setLinkAmount(CurrencyUtils.convert(data.getLinkAmount()));
            resStat.setClickRate(calcClickRate(resStat.getClickCount(), resStat.getLinkDisplayCount()));
            resStat.setCpmAmount(calcCpmAmount(resStat.getAmount(), resStat.getDisplayCount()));
            resStat.setCpcAmount(calcCpcAmount(resStat.getLinkAmount(), resStat.getClickCount()));

            result.add(resStat);
        }
        return result;
    }

    @Override
    public List<DspTerminalStat> countByTerminal(Short dspCode, Date beginDate, Date endDate) {
        String countByTerminalApiUrl = ApiUrlBuilder.buildCountByTerminalUrl(dspCode, String.valueOf(beginDate.getTime()),
                String.valueOf(endDate.getTime()));
        ResponseEntity<DspTerminalStatResponse> dailyStatResp = restTemplate.getForEntity(countByTerminalApiUrl,
                DspTerminalStatResponse.class);
        if (dailyStatResp.getBody().getStatus() != 200) {
            logger.error("调用统计接口出错，error={}", dailyStatResp.getBody().getMessage());
            return Collections.emptyList();
        }
        List<DspTerminalStatResponseData> terminalStatDatas = dailyStatResp.getBody().getData();
        List<DspTerminalStat> result = new ArrayList<>();
        for (DspTerminalStatResponseData data : terminalStatDatas) {
            DspTerminalStat terminalStat = new DspTerminalStat();
            BeanUtils.copyProperties(data, terminalStat);
            //第三方只接入定制轮播资源位，判断终端类型暂时写死
            if (terminalStat.getOs() == 2) {
                terminalStat.setTerminalName("飞凡iOS");
            } else if (terminalStat.getOs() == 1) {
                terminalStat.setTerminalName("飞凡Android");
            }
            //处理需要单独处理的数据
            terminalStat.setAmount(CurrencyUtils.convert(data.getAmount()));
            terminalStat.setLinkAmount(CurrencyUtils.convert(data.getLinkAmount()));
            terminalStat.setClickRate(calcClickRate(terminalStat.getClickCount(), terminalStat.getLinkDisplayCount()));
            terminalStat.setCpmAmount(calcCpmAmount(terminalStat.getAmount(), terminalStat.getDisplayCount()));
            terminalStat.setCpcAmount(calcCpcAmount(terminalStat.getLinkAmount(), terminalStat.getClickCount()));

            result.add(terminalStat);
        }
        return result;
    }

    @Override
    public List<DspCityStat> countByCity(Short dspCode, Date beginDate, Date endDate) {
        String countByCityApiUrl = ApiUrlBuilder.buildCountByCityUrl(dspCode, String.valueOf(beginDate.getTime()),
                String.valueOf(endDate.getTime()));
        ResponseEntity<DspCityStatResponse> cityStatResp = restTemplate.getForEntity(countByCityApiUrl,
                DspCityStatResponse.class);
        if (cityStatResp.getBody().getStatus() != 200) {
            logger.error("调用统计接口出错，error={}", cityStatResp.getBody().getMessage());
            return Collections.emptyList();
        }
        List<DspCityStatResponseData> cityStatDatas = cityStatResp.getBody().getData();
        List<DspCityStat> result = new ArrayList<>();
        for (DspCityStatResponseData data : cityStatDatas) {
            DspCityStat cityStat = new DspCityStat();
            BeanUtils.copyProperties(data, cityStat);
            //处理城市信息
            if (StringUtils.isNumeric(data.getCity())) {
                //目前不支持直辖市ID查询，暂时写死
                String city = handleSpecialCity(data.getCity());
                if (city == null) {
                    RegionVo regionVo = RegionService.queryCityById(Integer.parseInt(data.getCity()));
                    cityStat.setCity(regionVo == null ? "未知" + data.getCity() : regionVo.getRegionName());
                } else {
                    cityStat.setCity(city);
                }
            }
            //处理需要单独处理的数据
            cityStat.setAmount(CurrencyUtils.convert(data.getAmount()));
            cityStat.setLinkAmount(CurrencyUtils.convert(data.getLinkAmount()));
            cityStat.setClickRate(calcClickRate(cityStat.getClickCount(), cityStat.getLinkDisplayCount()));
            cityStat.setCpmAmount(calcCpmAmount(cityStat.getAmount(), cityStat.getDisplayCount()));
            cityStat.setCpcAmount(calcCpcAmount(cityStat.getLinkAmount(), cityStat.getClickCount()));

            result.add(cityStat);
        }
        return result;
    }

    /**
     * 计算点击率
     * 
     * @return
     */
    private double calcClickRate(Long clickCount, Long displayCount) {
        if (displayCount == 0) {
            return 0.00;
        } else {
            return MathUtils.formatTo2DecimalPlace(MathUtils.divide(clickCount * 100, displayCount)).doubleValue();
        }
    }

    /**
     * 计算千次展示成本
     * 
     * @param amount
     * @param displayCount
     * @return
     */
    private double calcCpmAmount(double amount, Long displayCount) {
        if (displayCount == 0) {
            return 0.00;
        } else {
            return MathUtils.formatTo2DecimalPlace(MathUtils.divide(amount * 1000, displayCount)).doubleValue();
        }
    }

    /**
     * 计算点击成本
     * 
     * @param amount
     * @param clickCount
     * @return
     */
    private double calcCpcAmount(double amount, Long clickCount) {
        if (clickCount == 0) {
            return 0.00;
        } else {
            return MathUtils.formatTo2DecimalPlace(MathUtils.divide(amount, clickCount)).doubleValue();
        }
    }

    /**
     * 处理直辖市的转换
     * 
     * @param cityId
     * @return
     */
    private String handleSpecialCity(String cityId) {
        if (StringUtils.isBlank(cityId))
            return null;

        if (cityId.equals("110100")) {
            return "北京市";
        } else if (cityId.equals("120100")) {
            return "天津市";
        } else if (cityId.equals("310100")) {
            return "上海市";
        } else if (cityId.equals("500100")) {
            return "重庆市";
        } else {
            return null;
        }
    }
}
