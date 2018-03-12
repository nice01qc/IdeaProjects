/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.ffanad.base.common.Page;
import com.wanda.ffanad.base.common.ResultBo;
import com.wanda.ffanad.base.constants.OperationLogOperation;
import com.wanda.ffanad.base.constants.OperationLogPage;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.dal.entities.TFfanadAdxDspEntity;
import com.wanda.ffanad.base.error.WrongParamException;
import com.wanda.ffanad.base.message.CacheEventDispatcher;
import com.wanda.ffanad.common.constants.Constant;
import com.wanda.ffanad.common.redis.RedisUtil;
import com.wanda.ffanad.core.common.redis.RedisCacheConstants;
import com.wanda.ffanad.core.domains.OperationLog;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.crm.dto.req.AdxDspDetailReqVo;
import com.wanda.ffanad.crm.dto.req.AdxDspPageReqVo;
import com.wanda.ffanad.crm.dto.req.AdxDspStatusReqVo;
import com.wanda.ffanad.crm.dto.req.AdxReviewOperateReqVo;
import com.wanda.ffanad.crm.dto.req.AdxWaitReviewListReqVo;
import com.wanda.ffanad.crm.dto.resp.AdxDspDetailResVo;
import com.wanda.ffanad.crm.dto.resp.AdxDspListResVo;
import com.wanda.ffanad.crm.dto.resp.AdxReviewListResVo;
import com.wanda.ffanad.crm.mappers.ext.TFfanadAdxCreationEntityMapperExt;
import com.wanda.ffanad.crm.mappers.ext.TFfanadAdxDspEntityMapperExt;
import com.wanda.ffanad.crm.service.AdxDspManagerService;
import com.wanda.ffanad.crm.utils.DateUtil;

/**
 * 类AdxDspManagerServiceImpl.java的实现描述：adxdsp管理service实现类
 * 
 * @author liuzhenkai1 2016年10月20日 上午11:24:03
 */
@Service
public class AdxDspManagerServiceImpl implements AdxDspManagerService {
    private Logger                            logger = LoggerFactory.getLogger(AdxDspManagerServiceImpl.class);
    @Autowired
    private TFfanadAdxDspEntityMapperExt      tadeMapperExt;
    /**
     * 创意审核
     */
    @Autowired
    private TFfanadAdxCreationEntityMapperExt taceMapperExt;
    @Autowired
    private CacheEventDispatcher              cacheEventDispatcher;

    @Autowired
    private OperationLogService               operationLogService;

    @Override
    public boolean checkAddDspParams(AdxDspDetailReqVo addv) {
        return checkCommonDspParam(addv);
    }

    /**
     * 校验公用的dsp参数
     * 
     * @param addv
     * @return
     */
    private boolean checkCommonDspParam(AdxDspDetailReqVo addv) {
        boolean name = StringUtils.isNoneBlank(addv.getName()) && addv.getName().length() <= Constant.INT_TWENTY;
        //0像对方获取广告1实时竞价
        boolean accessMode = addv.getAccessMode().matches(Constant.CommonReg.ZERO_OR_ONE);
        //为空或者中间不含空格,长度小于等于255位
        boolean appkey = StringUtils.isBlank(addv.getAppkey()) || (StringUtils.isNoneBlank(addv.getAppkey())
                && addv.getAppkey().length() < Constant.INT_TWO_HUNDRED_AND_FIFTY_SIX);
        //为空或者中间不含空格,长度小于等于255位
        boolean appsecret = StringUtils.isBlank(addv.getAppsecret()) || (StringUtils.isNoneBlank(addv.getAppsecret())
                && addv.getAppsecret().length() < Constant.INT_TWO_HUNDRED_AND_FIFTY_SIX);
        //为空或者保留2位小数
        boolean cpm = StringUtils.isBlank(addv.getCpm()) || (addv.getCpm().matches(Constant.CommonReg.NUMBER_TWO_DEC));
        //为空或者200个字符以内以http https开头的字符串
        boolean feedbackUrl = StringUtils.isBlank(addv.getFeedbackUrl())
                || ((addv.getFeedbackUrl().startsWith(Constant.HTTP_START)
                        || addv.getFeedbackUrl().startsWith(Constant.HTTPS_START)) && addv.getFeedbackUrl().length() <= 200);
        //N不需要 Y需要
        boolean creationReview = addv.getCreationReview().matches(Constant.CommonReg.Y_OR_N);

        return name && accessMode && appkey && appsecret && cpm && feedbackUrl && creationReview;
    }

