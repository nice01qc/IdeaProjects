/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.mappers.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wanda.ffanad.crm.dto.req.AdxAlreadyReviewListReqVo;
import com.wanda.ffanad.crm.dto.req.AdxDspStatusReqVo;
import com.wanda.ffanad.crm.dto.req.AdxReviewOperateReqVo;
import com.wanda.ffanad.crm.dto.req.AdxWaitReviewListReqVo;
import com.wanda.ffanad.crm.dto.resp.AdxReviewListResVo;

/**
 * 类AdxReviewManagerEntityMapper.java的实现描述：adx审核管理mapper
 * 
 * @author liuzhenkai1 2016年10月20日 下午2:32:39
 */
public interface TFfanadAdxCreationEntityMapperExt {

    /**
     * 获取符合条件的审核列表总数
     * 
     * @param arlv
     * @return
     */
    int getTotalCountWaitReviewPage(@Param("arlv") AdxWaitReviewListReqVo arlv);

    /**
     * 查询审核列表list
     * 
     * @param arlv
     * @return
     */
    List<AdxReviewListResVo> getWaitReviewPageList(@Param("arlv") AdxWaitReviewListReqVo arlv);

    /**
     * 操作审核
     * 
     * @param aror
     * @return
     */
    int operateReviewById(@Param("aror") AdxReviewOperateReqVo aror);

    /**
     * 查找待审核的创意记录
     * 
     * @param list
     * @return
     */
    List<AdxReviewListResVo> getWaitReviewListByIdList(List<String> list);

    /**
     * 获取已审核的创意数据总数
     * 
     * @param aarlv
     * @return
     */
    int getTotalCountAlreadyReviewPage(@Param("aarlv") AdxAlreadyReviewListReqVo aarlv);

    /**
     * 获取已审核的创意数据
     * 
     * @param aarlv
     * @return
     */
    List<AdxReviewListResVo> getAlreadyReviewPageList(@Param("aarlv") AdxAlreadyReviewListReqVo aarlv);

    /**
     * 根据ID获取创意审核
     * 
     * @param id
     * @return
     */
    AdxReviewListResVo getCreationReviewById(String id);

    /**
     * 获取待审核的创意 根据dspid
     * 
     * @param dspId
     * @return
     */
    List<AdxReviewListResVo> getWaitReviewListByDspId(String dspId);

    /**
     * 根据dspid将所有未审核的创意变更为审核通过
     * @param dspId
     * @return
     */
    int reviewAllWaitReviewCreationByDspId(String dspId);

}
