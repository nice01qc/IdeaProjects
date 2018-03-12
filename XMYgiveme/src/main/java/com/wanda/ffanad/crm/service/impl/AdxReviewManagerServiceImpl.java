/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.wanda.ffanad.base.dal.entities.TFfanadAdxCreationDetectUrlEntity;
import com.wanda.ffanad.base.message.CacheEventDispatcher;
import com.wanda.ffanad.common.constants.Constant;
import com.wanda.ffanad.common.utils.DateUtil;
import com.wanda.ffanad.core.domains.OperationLog;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.crm.dto.req.AdxAlreadyReviewListReqVo;
import com.wanda.ffanad.crm.dto.req.AdxReviewOperateReqVo;
import com.wanda.ffanad.crm.dto.req.AdxWaitReviewListReqVo;
import com.wanda.ffanad.crm.dto.resp.AdxReviewListResVo;
import com.wanda.ffanad.crm.mappers.ext.TFfanadAdxCreationDetectUrlEntityMapperExt;
import com.wanda.ffanad.crm.mappers.ext.TFfanadAdxCreationEntityMapperExt;
import com.wanda.ffanad.crm.mappers.ext.TFfanadAdxDspEntityMapperExt;
import com.wanda.ffanad.crm.service.AdxReviewManagerService;

/**
 * 类AdxReviewManagerServiceImpl.java的实现描述：adx审核service实现类
 * 
 * @author liuzhenkai1 2016年10月20日 上午11:24:42
 */
@Service
public class AdxReviewManagerServiceImpl implements AdxReviewManagerService {

    private Logger                                     logger = LoggerFactory.getLogger(AdxReviewManagerServiceImpl.class);

    /**
     * 创意审核
     */
    @Autowired
    private TFfanadAdxCreationEntityMapperExt          taceMapperExt;

    /**
     * dsp管理
     */
    @Autowired
    private TFfanadAdxDspEntityMapperExt               tadeMapperExt;
    @Autowired
    private TFfanadAdxCreationDetectUrlEntityMapperExt tacdeMapperExt;

    @Autowired
    private OperationLogService                        operationLogService;
    @Autowired
    private CacheEventDispatcher                       cacheEventDispatcher;