    @Override
    @Transactional
    public ResultBo addDsp(AdxDspDetailReqVo addv) {
        ResultBo rv = new ResultBo();
        //转换到保存的格式
        TFfanadAdxDspEntity tad = convertParamToSave(addv);
        int result = tadeMapperExt.getDspNameCount(tad.getName());
        if (result > 0) {
            rv.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            rv.setMessage("dsp名称不能重复");
            return rv;
        }
        //保存adx dsp,返回主键
        tadeMapperExt.insertAdxDsp(tad);
        addv.setId(tad.getId().toString());
        operationLogService.addOperationLog(OperationLogPage.NEW_DSP, OperationLogOperation.NEW,
                addv.getAccount().getAccountEmail(), "DSP新建成功", tad.getName(), "DSP新建成功");
        return rv;
    }

    /**
     * 转换页面参数到入表格式
     * 
     * @param addv
     * @return
     */
    private TFfanadAdxDspEntity convertParamToSave(AdxDspDetailReqVo addv) {
        TFfanadAdxDspEntity tad = new TFfanadAdxDspEntity();
        tad.setName(addv.getName());
        //adx的别名生成逻辑，adx_   加上  名
        tad.setAlias(getNameAlias(addv.getName()));
        tad.setAccessMode(Byte.valueOf(addv.getAccessMode()));
        tad.setAppkey(StringUtils.isNotBlank(addv.getAppkey()) ? addv.getAppkey() : null);
        tad.setAppsecret(StringUtils.isNotBlank(addv.getAppsecret()) ? addv.getAppsecret() : null);
        //假如有填写cpm
        if (StringUtils.isNotBlank(addv.getCpm())) {
            //单位是元，乘以100*100000
            BigDecimal cpm = new BigDecimal(addv.getCpm()).multiply(Constant.TEN_MILLION);
            tad.setCpm(cpm.longValue());
        }
        tad.setFeedbackUrl(StringUtils.isNotBlank(addv.getFeedbackUrl()) ? addv.getFeedbackUrl() : null);
        tad.setCreationReview(addv.getCreationReview());
        //默认启用
        tad.setStatus(Constant.YES);
        AccountEntity account = addv.getAccount();
        //appkey 通过创建者的id加上当前毫秒数拼接而成
        String appkey = generateKey(account.getAccountId().toString());
        //appSercret 通过appkey做一次md5加密而成
        String sercret = generateSercret(appkey);
        tad.setFfanAdxAppkey(appkey);
        tad.setFfanAdxAppsecret(sercret);
        tad.setCreaterId(account.getAccountId().longValue());
        tad.setCreaterName(account.getAccountEmail());
        tad.setUpdaterId(account.getAccountId().longValue());
        tad.setUpdaterName(account.getAccountEmail());
        tad.setCreateTime(new Date());
        tad.setUpdateTime(new Date());
        tad.setIsDeleted(Constant.NO);
        return tad;
    }

    /**
     * 获取别名
     * 
     * @return
     */
    private String getNameAlias(String name) {
        return DigestUtils.md5Hex(Constant.ADX_KEY + name);
    }

    /**
     * appkey获取
     * 
     * @param confuseStr
     * @return
     */
    private String generateKey(String confuseStr) {
        //key是用 以微秒计的当前时间, 考虑Java不能拿到微妙，毫秒可能会重复   
        return System.currentTimeMillis() + "" + confuseStr;
    }

    /**
     * 获取sercret 用md5加密appkey
     * 
     * @param key
     * @return
     */
    private String generateSercret(String key) {
        //secret用 是key做了个md5
        return DigestUtils.md5Hex(key);
    }

    @Override
    public boolean checkAdxDspPageParam(AdxDspPageReqVo adpv) {
        return true;
    }

