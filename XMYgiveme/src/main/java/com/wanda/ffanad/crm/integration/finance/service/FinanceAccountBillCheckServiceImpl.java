/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.integration.finance.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wanda.ffanad.core.services.api.RegionService;
import com.wanda.ffanad.core.vo.RegionVo;
import com.wanda.ffanad.crm.common.BillCheckListReceiveResponse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntity;
import com.wanda.ffanad.base.dal.entities.ResourceInfoEntityExample;
import com.wanda.ffanad.base.dal.mappers.ResourceInfoEntityMapper;
import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.common.utils.MathUtils;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.common.type.UserType;
import com.wanda.ffanad.core.domains.App;
import com.wanda.ffanad.core.domains.vo.res.ResourceInfoResVo;
import com.wanda.ffanad.core.services.AccountService;
import com.wanda.ffanad.core.services.AppService;
import com.wanda.ffanad.core.services.ResourceService;
import com.wanda.ffanad.crm.common.BillCheckSummaryReceiveResponse;
import com.wanda.ffanad.crm.common.BillCheckPageReceiveResponse;
import com.wanda.ffanad.crm.common.FinanceReceiveResponse;
import com.wanda.ffanad.crm.common.FinanceResourceReceiveResponse;
import com.wanda.ffanad.crm.common.PaginationBo;
import com.wanda.ffanad.crm.dto.FinanceAccountBillCheckDto;
import com.wanda.ffanad.crm.dto.resp.DspBillCheckDetailRespDto;
import com.wanda.ffanad.crm.dto.resp.DspBillCheckSummaryRespDto;
import com.wanda.ffanad.crm.integration.common.ApiUrlBuilder;
import com.wanda.ffanad.crm.integration.finance.resp.FinanceAccountBillCheckRespDto;
import com.wanda.ffanad.crm.integration.finance.resp.FinanceResourceReceiveRespDto;
import com.wanda.ffanad.crm.utils.DateUtil;

/**
 * 类FinanceAccountBillCheckServiceImpl.java的实现描述：TODO 类实现描述
 * 
 * @author yanji 2016年6月7日 下午4:27:01
 */
@Service
public class FinanceAccountBillCheckServiceImpl implements FinanceAccountBillCheckService {

    private static final Logger      log = LoggerFactory.getLogger(FinanceAccountBillCheckServiceImpl.class);

    @Autowired
    private ApiUrlBuilder            ApiUrlBuilder;

    @Autowired
    private RestTemplate             restTemplate;
    @Autowired
    private ResourceService          resourceService;

    @Autowired
    private AppService               appService;

    @Autowired
    private AccountService           accountService;

    @Autowired
    private ResourceInfoEntityMapper resourceInfoEntityMapper;

    @Autowired
    private RegionService            regionService;