    @Override
    public boolean checkWaitReviewListParams(AdxWaitReviewListReqVo arlv) {
        if (StringUtils.isBlank(arlv.getHasThrowTime())) {
            arlv.setHasThrowTime(Constant.S_ZERO);
        }
        if (StringUtils.isBlank(arlv.getHasUpdateTime())) {
            arlv.setHasUpdateTime(Constant.S_ZERO);
        }
        boolean result = true;
        boolean hasThrowTime = StringUtils.isNotBlank(arlv.getHasThrowTime())
                && arlv.getHasThrowTime().matches(Constant.CommonReg.ZERO_OR_ONE);
        boolean hasUpdateTime = StringUtils.isNotBlank(arlv.getHasUpdateTime())
                && arlv.getHasUpdateTime().matches(Constant.CommonReg.ZERO_OR_ONE);
        if (!hasThrowTime || !hasUpdateTime) {
            result = false;
        }
        //假如选择了投放时间
        if (result && Constant.S_ONE.equals(arlv.getHasThrowTime())) {
            if (!checkTwoTimeString(arlv.getThrowTimeStart(), arlv.getThrowTimeEnd(), Constant.DateFormat.YYYY_MM_DD)) {
                result = false;
            }
        }
        if (result && Constant.S_ONE.equals(arlv.getHasUpdateTime())) {
            if (!checkTwoTimeString(arlv.getUpdateTimeStart(), arlv.getUpdateTimeEnd(), Constant.DateFormat.YYYY_MM_DD)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public Page<AdxReviewListResVo> getWaitReviewListPage(AdxWaitReviewListReqVo arlv) {
        Page<AdxReviewListResVo> page = new Page<AdxReviewListResVo>();
        try {
            int total = taceMapperExt.getTotalCountWaitReviewPage(arlv);
            int pageNo = arlv.getPage();
            int pageSize = arlv.getLimit(); //默认每页20条
            //假如数据总数为空或者页码超出最大数量，返回空的page.
            if (total <= 0 || (pageNo - 1) * pageSize > total) {
                return page;
            }
            arlv.setPageStartNo((pageNo - 1) * pageSize);
            List<AdxReviewListResVo> adxReviewPageList = taceMapperExt.getWaitReviewPageList(arlv);
            for (AdxReviewListResVo arlr : adxReviewPageList) {
                List<TFfanadAdxCreationDetectUrlEntity> detectUrlList = tacdeMapperExt.getDetectUrlByCreationId(arlr.getId());
                //分析获取到的监控链接，存入审核url
                saveDetectUrlToReviewVo(arlr, detectUrlList);
            }
            page = new Page<AdxReviewListResVo>(adxReviewPageList, pageNo, pageSize, total);

        } catch (RuntimeException re) {
            logger.error(re.getMessage());
            page.setStatus(Constant.Http.ERROR);
            page.setMsg(Constant.TipString.ERR_MSG);
        }

        return page;
    }

    /***
     * 分析获取到的监控链接，存入审核url
     * 
     * @param arlr
     * @param detectUrlList
     */
    private void saveDetectUrlToReviewVo(AdxReviewListResVo arlr, List<TFfanadAdxCreationDetectUrlEntity> detectUrlList) {
        if (!CollectionUtils.isEmpty(detectUrlList)) {
            List<String> showDetectUrlList = arlr.getShowDetectUrlList();
            List<String> clickDetectUrlList = arlr.getClickDetectUrlList();
            for (TFfanadAdxCreationDetectUrlEntity tacd : detectUrlList) {
                if (tacd.getLinkType().byteValue() == (byte) 0) {
                    showDetectUrlList.add(tacd.getDetectUrl());
                } else if (tacd.getLinkType().byteValue() == (byte) 1) {
                    clickDetectUrlList.add(tacd.getDetectUrl());
                }
            }
        }

    }

    @Override
    public List<String> getAllDsp() {

        return tadeMapperExt.getAllDsp();
    }

    @Override
    @Transactional
    public ResultBo operateReview(AdxReviewOperateReqVo aror) {
        aror.setIdList(new ArrayList<String>(Arrays.asList(aror.getIdString().split(","))));
        ResultBo rb = new ResultBo();
        aror.setAccountId(aror.getAccount().getAccountId().toString());
        aror.setAccountName(aror.getAccount().getAccountEmail());
        //假如审核为退回，添加退回原因,否则置为空
        aror.setNoAdoptMsg(Constant.S_TWO.equals(aror.getStatus()) ? (aror.getQuestionClass() + " > " + aror.getQuestion()
                + (StringUtils.isNotBlank(aror.getNoAdoptMsg()) ? (" > " + aror.getNoAdoptMsg()) : "")) : null);
        List<AdxReviewListResVo> ls = taceMapperExt.getWaitReviewListByIdList(aror.getIdList());
        if(ls.size()==0){
            rb.setMessage("本次没有数据更新");
            rb.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            return rb;
        }
        //处理待审核的数据
        handleWaitedReviewIdList(aror, ls);
        int result = taceMapperExt.operateReviewById(aror);
        rb.setMessage("本次成功审核" + result + "条创意");
        //更新到的条数跟传参的条数不符，可能是id有误     20161109--不再回滚
        //        if (result != aror.getIdList().size()) {
        //            logger.error("参数异常，更新到的条数跟传参的条数不符，idList为" + aror.getIdList());
        //            throw new WrongParamException();
        //        }
        cacheEventDispatcher.dispatchDspCreation(aror.getIdList());
        //保存审核日志
        saveOperateReviewLog(aror, ls);
        return rb;
    }

    /**
     * 根据当前的数据库中的审核状态判断是否需要更新审核状态
     * 
     * @param aror
     * @param ls
     */
    private void handleWaitedReviewIdList(AdxReviewOperateReqVo aror, List<AdxReviewListResVo> ls) {
        List<String> idListNew = new ArrayList<>();
        String reviewStatus = aror.getStatus();
        //假如当前的待审核创意的审核状态跟准备操作的状态相同，则跳过创意
        for (AdxReviewListResVo arlr : ls) {
            if (!arlr.getReviewStatus().equals(reviewStatus)) {
                idListNew.add(arlr.getId());
            }
        }
        aror.setIdList(idListNew);
    }

    /**
     * 保存审核日志
     * 
     * @param aror
     */
    private void saveOperateReviewLog(AdxReviewOperateReqVo aror, List<AdxReviewListResVo> awrlrList) {
        List<OperationLog> oList = new ArrayList<OperationLog>();
        String logAction = "第三方创意审核" + (Constant.S_TWO.equals(aror.getStatus()) ? "退回" : "通过");
        for (AdxReviewListResVo awrlrv : awrlrList) {
            OperationLog ol = new OperationLog();
            ol.setLogPage(OperationLogPage.CREATION_REVIEW);
            ol.setLogOperation(OperationLogOperation.REVIEW);
            ol.setLogUserEmail(aror.getAccountName());
            ol.setLogAction(logAction);
            ol.setLogTarget(awrlrv.getUrl());
            ol.setLogRemark(StringUtils.isBlank(aror.getNoAdoptMsg()) ? "" : aror.getNoAdoptMsg());
            oList.add(ol);
        }
        operationLogService.addOperationLog(oList);
    }

    @Override
    public boolean checkOperateReviewParams(AdxReviewOperateReqVo aror) {
        boolean idString = false;
        if (StringUtils.isNotBlank(aror.getIdString())) {
            String[] idList = aror.getIdString().split(",");
            if (idList.length > 0) {
                idString = true;
            }
        }
        boolean status = aror.getStatus().matches(Constant.CommonReg.ONE_OR_TWO);
        //boolean idList = CollectionUtils.isNotEmpty(aror.getIdList()) && aror.getIdList().size() <= Constant.INT_TWENTY;
        boolean question = Constant.S_TWO.equals(aror.getStatus()) && StringUtils.isBlank(aror.getQuestion());
        boolean questionClass = Constant.S_TWO.equals(aror.getStatus()) && StringUtils.isBlank(aror.getQuestionClass());
        //审核不通过的退回原因不能为空
        boolean noAdopt = Constant.S_TWO.equals(aror.getStatus()) && StringUtils.isNotBlank(aror.getNoAdoptMsg())
                && aror.getNoAdoptMsg().length() > Constant.INT_ONE_HUNDRED;
        return status && idString && !question && !questionClass && !noAdopt;
    }

    @Override
    public boolean checkAlreadyReviewListParams(AdxAlreadyReviewListReqVo aarlv) {
        if (StringUtils.isBlank(aarlv.getHasThrowTime())) {
            aarlv.setHasThrowTime(Constant.S_ZERO);
        }
        if (StringUtils.isBlank(aarlv.getHasUpdateTime())) {
            aarlv.setHasUpdateTime(Constant.S_ZERO);
        }
        if (StringUtils.isBlank(aarlv.getHasReviewTime())) {
            aarlv.setHasReviewTime(Constant.S_ZERO);
        }
        boolean result = true;
        //为空或者为1/2
        boolean reviewStatus = StringUtils.isBlank(aarlv.getReviewStatus())
                || (aarlv.getReviewStatus().matches(Constant.CommonReg.ONE_OR_TWO));
        boolean hasReviewTime = StringUtils.isNotBlank(aarlv.getHasReviewTime())
                && aarlv.getHasReviewTime().matches(Constant.CommonReg.ZERO_OR_ONE);
        boolean hasThrowTime = StringUtils.isNotBlank(aarlv.getHasThrowTime())
                && aarlv.getHasThrowTime().matches(Constant.CommonReg.ZERO_OR_ONE);
        boolean hasUpdateTime = StringUtils.isNotBlank(aarlv.getHasUpdateTime())
                && aarlv.getHasUpdateTime().matches(Constant.CommonReg.ZERO_OR_ONE);
        if (!reviewStatus || !hasThrowTime || !hasUpdateTime || !hasReviewTime) {
            result = false;
        }
        //假如选择了投放时间
        if (result && Constant.S_ONE.equals(aarlv.getHasThrowTime())) {
            if (!checkTwoTimeString(aarlv.getThrowTimeStart(), aarlv.getThrowTimeEnd(), Constant.DateFormat.YYYY_MM_DD)) {
                result = false;
            }
        }
        if (result && Constant.S_ONE.equals(aarlv.getHasUpdateTime())) {
            if (!checkTwoTimeString(aarlv.getUpdateTimeStart(), aarlv.getUpdateTimeEnd(), Constant.DateFormat.YYYY_MM_DD)) {
                result = false;
            }
        }
        if (result && Constant.S_ONE.equals(aarlv.getHasReviewTime())) {
            if (!checkTwoTimeString(aarlv.getReviewTimeStart(), aarlv.getReviewTimeEnd(), Constant.DateFormat.YYYY_MM_DD)) {
                result = false;
            }
        }

        return result;
    }

    /**
     * 校验2个时间是否符合规则
     * 
     * @param timeStart
     * @param timeEnd
     * @param dateFormat
     * @return
     */
    private boolean checkTwoTimeString(String timeStart, String timeEnd, String dateFormat) {
        try {
            Date start = DateUtil.stringToDate(timeStart, dateFormat);
            Date end = DateUtil.stringToDate(timeEnd, dateFormat);
            if (end.before(start)) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Page<AdxReviewListResVo> getAlreadyReviewListPage(AdxAlreadyReviewListReqVo aarlv) {
        Page<AdxReviewListResVo> page = new Page<AdxReviewListResVo>();
        try {
            int total = taceMapperExt.getTotalCountAlreadyReviewPage(aarlv);
            int pageNo = aarlv.getPage();
            int pageSize = aarlv.getLimit(); //默认每页20条
            //假如数据总数为空或者页码超出最大数量，返回空的page.
            if (total <= 0 || (pageNo - 1) * pageSize > total) {
                return page;
            }
            aarlv.setPageStartNo((pageNo - 1) * pageSize);
            List<AdxReviewListResVo> adxReviewPageList = taceMapperExt.getAlreadyReviewPageList(aarlv);
            for (AdxReviewListResVo arlr : adxReviewPageList) {
                List<TFfanadAdxCreationDetectUrlEntity> detectUrlList = tacdeMapperExt.getDetectUrlByCreationId(arlr.getId());
                //分析获取到的监控链接，存入审核url
                saveDetectUrlToReviewVo(arlr, detectUrlList);
            }
            page = new Page<AdxReviewListResVo>(adxReviewPageList, pageNo, pageSize, total);

        } catch (RuntimeException re) {
            logger.error(re.getMessage());
            page.setStatus(Constant.Http.ERROR);
            page.setMsg(Constant.TipString.ERR_MSG);
        }

        return page;
    }

    @Override
    public AdxReviewListResVo getCreationReviewById(String id) {
        AdxReviewListResVo arlrv = taceMapperExt.getCreationReviewById(id);
        List<TFfanadAdxCreationDetectUrlEntity> detectUrlList = tacdeMapperExt.getDetectUrlByCreationId(arlrv.getId());
        //分析获取到的监控链接，存入审核url
        saveDetectUrlToReviewVo(arlrv, detectUrlList);
        return arlrv;
    }

}