    @Override
    public Page<AdxDspListResVo> getDspPage(AdxDspPageReqVo adpv) {
        Page<AdxDspListResVo> page = new Page<AdxDspListResVo>();
        try {
            int total = tadeMapperExt.getTotalCountDspPage(adpv);
            int pageNo = adpv.getPage();
            int pageSize = adpv.getLimit(); //默认每页20条
            //假如数据总数为空或者页码超出最大数量，返回空的page.
            if (total <= 0 || (pageNo - 1) * pageSize > total) {
                return page;
            }
            adpv.setPageStartNo((pageNo - 1) * pageSize);
            List<AdxDspListResVo> adxDspPageList = tadeMapperExt.getDspPageList(adpv);
            formatDate(adxDspPageList);
            page = new Page<AdxDspListResVo>(adxDspPageList, pageNo, pageSize, total);

        } catch (RuntimeException re) {
            logger.error(re.getMessage());
            page.setStatus(Constant.Http.ERROR);
            page.setMsg(Constant.TipString.ERR_MSG);
        }

        return page;
    }

    /**
     * 格式化参数
     * 
     * @param adxDspPageList
     */
    private void formatDate(List<AdxDspListResVo> adxDspPageList) {
        for (AdxDspListResVo apv : adxDspPageList) {
            String cpm = apv.getCpm();
            if (StringUtils.isNotBlank(cpm)) {
                //存表的时候会乘以一千万，现在需要除去
                cpm = new BigDecimal(cpm).divide(Constant.TEN_MILLION).toString();
            }
            apv.setCpm(cpm);
            //从缓存中判断是否可以更新token
            if (checkCanUpdateToken(getUpdateTokenRedisCacheKey(Long.valueOf(apv.getId())))) {
                apv.setCanUpdateToken(Constant.S_ONE);
            } else {
                apv.setCanUpdateToken(Constant.S_ZERO);
            }
        }
    }

    @Override
    public boolean checkModifyDspParams(AdxDspDetailReqVo addv) {
        boolean id = StringUtils.isNotBlank(addv.getId()) && addv.getId().matches(Constant.CommonReg.NUMBER);
        return id && checkCommonDspParam(addv);
    }

    @Override
    @Transactional
    public ResultBo modifyDsp(AdxDspDetailReqVo addv) {
        ResultBo rv = new ResultBo();
        //转换到保存的格式
        TFfanadAdxDspEntity tad = convertParamToModify(addv);

        AdxDspStatusReqVo adsrv = new AdxDspStatusReqVo();
        adsrv.setId(addv.getId());
        String dspName = getDspById(adsrv).getName();
        AdxDspDetailResVo addrv = tadeMapperExt.getDspById(adsrv);
        if (addrv == null) {
            rv.setMessage("参数有误");
            rv.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            return rv;
        }
        List<String> idList = new ArrayList<>();
        //假如原来需要创意审核现在改成不需要创意审核
        if (Constant.YES.equals(addrv.getCreationReview()) && Constant.NO.equals(tad.getCreationReview())) {
            String dspId = addv.getId();
            //将该dsp所有未审核通过的创意修改为审核通过
            idList = auditPassCreation(dspId, addv);
        }

        int result = tadeMapperExt.modifyDsp(tad);
        if (result != 1) {
            rv.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            rv.setMessage("更新失败");
            return rv;
        }

        operationLogService.addOperationLog(OperationLogPage.MODIFY_DSP, OperationLogOperation.EDIT_INFO,
                addv.getAccount().getAccountEmail(), "DSP更新成功", tad.getName(), "原DSP名称:" + dspName);
        //假如不为空，发送Kakfa
        if (CollectionUtils.isNotEmpty(idList)) {
            cacheEventDispatcher.dispatchDspCreation(idList);
        }
        return rv;
    }

    /**
     * 将未审核的创意修改为审核通过，并记录日志
     * 
     * @param dspId
     * @param addv
     * @return
     */
    private List<String> auditPassCreation(String dspId, AdxDspDetailReqVo addv) {
        List<AdxReviewListResVo> hasWaitedReviewList = taceMapperExt.getWaitReviewListByDspId(dspId);
        List<String> idList = new ArrayList<>();
        //假如不为空
        if (!CollectionUtils.isEmpty(hasWaitedReviewList)) {
            int count = taceMapperExt.reviewAllWaitReviewCreationByDspId(dspId);
            if (count == hasWaitedReviewList.size()) {
                saveOperateReviewLog(hasWaitedReviewList, addv);
            } else {
                //有数据没有被更新到
                throw new RuntimeException();
            }
            //取出本次审核通过的消息，返回准备发送kakfa
            for (AdxReviewListResVo ar : hasWaitedReviewList) {
                idList.add(ar.getId());
            }

        }
        return idList;
    }

