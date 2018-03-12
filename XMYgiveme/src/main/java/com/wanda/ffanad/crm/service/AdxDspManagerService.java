/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.service;

import com.wanda.ffanad.base.common.Page;
import com.wanda.ffanad.base.common.ResultBo;
import com.wanda.ffanad.crm.dto.req.AdxDspDetailReqVo;
import com.wanda.ffanad.crm.dto.req.AdxDspPageReqVo;
import com.wanda.ffanad.crm.dto.req.AdxDspStatusReqVo;
import com.wanda.ffanad.crm.dto.resp.AdxDspDetailResVo;
import com.wanda.ffanad.crm.dto.resp.AdxDspListResVo;

/**
 * 类AdxDspManagerService.java的实现描述：adx dps service
 * 
 * @author liuzhenkai1 2016年10月19日 下午5:00:32
 */
public interface AdxDspManagerService {

    /**
     * 校验新增dsp参数
     * 
     * @param addv
     * @return
     */
    boolean checkAddDspParams(AdxDspDetailReqVo addv);

    /**
     * 新增dsp
     * 
     * @param addv
     * @return
     */
    ResultBo addDsp(AdxDspDetailReqVo addv);

    /**
     * 校验dsp查询列表页参数
     * 
     * @param adpv
     * @return
     */
    boolean checkAdxDspPageParam(AdxDspPageReqVo adpv);

    /**
     * 获取dsp列表页page
     * 
     * @param adpv
     * @return
     */
    Page<AdxDspListResVo> getDspPage(AdxDspPageReqVo adpv);

    /**
     * 校验更新dsp参数
     * 
     * @param addv
     * @return
     */
    boolean checkModifyDspParams(AdxDspDetailReqVo addv);

    /**
     * 更新dsp
     * 
     * @param addv
     * @return
     */
    ResultBo modifyDsp(AdxDspDetailReqVo addv);

    /**
     * 更新dsp状态
     * 
     * @param adsv
     * @return
     */
    ResultBo updateDspStatus(AdxDspStatusReqVo adsv);

    /**
     * 更新dsp token
     * 
     * @param adsv
     * @return
     */
    ResultBo updateDspToken(AdxDspStatusReqVo adsv);

    /**
     * 根据id获取单个dsp
     * 
     * @param adsv
     * @return
     */
    AdxDspDetailResVo getDspById(AdxDspStatusReqVo adsv);

}
