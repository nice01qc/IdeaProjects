/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.service;

import java.util.List;

import com.wanda.ffanad.base.common.Page;
import com.wanda.ffanad.base.common.ResultBo;
import com.wanda.ffanad.crm.dto.req.AdxAlreadyReviewListReqVo;
import com.wanda.ffanad.crm.dto.req.AdxReviewOperateReqVo;
import com.wanda.ffanad.crm.dto.req.AdxWaitReviewListReqVo;
import com.wanda.ffanad.crm.dto.resp.AdxReviewListResVo;

/**
 * 类AdxReviewManagerService.java的实现描述：adx审核管理service
 * 
 * @author liuzhenkai1 2016年10月19日 下午5:01:11
 */
public interface AdxReviewManagerService {
    /**
     * 校验审核列表参数
     * 
     * @param arlv
     * @return
     */
    boolean checkWaitReviewListParams(AdxWaitReviewListReqVo arlv);

    /**
     * 查询审核列表页
     * 
     * @param arlv
     * @return
     */
    Page<AdxReviewListResVo> getWaitReviewListPage(AdxWaitReviewListReqVo arlv);

    /**
     * 获取所有的dsp
     * 
     * @return
     */
    List<String> getAllDsp();

    /**
     * 审核
     * 
     * @param aror
     * @return
     */
    ResultBo operateReview(AdxReviewOperateReqVo aror);

    /**
     * 校验审核参数
     * 
     * @param aror
     * @return
     */
    boolean checkOperateReviewParams(AdxReviewOperateReqVo aror);

    /**
     * 校验已审核列表页参数
     * 
     * @param aarlv
     * @return
     */
    boolean checkAlreadyReviewListParams(AdxAlreadyReviewListReqVo aarlv);

    /**
     * 获取已审核列表页数据
     * 
     * @param aarlv
     * @return
     */
    Page<AdxReviewListResVo> getAlreadyReviewListPage(AdxAlreadyReviewListReqVo aarlv);

    /**
     * 根据ID获取创意审核信息
     * 
     * @param id
     * @return
     */
    AdxReviewListResVo getCreationReviewById(String id);

}
