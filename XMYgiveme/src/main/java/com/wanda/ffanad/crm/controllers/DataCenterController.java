package com.wanda.ffanad.crm.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.common.utils.DateUtil;
import com.wanda.ffanad.common.utils.EXCELUtils;
import com.wanda.ffanad.core.common.FfanadStatus;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.common.rest.PromoteRequest;
import com.wanda.ffanad.core.common.rest.PromoteResponse;
import com.wanda.ffanad.core.services.AccountService;
import com.wanda.ffanad.core.services.promote.TFfadPromoteService;
import com.wanda.ffanad.crm.datacenter.bo.AdAccountInfo;
import com.wanda.ffanad.crm.datacenter.bo.AdDaySummaryInfo;
import com.wanda.ffanad.crm.datacenter.bo.DataCenterBo;
import com.wanda.ffanad.crm.datacenter.bo.Promote;
import com.wanda.ffanad.crm.integration.promote.PromoteApiService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 数据中心 类DataCenterController.java的实现描述：TODO 类实现描述
 * 
 * @author czs 2016年6月6日 下午1:57:16
 */
@Api(tags = { "数据中心数据查询接口" })
@RestController
@RequestMapping(value = "/user/datacenter")
public class DataCenterController {

    private Logger              logger = LoggerFactory.getLogger(DataCenterController.class);
    @Autowired
    private TFfadPromoteService promoteService;

    @Autowired
    private PromoteApiService   promoteApiService;

    @Autowired
    private AccountService      accountService;

    /**
     * 用户数据中心初始化数据方法
     * 
     * @param session
     * @return 返回当前用户数据中心 的统计相关数据
     * @throws ParseException
     */
    @ApiOperation(value = "初始化")
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult init(HttpSession session) throws ParseException {
        //用户帐户id
        //数据中心业务对象
        DataCenterBo dataCenterBo = new DataCenterBo();

        //用户帐户金额数据
        AdAccountInfo accountInfo = new AdAccountInfo();

        AccountEntity account = accountService.selectCountByAccountId(null);
        if (account != null) {
            accountInfo.setAdBalance(CurrencyUtils.convert(account.getBalance()));
            accountInfo.setAdConsume(CurrencyUtils.convert(account.getAdAmount()));
        }

        dataCenterBo.setAdAccountData(accountInfo);
        PromoteRequest request = new PromoteRequest();
        // request.setStartDate(DateUtil.dateAdd(-3, DateUtil.getNowDate()));
        // request.setEndDate(DateUtil.dateAdd(-1, DateUtil.getNowDate()));
        //用户投放广告数据
        List<Promote> promotes = new ArrayList<Promote>();
        List<PromoteResponse> responsePromotes = promoteService.getPromotesByTime(request);
        for (PromoteResponse rps : responsePromotes) {
            Promote p = new Promote();
            p.setPromoteId(rps.getPromoteId());
            p.setPromoteName(rps.getPromoteName());
            promotes.add(p);
        }
        dataCenterBo.setAdData(promotes);

        dataCenterBo.setAdSummaryData(promoteApiService.getAdSummaryInfoByUserId(null));

        dataCenterBo.setAdDaySummaryDataPage(promoteApiService.getAdDaySumaryInfoPage(null, 0, 20,
                DateUtil.dateAdd(-3, DateUtil.getNowDate()), DateUtil.dateAdd(-1, DateUtil.getNowDate()), null));
        dataCenterBo.setAdDaySummaryData(promoteApiService.getAdDaySumaryInfo(null,
                DateUtil.dateAdd(-3, DateUtil.getNowDate()), DateUtil.dateAdd(-1, DateUtil.getNowDate()), null));

        return new RestResult(FfanadStatus.S_OK, dataCenterBo);
    }