    /**
     * 保存审核日志
     * 
     * @param aror
     */
    private void saveOperateReviewLog(List<AdxReviewListResVo> awrlrList, AdxDspDetailReqVo addv) {
        List<OperationLog> oList = new ArrayList<OperationLog>();
        String logAction = "第三方创意审核通过";
        for (AdxReviewListResVo awrlrv : awrlrList) {
            OperationLog ol = new OperationLog();
            ol.setLogPage(OperationLogPage.CREATION_REVIEW);
            ol.setLogOperation(OperationLogOperation.REVIEW);
            ol.setLogUserEmail(addv.getAccount().getAccountEmail());
            ol.setLogAction(logAction);
            ol.setLogTarget(awrlrv.getUrl());
            ol.setLogRemark("不需要事前审核创意");
            oList.add(ol);
        }
        operationLogService.addOperationLog(oList);
    }

    /**
     * 转换参数到modify
     * 
     * @param addv
     * @return
     */
    private TFfanadAdxDspEntity convertParamToModify(AdxDspDetailReqVo addv) {
        TFfanadAdxDspEntity tad = new TFfanadAdxDspEntity();
        tad.setId(Long.valueOf(addv.getId()));
        tad.setName(addv.getName());
        //adx的别名生成逻辑，adx_   加上  名
        tad.setAlias(getNameAlias(addv.getName()));
        tad.setAccessMode(Byte.valueOf(addv.getAccessMode()));
        tad.setAppkey(addv.getAppkey());
        tad.setAppsecret(addv.getAppsecret());
        //假如有填写cpm
        if (StringUtils.isNotBlank(addv.getCpm())) {
            //单位是元，乘以100*100000
            BigDecimal cpm = new BigDecimal(addv.getCpm()).multiply(Constant.TEN_MILLION);
            tad.setCpm(cpm.longValue());
        }
        tad.setFeedbackUrl(addv.getFeedbackUrl());
        tad.setCreationReview(addv.getCreationReview());
        AccountEntity account = addv.getAccount();
        tad.setUpdaterId(account.getAccountId().longValue());
        tad.setUpdaterName(account.getAccountEmail());
        tad.setUpdateTime(new Date());
        return tad;
    }

    @Override
    @Transactional
    public ResultBo updateDspStatus(AdxDspStatusReqVo adsv) {
        ResultBo rv = new ResultBo();
        if (StringUtils.isBlank(adsv.getId()) || !adsv.getId().matches(Constant.CommonReg.NUMBER)
                || StringUtils.isBlank(adsv.getStatus()) || !adsv.getStatus().matches(Constant.CommonReg.Y_OR_N)) {
            rv.setMessage("参数有误");
            rv.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            return rv;
        }
        TFfanadAdxDspEntity tad = new TFfanadAdxDspEntity();
        tad.setId(Long.valueOf(adsv.getId()));
        tad.setStatus(adsv.getStatus());
        AccountEntity account = adsv.getAccount();
        tad.setUpdaterId(account.getAccountId().longValue());
        tad.setUpdaterName(account.getAccountEmail());
        tad.setUpdateTime(new Date());
        int result = tadeMapperExt.updateDspStatus(tad);
        String operateName = Constant.YES.equals(adsv.getStatus()) ? "启用" : "停用";
        if (result != 1) {
            rv.setMessage(operateName + "失败");
            rv.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            return rv;
        }
        String dspName = getDspById(adsv).getName();
        operationLogService.addOperationLog(OperationLogPage.STOP_DSP, OperationLogOperation.STOP,
                adsv.getAccount().getAccountEmail(), "DSP" + operateName + "成功", dspName, "DSP" + operateName + "成功");
        return rv;
    }