    @Override
    public List<FinanceAccountBillCheckDto> requirementAccountBillCheckByMonth(String day, String accountId) {
        try {
            String year = DateUtil.getYear(day);
            String month = DateUtil.getMonth(day);
            String url = ApiUrlBuilder.buildFinanceAdUrl(accountId, year, month);
            ResponseEntity<FinanceReceiveResponse> financeReceive = restTemplate.getForEntity(url, FinanceReceiveResponse.class);
            List<FinanceAccountBillCheckRespDto> datas = financeReceive.getBody().getData();

            if (financeReceive.getBody().getStatus() != 200) {
                log.error("调数据中心接口失败 error={}", financeReceive.getBody().getMessage());
                return Collections.emptyList();
            }

            if (CollectionUtils.isEmpty(datas)) {
                return Collections.emptyList();
            }
            List<FinanceAccountBillCheckDto> results = new ArrayList<>(datas.size());
            FinanceAccountBillCheckDto result = null;
            for (FinanceAccountBillCheckRespDto financeCheckDto : datas) {
                AccountEntity account = new AccountEntity();
                if (null == financeCheckDto.getAccountId()) {
                    log.error("数据中心返回数据accountid为空!");
                    return Collections.emptyList();
                }
                account.setAccountId(financeCheckDto.getAccountId().intValue());
                account.setUserType(UserType.DSP.getValue());// 有三种类型：1.CRM,2.SSP,3.DSP
                AccountEntity userAccount = accountService.findBySelective(account);
                if (userAccount == null) {
                    log.error("用户账户异常");
                    return Collections.emptyList();
                }
                result = new FinanceAccountBillCheckDto();
                BeanUtils.copyProperties(financeCheckDto, result);
                result.setAccountMail(userAccount.getAccountEmail());
                result.setCompany(userAccount.getCompany());
                // 设置ctr,3位小数
                result.setCtr(financeCheckDto.getDisplayCount() == 0 || financeCheckDto.getClickCount() == 0 ? "0"
                        : String.valueOf(MathUtils.formatTo2DecimalPlace(
                                MathUtils.divide(financeCheckDto.getClickCount(), financeCheckDto.getDisplayCount()))));

                // Long,通过CPM得到的收入(单位：分)
                result.setCpmAmount(
                        CurrencyUtils.convert(financeCheckDto.getCpmAmount() == null ? 0 : financeCheckDto.getCpmAmount()));
                // Long,通过CPC得到的收入(单位：分)
                result.setCpcAmount(
                        CurrencyUtils.convert(financeCheckDto.getCpcAmount() == null ? 0 : financeCheckDto.getCpcAmount()));
                // Long,通过独占得到的收入(单位：分)
                result.setOccupyAmount(
                        CurrencyUtils.convert(financeCheckDto.getOccupyAmount() == null ? 0 : financeCheckDto.getOccupyAmount()));
                // Long,总价(单位：分)
                result.setAmount(CurrencyUtils.convert(financeCheckDto.getAmount() == null ? 0 : financeCheckDto.getAmount()));
                results.add(result);
            }
            return results;
        } catch (Exception e) {
            log.error("获取需求方月对账单异常: error={}  ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<FinanceAccountBillCheckDto> requirementAccountBillCheckByDaily(String day, String accountId) {
        try {
            String year = DateUtil.getYear(day);
            String month = DateUtil.getMonth(day);
            String url = ApiUrlBuilder.buildFinanceDetailAdUrl(accountId, year, month);

            ResponseEntity<FinanceReceiveResponse> financeReceive = restTemplate.getForEntity(url, FinanceReceiveResponse.class);
            if (financeReceive.getBody().getStatus() != 200) {
                log.error("调数据中心接口失败 error={}", financeReceive.getBody().getMessage());
                return Collections.emptyList();
            }
            List<FinanceAccountBillCheckRespDto> datas = financeReceive.getBody().getData();

            if (CollectionUtils.isEmpty(datas)) {
                return Collections.emptyList();
            }
            List<FinanceAccountBillCheckDto> results = new ArrayList<>(datas.size());
            FinanceAccountBillCheckDto result = null;
            for (FinanceAccountBillCheckRespDto financeCheckDto : datas) {
                result = new FinanceAccountBillCheckDto();
                BeanUtils.copyProperties(financeCheckDto, result);
                // date转换
                result.setDate(DateUtil.getFormatDate(financeCheckDto.getDate()));
                // 设置ctr,3位小数s
                result.setCtr(financeCheckDto.getDisplayCount() == 0 || financeCheckDto.getClickCount() == 0 ? "0"
                        : String.valueOf(MathUtils.formatTo2DecimalPlace(
                                MathUtils.divide(financeCheckDto.getClickCount(), financeCheckDto.getDisplayCount()))));
                // Long,通过CPM得到的收入(单位：分)
                result.setCpmAmount(
                        CurrencyUtils.convert(financeCheckDto.getCpmAmount() == null ? 0 : financeCheckDto.getCpmAmount()));
                // Long,通过CPC得到的收入(单位：分)
                result.setCpcAmount(
                        CurrencyUtils.convert(financeCheckDto.getCpcAmount() == null ? 0 : financeCheckDto.getCpcAmount()));
                // Long,通过独占得到的收入(单位：分)
                result.setOccupyAmount(
                        CurrencyUtils.convert(financeCheckDto.getOccupyAmount() == null ? 0 : financeCheckDto.getOccupyAmount()));
                // Long,总价(单位：分)
                result.setAmount(CurrencyUtils.convert(financeCheckDto.getAmount() == null ? 0 : financeCheckDto.getAmount()));
                results.add(result);
            }
            return results;
        } catch (Exception e) {
            log.error("获取需求方月对账单异常: error={}  ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<FinanceAccountBillCheckDto> resourceAccountBillCheckByMonth(String day, String accountId, String userRole) {
        try {
            String year = DateUtil.getYear(day);
            String month = DateUtil.getMonth(day);
            String url = ApiUrlBuilder.buildFinanceResourceAdUrl(accountId, year, month, userRole);

            ResponseEntity<FinanceReceiveResponse> financeReceive = restTemplate.getForEntity(url, FinanceReceiveResponse.class);
            if (financeReceive.getBody().getStatus() != 200) {
                log.error("调数据中心接口失败 error={}", financeReceive.getBody().getMessage());
                return Collections.emptyList();
            }
            List<FinanceAccountBillCheckRespDto> datas = financeReceive.getBody().getData();

            if (CollectionUtils.isEmpty(datas)) {
                return Collections.emptyList();
            }
            List<FinanceAccountBillCheckDto> results = new ArrayList<>(datas.size());
            FinanceAccountBillCheckDto result = null;
            for (FinanceAccountBillCheckRespDto financeCheckDto : datas) {
                AccountEntity account = new AccountEntity();
                if (null == financeCheckDto.getAccountId()) {
                    log.error("数据中心返回数据accountid为空!");
                    return Collections.emptyList();
                }
                account.setAccountId(financeCheckDto.getAccountId().intValue());
                if (!SystemConstant.ROLE_SYSTEM_SSP_SENIOR.equals(UserType.SSP.getValue() + "#" + userRole)) {
                    account.setUserType(UserType.SSP.getValue());// 有三种类型：1.CRM,2.SSP,3.DSP
                }
                AccountEntity userAccount = accountService.findBySelective(account);
                if (userAccount == null) {
                    log.error("用户账户异常");
                    return Collections.emptyList();
                }
                result = new FinanceAccountBillCheckDto();
                BeanUtils.copyProperties(financeCheckDto, result);
                result.setAccountMail(userAccount.getAccountEmail());
                result.setCompany(userAccount.getCompany());
                // 设置ctr,2位小数
                result.setCtr(financeCheckDto.getDisplayCount() == 0 || financeCheckDto.getClickCount() == 0 ? "0"
                        : String.valueOf(MathUtils.formatTo2DecimalPlace(
                                MathUtils.divide(financeCheckDto.getClickCount(), financeCheckDto.getDisplayCount()))));
                // Long,通过CPM得到的收入(单位：分)
                result.setCpmAmount(
                        CurrencyUtils.convert(financeCheckDto.getCpmAmount() == null ? 0 : financeCheckDto.getCpmAmount()));
                // Long,通过CPC得到的收入(单位：分)
                result.setCpcAmount(
                        CurrencyUtils.convert(financeCheckDto.getCpcAmount() == null ? 0 : financeCheckDto.getCpcAmount()));
                // Long,通过独占得到的收入(单位：分)
                result.setOccupyAmount(
                        CurrencyUtils.convert(financeCheckDto.getOccupyAmount() == null ? 0 : financeCheckDto.getOccupyAmount()));
                // Long,总价(单位：分)
                result.setAmount(CurrencyUtils.convert(financeCheckDto.getAmount() == null ? 0 : financeCheckDto.getAmount()));
                results.add(result);
            }
            return results;
        } catch (Exception e) {
            log.error("获取资源方月对账单异常: error={}  ", e);
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Map<Long, List<FinanceAccountBillCheckDto>>> resourceAccountBillCheckByDaily(String day,
                                                                                                    String accountId) {
        // 根据账户id查询app key
        List<Byte> statusList = new ArrayList<Byte>();
        //statusList.add(AppStatusEnum.APP_NORMAL.getValue().byteValue());
        List<App> apps = appService.queryAppByAccountId(Integer.valueOf(accountId), statusList);
        if (CollectionUtils.isEmpty(apps)) {
            log.info("该账户没有查到app信息,accountid={}", accountId);
            return MapUtils.EMPTY_MAP;
        }
        Map<String, Map<Long, List<FinanceAccountBillCheckDto>>> map = new HashMap<String, Map<Long, List<FinanceAccountBillCheckDto>>>();

        // 调数据中心接口
        for (App app : apps) {
            if (StringUtils.isBlank(app.getAppKey())) {
                log.error(" app key 为空! accountId={}", accountId);
                return MapUtils.EMPTY_MAP;
            }

            String year = DateUtil.getYear(day);
            String month = DateUtil.getMonth(day);
            String url = ApiUrlBuilder.buildFinanceResourceDetailAdUrl(app.getAppKey(), year, month);// app
                                                                                                     // key

            ResponseEntity<FinanceResourceReceiveResponse> financeReceive = restTemplate.getForEntity(url,
                    FinanceResourceReceiveResponse.class);
            if (financeReceive.getBody().getStatus() != 200) {
                log.error("调数据中心接口失败 error={}", financeReceive.getBody().getMessage());
                return MapUtils.EMPTY_MAP;
            }
            List<FinanceResourceReceiveRespDto> datas = financeReceive.getBody().getData();
            Map<Long, List<FinanceAccountBillCheckDto>> mapRes = new HashMap<Long, List<FinanceAccountBillCheckDto>>();
            for (FinanceResourceReceiveRespDto financeResourceReceiveDto : datas) {
                List<FinanceAccountBillCheckRespDto> resources = financeResourceReceiveDto.getStatementDayData();
                // 根据资源id获取资源位名称
                Long resId = financeResourceReceiveDto.getResourceId();

                ResourceInfoResVo resourceInfoResVo = null;
                try {
                    resourceInfoResVo = resourceService.getResourceById(resId.intValue());
                }catch (Exception e){
                    continue;
                }
                List<FinanceAccountBillCheckDto> results = new ArrayList<>(datas.size());
                FinanceAccountBillCheckDto result = null;
                for (FinanceAccountBillCheckRespDto financeCheckDto : resources) {
                    result = new FinanceAccountBillCheckDto();
                    BeanUtils.copyProperties(financeCheckDto, result);
                    // app名称
                    result.setAppName(app.getName());
                    // 资源位名称
                    result.setResourcePostion(resourceInfoResVo.getResName());
                    // date转换
                    result.setDate(DateUtil.getFormatDate(financeCheckDto.getDate()));
                    // 设置ctr,3位小数s
                    result.setCtr(financeCheckDto.getDisplayCount() == 0 || financeCheckDto.getClickCount() == 0 ? "0"
                            : String.valueOf(MathUtils.formatTo2DecimalPlace(
                                    MathUtils.divide(financeCheckDto.getClickCount(), financeCheckDto.getDisplayCount()))));

                    // Long,通过CPM得到的收入(单位：分)
                    result.setCpmAmount(
                            CurrencyUtils.convert(financeCheckDto.getCpmAmount() == null ? 0 : financeCheckDto.getCpmAmount()));
                    // Long,通过CPC得到的收入(单位：分)
                    result.setCpcAmount(
                            CurrencyUtils.convert(financeCheckDto.getCpcAmount() == null ? 0 : financeCheckDto.getCpcAmount()));
                    // Long,通过独占得到的收入(单位：分)
                    result.setOccupyAmount(CurrencyUtils
                            .convert(financeCheckDto.getOccupyAmount() == null ? 0 : financeCheckDto.getOccupyAmount()));
                    // Long,总价(单位：分)
                    result.setAmount(
                            CurrencyUtils.convert(financeCheckDto.getAmount() == null ? 0 : financeCheckDto.getAmount()));
                    results.add(result);
                }

                if (StringUtils.isBlank(resourceInfoResVo.getResName())) {
                    log.error(" resource name 为空! resId={}", resId);
                    return MapUtils.EMPTY_MAP;
                }
                mapRes.put(resId, results);
            }

            map.put(app.getAppKey(), mapRes);
        }
        return map;
    }

    @Override
    public RestResult updateResourceAccountBillCheckPaysStatus(String day, String accountId) {
        try {
            String year = DateUtil.getYear(day);
            String month = DateUtil.getMonth(day);
            String url = ApiUrlBuilder.buildFinanceResourcePayStatusAdUrl(accountId, year, month);
            ResponseEntity<FinanceReceiveResponse> financeReceive = restTemplate.getForEntity(url, FinanceReceiveResponse.class);
            return new RestResult(financeReceive.getBody().getStatus(), financeReceive.getBody().getMessage());
        } catch (Exception e) {
            log.error("获取资源方月对账单异常: error={}  ", e);
            return new RestResult(0, "获取资源方月对账单异常");
        }
    }

    @Override
    public DspBillCheckSummaryRespDto getDspBillCheckSummary(Short dsp, Date beginDate, Date endDate) {
        String url = ApiUrlBuilder.buildDspPromoteSummaryUrl(dsp, beginDate.getTime(),
                new DateTime(endDate).plusDays(1).getMillis());
        ResponseEntity<BillCheckSummaryReceiveResponse> receiveData = restTemplate.getForEntity(url,
                BillCheckSummaryReceiveResponse.class);
        DspBillCheckSummaryRespDto data = receiveData.getBody().getData();

        if (receiveData.getBody().getStatus() != 200) {
            log.error("调数据中心接口失败 error={}", receiveData.getBody().getMessage());
            log.error("接口地址:" + url);
        }

        if (data == null) {
            data = new DspBillCheckSummaryRespDto();
        }
        data.setDateRange(new DateTime(beginDate).toString("yyyy.MM.dd") + " - " + new DateTime(endDate).toString("yyyy.MM.dd"));
        return data;
    }

    @Override
    public PaginationBo getDspBillCheckPage(Short dsp, Date beginDate, Date endDate, Integer pageNum, Integer pageSize) {
        String url = ApiUrlBuilder.buildDspPromoteDetailUrl(dsp, beginDate.getTime(), endDate.getTime(), pageNum, pageSize, true);
        ResponseEntity<BillCheckPageReceiveResponse> receiveData = restTemplate.getForEntity(url,
                BillCheckPageReceiveResponse.class);
        PaginationBo<DspBillCheckDetailRespDto> data = receiveData.getBody().getData();
        if (receiveData.getBody().getStatus() != 200) {
            log.error("调数据中心接口失败 error={}", receiveData.getBody().getMessage());
            log.error("接口地址:" + url);
        }

        if (data == null) {
            return new PaginationBo();
        }

        if (!CollectionUtils.isEmpty(data.getRows())) {
            //绑定资源信息
            bindResNameAndCityName(data.getRows());
        }
        return data;
    }

    @Override
    public List<DspBillCheckDetailRespDto> getDspBillCheckList(Short dsp, Date beginDate, Date endDate) {
        String url = ApiUrlBuilder.buildDspPromoteDetailUrl(dsp, beginDate.getTime(), endDate.getTime(), null, null, false);
        ResponseEntity<BillCheckListReceiveResponse> receiveData = restTemplate.getForEntity(url,
                BillCheckListReceiveResponse.class);
        List<DspBillCheckDetailRespDto> data = receiveData.getBody().getData();
        if (receiveData.getBody().getStatus() != 200) {
            log.error("调数据中心接口失败 error={}", receiveData.getBody().getMessage());
            log.error("接口地址:" + url);
        }

        if (data == null) {
            return new ArrayList<>();
        }

        if (!CollectionUtils.isEmpty(data)) {
            //绑定资源信息
            bindResNameAndCityName(data);
        }
        return data;
    }

    private void bindResNameAndCityName(List<DspBillCheckDetailRespDto> data) {
        List<String> lisResAlias = new ArrayList<>();
        for (DspBillCheckDetailRespDto dspBillCheckDetailRespDto : data) {
            lisResAlias.add(dspBillCheckDetailRespDto.getResAlias());
        }
        ResourceInfoEntityExample resourceInfoEntityExample = new ResourceInfoEntityExample();
        resourceInfoEntityExample.createCriteria().andAliasIn(lisResAlias);
        List<ResourceInfoEntity> lisResource = resourceInfoEntityMapper.selectByExample(resourceInfoEntityExample);
        Map<String, ResourceInfoEntity> mapResource = new HashMap<>();
        for (ResourceInfoEntity resourceInfoEntity : lisResource) {
            mapResource.put(resourceInfoEntity.getAlias(), resourceInfoEntity);
        }
        //绑定区域信息
        for (DspBillCheckDetailRespDto dspBillCheckDetailRespDto : data) {
            if (mapResource.containsKey(dspBillCheckDetailRespDto.getResAlias())) {
                dspBillCheckDetailRespDto.setResName(mapResource.get(dspBillCheckDetailRespDto.getResAlias()).getResName());

                if (StringUtils.isNotEmpty(dspBillCheckDetailRespDto.getCity())) {
                    String cityName = handleSpecialCity(dspBillCheckDetailRespDto.getCity());
                    if (cityName == null) {
                        RegionVo regionVo = regionService.queryCityById(Integer.valueOf(dspBillCheckDetailRespDto.getCity()));
                        if (regionVo != null)
                            cityName = regionVo.getRegionName();
                    }
                    dspBillCheckDetailRespDto.setCityStr(cityName);
                }

            }
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