    /**
     * 根据条件查询广告投放统计数据
     * 
     * @param session 会话
     * @param promoteId 投放广告id
     * @param days 时间
     * @param countType 统计类型
     * @return 返回按用户时间和投放广告的每日统计数据
     * @throws ParseException
     */
    @ApiOperation(value = "根据条件查询图表数据")
    @RequestMapping(value = "/chart", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult queryAdSummaryInfo(HttpSession session, @RequestBody DataCenterBo dataCeneterBo) throws ParseException {
        //数据中心业务对象
        DataCenterBo dataCenterBo = new DataCenterBo();
        String startDate = null;
        String endDate = null;
        if (dataCeneterBo.getDays() != null) {
            if (dataCeneterBo.getDays() == 3) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-3, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 7) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-7, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 30) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-30, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 90) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-90, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 1) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.monthAdd(-6, DateUtil.getNowDate());
            }

        }
        dataCenterBo.setAdDaySummaryData(
                promoteApiService.getAdDaySumaryInfo(null, startDate, endDate, dataCeneterBo.getPromoteId()));
        return new RestResult(FfanadStatus.S_OK, dataCenterBo);
    }

    /**
     * @param session 会话
     * @param promoteId 投放广告id
     * @param days 日期
     * @param pageNo 页面
     * @param pageSize 每页展示数量
     * @return 按用户和时间和投放广告的分页查询
     * @throws ParseException
     */
    @ApiOperation(value = "根据条件 分页查询数据")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult queryAdSummaryInfoByPage(HttpSession session, @RequestBody DataCenterBo dataCeneterBo)
            throws ParseException {
        //数据中心业务对象
        DataCenterBo dataCenterBo = new DataCenterBo();
        String startDate = null;
        String endDate = null;
        int start = dataCeneterBo.getOffset();
        int end = dataCeneterBo.getLimit();
        if (dataCeneterBo.getDays() != null) {
            if (dataCeneterBo.getDays() == 3) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-3, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 7) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-7, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 30) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-30, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 90) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-90, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 1) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.monthAdd(-6, DateUtil.getNowDate());
            }

        }

        dataCenterBo.setAdDaySummaryDataPage(
                promoteApiService.getAdDaySumaryInfoPage(null, start, end, startDate, endDate, dataCeneterBo.getPromoteId()));
        return new RestResult(FfanadStatus.S_OK, dataCenterBo);
    }

    /**
     * 导出用户每日导出数据
     * 
     * @param session 会话
     * @param request
     * @param response
     * @param promoteId 投放广告id
     * @param days 时间
     * @return 是否导出成功
     * @throws ParseException
     */
    @ApiOperation(value = "导出数据")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult export(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                             DataCenterBo dataCeneterBo)
            throws ParseException {

        String startDate = null;
        String endDate = null;
        if (dataCeneterBo.getDays() != null) {
            if (dataCeneterBo.getDays() == 3) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-3, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 7) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-7, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 30) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-30, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 90) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.dateAdd(-90, DateUtil.getNowDate());
            } else if (dataCeneterBo.getDays() == 1) {
                endDate = DateUtil.dateAdd(-1, DateUtil.getNowDate());
                startDate = DateUtil.monthAdd(-6, DateUtil.getNowDate());
            }

        }
        //文件标题
        List<Object> head = new ArrayList<Object>();
        head.add("日期");
        head.add("展示量");
        head.add("点击量");
        head.add("点击率");
        head.add("千次展示成本");
        head.add("单次展示成本");
        head.add("花费");
        //文件数据
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        //文件名
        String fileName = "【" + startDate + "至" + endDate + "】整体情况投放数据.xls";
        List<AdDaySummaryInfo> adDaySummaryInfo = promoteApiService.getAdDaySumaryInfo(null, startDate, endDate,
                dataCeneterBo.getPromoteId());

        for (AdDaySummaryInfo p : adDaySummaryInfo) {
            List<Object> data = new ArrayList<Object>();
            data.add(p.getTime());
            data.add(p.getShowNum());
            data.add(p.getClickNum());
            data.add(p.getClickRate());
            data.add(p.getCpm());
            data.add(p.getCpc());
            data.add(p.getConsume());
            dataList.add(data);
        }
        try {
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            EXCELUtils.exportExcel(head, dataList, response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (IOException e) {
            logger.error(e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, e.getMessage());
        }
        return new RestResult(FfanadStatus.S_OK, "导出成功");
    }

}