    @Override
    @Transactional
    public ResultBo updateDspToken(AdxDspStatusReqVo adsv) {
        ResultBo rv = new ResultBo();
        if (StringUtils.isBlank(adsv.getId()) || !adsv.getId().matches(Constant.CommonReg.NUMBER)
                || !Constant.S_ONE.equals(adsv.getStatus())) {
            rv.setMessage("参数有误");
            rv.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            return rv;
        }

        if (!checkCanUpdateToken(getUpdateTokenRedisCacheKey(Long.valueOf(adsv.getId())))) {
            rv.setMessage("一天只能更新2次");
            rv.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            return rv;
        }

        //初始化更新token的参数
        TFfanadAdxDspEntity tad = initUpdateDspTokenObj(adsv);
        int result = tadeMapperExt.updateDspToken(tad);
        if (result != 1) {
            rv.setMessage("停用失败");
            rv.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            return rv;
        }
        String dspName = getDspById(adsv).getName();
        operationLogService.addOperationLog(OperationLogPage.MODIFY_DSP, OperationLogOperation.STOP,
                adsv.getAccount().getAccountEmail(), "DSP更新token成功", dspName, "DSP更新token成功");

        updateUpdateTokenCache(adsv);
        return rv;
    }

    /**
     * 初始化更新token的参数
     * 
     * @param adsv
     * @return
     */
    private TFfanadAdxDspEntity initUpdateDspTokenObj(AdxDspStatusReqVo adsv) {
        TFfanadAdxDspEntity tad = new TFfanadAdxDspEntity();
        tad.setId(Long.valueOf(adsv.getId()));
        AccountEntity account = adsv.getAccount();
        tad.setUpdaterId(account.getAccountId().longValue());
        tad.setUpdaterName(account.getAccountEmail());
        tad.setUpdateTime(new Date());
        //appkey 通过创建者的id加上当前毫秒数拼接而成
        String appkey = generateKey(account.getAccountId().toString());
        //appSercret 通过appkey做一次md5加密而成
        String sercret = generateSercret(appkey);
        //tad.setFfanAdxAppkey(appkey);
        tad.setFfanAdxAppsecret(sercret);
        return tad;
    }

    /**
     * 更新token 缓存的 值
     * 
     * @param adsv
     */
    private void updateUpdateTokenCache(AdxDspStatusReqVo adsv) {
        try {
            String tokenKey = getUpdateTokenRedisCacheKey(Long.valueOf(adsv.getId()));
            String size = RedisUtil.getString(tokenKey);
            RedisUtil.setStringEx(tokenKey, DateUtil.getNowToTomorrowTimeStamp().intValue(),
                    StringUtils.isBlank(size) ? "1" : String.valueOf((Integer.valueOf(size) + 1)));
            size = RedisUtil.getString(tokenKey);
            if (Integer.valueOf(size) > 2) {
                //假如更新后的值大于2次，抛错回滚数据库
                throw new WrongParamException();
            }
        } catch (Exception re) {
            if (re instanceof WrongParamException) {
                throw new WrongParamException();
            }
        }
    }

    @Override
    public AdxDspDetailResVo getDspById(AdxDspStatusReqVo adsv) {
        AdxDspDetailResVo addrv = tadeMapperExt.getDspById(adsv);
        return formatDetailData(addrv);
    }

    /**
     * 更新明细值
     * 
     * @param addrv
     * @return
     */
    private AdxDspDetailResVo formatDetailData(AdxDspDetailResVo addrv) {
        String cpm = addrv.getCpm();
        if (StringUtils.isNotBlank(cpm)) {
            //存表的时候会乘以一千万，现在需要除去
            cpm = new BigDecimal(cpm).divide(Constant.TEN_MILLION).toString();
        }
        addrv.setCpm(cpm);
        //从缓存中判断是否可以更新token
        if (checkCanUpdateToken(getUpdateTokenRedisCacheKey(Long.valueOf(addrv.getId())))) {
            addrv.setCanUpdateToken(Constant.S_ONE);
        } else {
            addrv.setCanUpdateToken(Constant.S_ZERO);
        }
        return addrv;
    }

    /**
     * 根据redis key 获取当天是否可以更新dsp token
     * 
     * @return
     */
    private boolean checkCanUpdateToken(String redisCache) {
        try {
            String count = RedisUtil.getString(redisCache);
            if (StringUtils.isBlank(count) || Constant.S_ONE.equals(count)) {
                return true;
            }
        } catch (Exception e) {
            //假如失效，返回true
            return true;
        }
        return false;
    }

    /**
     * 拼接redis cachekey
     * 
     * @param id
     * @return
     */
    private String getUpdateTokenRedisCacheKey(long id) {
        return RedisCacheConstants.ADX_DSP_UPDATE_TOKEN + RedisCacheConstants.CACHE_KEY_SPLIT + "ID" + Constant.UNDERLINE + id;
    }
}
