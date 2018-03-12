package com.wanda.ffanad.crm.controllers;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.error.FfanadException;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.constants.ReturnCode;
import com.wanda.ffanad.core.domains.App;
import com.wanda.ffanad.core.services.AppService;
import com.wanda.ffanad.crm.common.PaginationBo;
import com.wanda.ffanad.crm.integration.promote.AppApiService;
import com.wanda.ffanad.crm.integration.promote.respdto.PromoteStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * App审核管理相关接口
 * 
 * @author tianhongbo
 */
@RestController
@RequestMapping("/app")
@SessionAttributes("user")
public class AppController {

    private Logger        logger = LoggerFactory.getLogger(AppController.class);
    @Autowired
    private AppService    service;

    @Autowired
    private AppApiService appApiService;

    /**
     * 获取资源列表
     * 
     * @author tianhongbo
     * @return
     */
    @RequestMapping(path = "/list", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public PaginationBo<App> list(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                  @RequestParam(required = false, defaultValue = "10") int pageSize, App app) {
        logger.info("收到GET请求:list");
        PaginationBo<App> pbo = new PaginationBo<App>();
        List<App> list = service.getAppPage(app, pageNo, pageSize);
        Map<Integer, App> mapApp = new HashMap<Integer, App>();
        List<Integer> lisAppIds = new ArrayList<Integer>();
        for (App entity : list) {
            lisAppIds.add(entity.getId());
            mapApp.put(entity.getId().intValue(), entity);
        }
        try {
            if (!CollectionUtils.isEmpty(lisAppIds)) {
                List<PromoteStatistic> lisApiResult = appApiService.queryAppStatistics(lisAppIds);
                for (PromoteStatistic promoteStatistic : lisApiResult) {
                    mapApp.get(promoteStatistic.getAppId()).setClickTimes(promoteStatistic.getClickCount());
                    mapApp.get(promoteStatistic.getAppId()).setShowTimes(promoteStatistic.getDisplayCount());

                    //计算点击率
                    Long clickCount = promoteStatistic.getClickCount();
                    Long displayCount = promoteStatistic.getDisplayCount();
                    if (clickCount != null && displayCount != null && displayCount != 0) {
                        double clickRate = (clickCount / (double) displayCount) * 100;
                        clickRate = new BigDecimal(clickRate).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        mapApp.get(promoteStatistic.getAppId()).setClickRate(clickRate + "%");
                    } else {
                        mapApp.get(promoteStatistic.getAppId()).setClickRate("0.00%");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("查询app统计出错:" + e.getMessage(), e);
        }
        pbo.setRows(list);
        pbo.setTotal(service.getAppPageTotal(app));
        return pbo;
    }

    /**
     * App审核通过
     * 
     * @return
     */
    @RequestMapping(path = "/auditPass", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult auditPass(@Valid @NotNull Integer id, HttpSession session) {
        //TODO shiyu 换成account中取email
        AccountEntity accountEntity = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        try {
            service.auditPass(id, accountEntity.getAccountEmail(), accountEntity.getAccountId());
            return new RestResult();
        } catch (FfanadException e) {
            logger.error("App审核失败！AppID:" + id + "。" + e.getMessage());
            return new RestResult(ReturnCode.FAIL, "App审核失败！");
        }
    }

    /**
     * app审核驳回
     * 
     * @param reason
     * @return
     */
    @RequestMapping(path = "/auditNotPass", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult auditNotPass(@Valid @NotNull Integer id, @Valid @NotNull String reason, HttpSession session) {
        //TODO shiyu 换成account中取email
        String email = (String) session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL);
        try {
            service.auditNotPass(id, reason, email);
            return new RestResult();
        } catch (Exception e) {
            logger.error("App审核驳回失败！AppID:" + id + "。" + e.getMessage());
            return new RestResult(ReturnCode.FAIL, "App审核失败！");
        }
    }

    /**
     * app详情
     * 
     * @author tianhongbo 2016年5月11日
     * @param id
     * @return 详细信息
     */
    @RequestMapping(value = "/getAppDetail", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    private App getAppDetail(String id) {
        return service.getAppDetail(Integer.parseInt(id));
    }
}
