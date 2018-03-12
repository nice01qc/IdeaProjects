/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.ffanad.base.common.ResultBo;
import com.wanda.ffanad.base.constants.OperationLogOperation;
import com.wanda.ffanad.base.constants.OperationLogPage;
import com.wanda.ffanad.base.dal.entities.LandingPageInfoEntity;
import com.wanda.ffanad.base.dal.entities.LandingPageInfoEntityExample;
import com.wanda.ffanad.base.dal.mappers.LandingPageInfoEntityMapper;
import com.wanda.ffanad.base.redis.RedisCacheKeys;
import com.wanda.ffanad.common.constants.Constant;
import com.wanda.ffanad.common.redis.RedisUtil;
import com.wanda.ffanad.core.domains.OperationLog;
import com.wanda.ffanad.core.domains.vo.req.CreationStatusReqVo;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.crm.dto.resp.LandingPageDetailDto;
import com.wanda.ffanad.crm.service.LandingPageService;

/**
 * 类LandingPageServiceImpl.java的实现描述：h5登录页信息service
 * 
 * @author liuzhenkai1 2016年11月30日 下午4:31:37
 */
@Service("landingPageService")
public class LandingPageServiceImpl implements LandingPageService {
    private Logger                      logger = LoggerFactory.getLogger(LandingPageServiceImpl.class);

    @Value("${adstatic.url}")
    private String                      adstaticUrl;

    @Autowired
    private LandingPageInfoEntityMapper landingPageInfoEntityMapper;
    @Autowired
    private OperationLogService         operationLogService;

    @Override
    public LandingPageDetailDto getH5LandingPageDetail(Integer id) {
        LandingPageInfoEntity ls = landingPageInfoEntityMapper.selectByPrimaryKey(id);
        LandingPageDetailDto ld = new LandingPageDetailDto();
        ld.setAuditReason(ls.getAuditReason());
        ld.setAuditStatus(ls.getAuditReason());
        ld.setCreationName(ls.getCreationName());
        ld.setPageBody(ls.getPageBody());
        ld.setPageTitle(ls.getPageTitle());
        ld.setPageUrl(ls.getLandingPageUrl());
        ld.setResourceType(ls.getResourceLocationType());
        ld.setUserId(ls.getUserId());
        ld.setUserName(ls.getUserName());
        return ld;
    }

    @Override
    @Transactional
    public ResultBo auditH5LandingPage(CreationStatusReqVo crAuditResult, String accountEmail) {
        ResultBo rb = new ResultBo();
        rb.setMessage("操作成功");

        if (!crAuditResult.getAuditStatus().toString().matches(Constant.CommonReg.ONE_OR_TWO)) {
            rb.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            rb.setMessage("参数有误");
            return rb;
        }
        LandingPageInfoEntity lpiewb = new LandingPageInfoEntity();
        lpiewb.setId(crAuditResult.getId().intValue());
        lpiewb = landingPageInfoEntityMapper.selectByPrimaryKey(crAuditResult.getId().intValue());
        //校验参数
        if (lpiewb == null) {
            rb.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            rb.setMessage("着陆页ID有误");
            return rb;
        }

        if (lpiewb.getIsDeleted() == true) {
            rb.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            rb.setMessage("该数据已被删除");
            return rb;
        }

        //加载要更新的参数
        initAuditH5LandingPageParam(crAuditResult, lpiewb);
        String pageMd5 = DigestUtils.md5Hex(lpiewb.getId() + "-" + lpiewb.getCreationName());
        String redisCache = RedisCacheKeys.H5LandingKeys.H5_LANDING_PAGE(pageMd5);
        //假如是审核通过请求，需要上传文件到redis，驳回的话需要从redis中删除key
        if ("1".equals(crAuditResult.getAuditStatus().toString())) {
            //            byte[] fileByte = getFileByte(lpiewb);
            //            try {
            //                String landingPageUrl = fileManagerService.uploadFileByte(fileByte, lpiewb.getCreationName() + lpiewb.getId());
            //                lpiewb.setLandingPageUrl(landingPageUrl);
            //            } catch (IOException ie) {
            //                logger.error("文件上传失败，文件名" + lpiewb.getCreationName() + "--着陆页id:" + lpiewb.getId());
            //                rb.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            //                rb.setMessage("操作失败");
            //                return rb;
            //            }
            String page = getCompletePage(lpiewb.getPageTitle(), lpiewb.getPageBody());

            //缓存redis
            RedisUtil.setString(redisCache, page);
            lpiewb.setPageMd5(pageMd5);
            lpiewb.setLandingPageUrl(adstaticUrl + "/" + pageMd5);
        } else {
            RedisUtil.del(redisCache);
            lpiewb.setLandingPageUrl("");
            //将md5至空
            lpiewb.setPageMd5("");
        }

        //更新数据
        LandingPageInfoEntityExample lpieExample = new LandingPageInfoEntityExample();
        //根据id且不能是审核成功的
        lpieExample.createCriteria().andIdEqualTo(lpiewb.getId());

        int result = landingPageInfoEntityMapper.updateByExampleWithBLOBs(lpiewb, lpieExample);
        if (result != 1) {
            rb.setStatus(Constant.ResultStatus.RESULT_STATUS_FAILED);
            rb.setMessage("操作失败");
            return rb;
        }
        saveOperateReviewLog(lpiewb, accountEmail);
        return rb;
    }

