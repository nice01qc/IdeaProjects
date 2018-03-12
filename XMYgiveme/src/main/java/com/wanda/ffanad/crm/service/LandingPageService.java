/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.service;

import com.wanda.ffanad.base.common.ResultBo;
import com.wanda.ffanad.core.domains.vo.req.CreationStatusReqVo;
import com.wanda.ffanad.crm.dto.resp.LandingPageDetailDto;

/**
 * 类LandingPageService.java的实现描述：着陆页操作service
 * 
 * @author liuzhenkai1 2016年11月30日 下午4:30:04
 */
public interface LandingPageService {

    /**
     * 获取h5登录页详情信息
     * 
     * @param id
     * @return
     */
    LandingPageDetailDto getH5LandingPageDetail(Integer id);

    /**
     * 审核着陆页
     * 
     * @param crAuditResult
     * @param accountEmail
     * @return
     */
    ResultBo auditH5LandingPage(CreationStatusReqVo crAuditResult, String accountEmail);

    /**
     * 获取md5并存入redis
     * 
     * @param pageMd5
     * @return
     */
    String getH5LandingPageView(String pageMd5);

    /**
     * 访问着陆页
     * 
     * @param pageMd5
     * @return
     */
    String viewPageLanding(String pageMd5);

    /**
     * 刷新着陆页redis缓存
     * @param accountEmail 
     */
    void refushLadingPageCache(String accountEmail);

}