    /**
     * 拼接成完整的page
     * 
     * @param title
     * @param body
     * @return
     */
    private String getCompletePage(String title, String body) {
        return Constant.H5_LANDING_PAGE_CONTEXT.replace(Constant.H5_LANDING_PAGE_TITLE, title)
                .replace(Constant.H5_LANDING_PAGE_BODY, body);
    }

    /**
     * 初始化更新参数
     * 
     * @param crAuditResult
     * @param lpiewb
     * @param lbs
     */
    private void initAuditH5LandingPageParam(CreationStatusReqVo crAuditResult, LandingPageInfoEntity lpiewb) {
        lpiewb.setAuditReason(crAuditResult.getAuditReason());
        lpiewb.setAuditStatus(crAuditResult.getAuditStatus().byteValue());
        lpiewb.setUpdateTime(new Date());
    }

    //    /**
    //     * 将着陆页拼接成byte数据用于上传
    //     * 
    //     * @param lpiewb
    //     * @return
    //     */
    //    private byte[] getFileByte(LandingPageInfoEntityWithBLOBs lpiewb) {
    //        String page = Constant.H5_LANDING_PAGE_CONTEXT.replace(Constant.H5_LANDING_PAGE_TITLE, lpiewb.getPageTitle())
    //                .replace(Constant.H5_LANDING_PAGE_BODY, lpiewb.getPageBody());
    //        return page.getBytes();
    //    }

    /**
     * 保存审核日志
     * 
     * @param lpiewb
     */
    private void saveOperateReviewLog(LandingPageInfoEntity lpiewb, String accountEmail) {
        List<OperationLog> oList = new ArrayList<OperationLog>();
        String logAction = "创意审核" + (Constant.S_TWO.equals(lpiewb.getAuditStatus().toString()) ? "不通过" : "通过");
        OperationLog ol = new OperationLog();
        ol.setLogPage(OperationLogPage.CREATION_AUDIT);
        ol.setLogOperation(Constant.S_TWO.equals(lpiewb.getStatus()) ? OperationLogOperation.AUDIT_FAIL
                : OperationLogOperation.AUDIT_SUCCRESS);
        ol.setLogUserEmail(accountEmail);
        ol.setLogAction(logAction);
        ol.setLogTarget(lpiewb.getId().toString());
        ol.setLogRemark(lpiewb.getCreationName());
        oList.add(ol);
        operationLogService.addOperationLog(oList);
    }

    @Override
    public String getH5LandingPageView(String pageMd5) {
        //查询example
        LandingPageInfoEntityExample lpieExample = new LandingPageInfoEntityExample();
        //审核成功且未删除的
        lpieExample.createCriteria().andPageMd5EqualTo(pageMd5).andIsDeletedEqualTo(false).andAuditStatusEqualTo((byte) 1);

        List<LandingPageInfoEntity> lpiewbList = landingPageInfoEntityMapper.selectByExampleWithBLOBs(lpieExample);

        String redisCache = RedisCacheKeys.H5LandingKeys.H5_LANDING_PAGE(pageMd5);
        String page = "404,page no found";
        //假如数据库中有数据，加载进缓存
        if (CollectionUtils.isNotEmpty(lpiewbList)) {
            LandingPageInfoEntity lpiewb = lpiewbList.get(0);
            page = getCompletePage(lpiewb.getPageTitle(), lpiewb.getPageBody());
        }
        //缓存redis
        RedisUtil.setString(redisCache, page);
        return page;
    }

    @Override
    public String viewPageLanding(String pageMd5) {
        String redisCache = RedisCacheKeys.H5LandingKeys.H5_LANDING_PAGE(pageMd5);

        String page = RedisUtil.getString(redisCache);
        if (StringUtils.isBlank(page)) {
            //更新数据
            LandingPageInfoEntityExample lpieExample = new LandingPageInfoEntityExample();
            //审核成功且未删除的
            lpieExample.createCriteria().andPageMd5EqualTo(pageMd5).andIsDeletedEqualTo(false).andAuditStatusEqualTo((byte) 1);

            List<LandingPageInfoEntity> lpiewbList = landingPageInfoEntityMapper.selectByExampleWithBLOBs(lpieExample);

            //假如数据库中有数据，加载进缓存
            if (CollectionUtils.isNotEmpty(lpiewbList)) {
                LandingPageInfoEntity lpiewb = lpiewbList.get(0);
                page = getCompletePage(lpiewb.getPageTitle(), lpiewb.getPageBody());
            } else {
                page = "404,page no found";
            }
            //缓存redis
            RedisUtil.setString(redisCache, page);
        }
        return page;
    }

    @Override
    public void refushLadingPageCache(String email) {
        logger.error(email + "refush h5landingpage on " + new Date());
        //查询example
        LandingPageInfoEntityExample lpieExample = new LandingPageInfoEntityExample();
        //审核成功且未删除的
        lpieExample.createCriteria().andIsDeletedEqualTo(false).andAuditStatusEqualTo((byte) 1);

        List<LandingPageInfoEntity> lpiewbList = landingPageInfoEntityMapper.selectByExampleWithBLOBs(lpieExample);
        //假如数据库中有数据，加载进缓存
        if (CollectionUtils.isNotEmpty(lpiewbList)) {
            int i = 0;
            for (LandingPageInfoEntity ls : lpiewbList) {
                if (i > 5000) {
                    break;
                }
                String page = getCompletePage(ls.getPageTitle(), ls.getPageBody());
                String redisCache = RedisCacheKeys.H5LandingKeys.H5_LANDING_PAGE(ls.getPageMd5());
                //缓存redis
                RedisUtil.setString(redisCache, page);
                i++;
            }
        }
    }

}
