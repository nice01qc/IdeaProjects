package com.wanda.ffanad.crm.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.common.utils.EXCELUtils;
import com.wanda.ffanad.core.common.FfanadStatus;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.crm.dto.req.PromoteAnalysisRequest;
import com.wanda.ffanad.crm.integration.analysis.PromoteAnalysisApiService;
import com.wanda.ffanad.crm.integration.analysis.resp.DspCityStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspDailyStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspResStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspStat;
import com.wanda.ffanad.crm.integration.analysis.resp.DspStatSummary;
import com.wanda.ffanad.crm.integration.analysis.resp.DspTerminalStat;

import io.swagger.annotations.Api;

/**
 * 类PromoteAnalysisController.java的实现描述：投放分析查询接口
 * 
 * @author Yao 2016年9月23日 下午1:18:24
 */
@Api(tags = { "投放分析查询接口" })
@RestController
@RequestMapping(value = "/promoteAnalysis")
public class PromoteAnalysisController {

    private Logger                    logger = LoggerFactory.getLogger(PromoteAnalysisController.class);

    @Autowired
    private PromoteAnalysisApiService promoteAnalysisService;

    /**
     * 汇总信息
     * 
     * @param session
     * @return
     */
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult summary(@Validated PromoteAnalysisRequest request) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        DspStatSummary summaryDate = promoteAnalysisService.summaryData(request.getDspCode(), beginDate.toDate(),
                endDate.toDate());

        return new RestResult(FfanadStatus.S_OK, summaryDate);
    }

    /**
     * 按DSP维度统计
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/details/dsp", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult statDsp(@Validated PromoteAnalysisRequest request) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        List<DspStat> dspStats = promoteAnalysisService.countByDsp(request.getDspCode(), beginDate.toDate(), endDate.toDate());

        return new RestResult(FfanadStatus.S_OK, dspStats);
    }

    /**
     * 按DSP维度统计导出excel
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/details/dsp/export", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult statDspExport(@Validated PromoteAnalysisRequest request, HttpServletResponse response) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        List<DspStat> dspStats = promoteAnalysisService.countByDsp(request.getDspCode(), beginDate.toDate(), endDate.toDate());

        //文件名
        String fileName = "按DSP维度分析.xls";
        //文件标题
        List<Object> head = new ArrayList<Object>();
        head.add("投放来源");
        head.add("展示量(次)");
        head.add("点击量(次)");
        head.add("点击率");
        head.add("千次展示成本(元)");
        head.add("平均点击成本(元)");
        head.add("消耗金额(元)");
        //文件数据
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        for (DspStat dspStat : dspStats) {
            List<Object> data = new ArrayList<Object>();
            data.add(dspStat.getDspName());
            data.add(dspStat.getDisplayCount());
            data.add(dspStat.getClickCount());
            data.add(dspStat.getClickRate() + "%");
            data.add(dspStat.getCpmAmount());
            data.add(dspStat.getCpcAmount());
            data.add(dspStat.getAmount());
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
            logger.error("导出excel出错" + e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "导出excel出错");
        } catch (Exception e) {
            logger.error("导出excel出错" + e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "导出excel出错");
        }
        return new RestResult(FfanadStatus.S_OK, "导出成功");
    }

    /**
     * 按日统计查询
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/details/daily", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult dsilyStat(@Validated PromoteAnalysisRequest request) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        List<DspDailyStat> dailyStats = promoteAnalysisService.dailyCount(request.getDspCode(), beginDate.toDate(),
                endDate.toDate());

        return new RestResult(FfanadStatus.S_OK, dailyStats);
    }

    /**
     * 按日统计查询导出exce
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/details/daily/export", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult dsilyStatExport(@Validated PromoteAnalysisRequest request, HttpServletResponse response) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        List<DspDailyStat> dailyStats = promoteAnalysisService.dailyCount(request.getDspCode(), beginDate.toDate(),
                endDate.toDate());

        //文件名
        String fileName = "按时间维度分析.xls";
        //文件标题
        List<Object> head = new ArrayList<Object>();
        head.add("时间(日)");
        head.add("展示量(次)");
        head.add("点击量(次)");
        head.add("点击率");
        head.add("千次展示成本(元)");
        head.add("平均点击成本(元)");
        head.add("消耗金额(元)");
        //文件数据
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        for (DspDailyStat dailyStat : dailyStats) {
            List<Object> data = new ArrayList<Object>();
            data.add(new DateTime(dailyStat.getDate()).toString("yyyy.MM.dd"));
            data.add(dailyStat.getDisplayCount());
            data.add(dailyStat.getClickCount());
            data.add(dailyStat.getClickRate() + "%");
            data.add(dailyStat.getCpmAmount());
            data.add(dailyStat.getCpcAmount());
            data.add(dailyStat.getAmount());
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
            logger.error("导出excel出错" + e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "导出excel出错");
        } catch (Exception e) {
            logger.error("导出excel出错" + e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "导出excel出错");
        }
        return new RestResult(FfanadStatus.S_OK, "导出成功");
    }

    /**
     * 按资源位统计查询
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/details/resource", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult resStat(@Validated PromoteAnalysisRequest request) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        List<DspResStat> resStats = promoteAnalysisService.countByResource(request.getDspCode(), beginDate.toDate(),
                endDate.toDate());

        return new RestResult(FfanadStatus.S_OK, resStats);
    }

    /**
     * 按资源位统计查询导出excel
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/details/resource/export", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult resStatExport(@Validated PromoteAnalysisRequest request, HttpServletResponse response) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        List<DspResStat> resStats = promoteAnalysisService.countByResource(request.getDspCode(), beginDate.toDate(),
                endDate.toDate());

        //文件名
        String fileName = "按广告位对比分析.xls";
        //文件标题
        List<Object> head = new ArrayList<Object>();
        head.add("广告位");
        head.add("展示量(次)");
        head.add("点击量(次)");
        head.add("点击率");
        head.add("千次展示成本(元)");
        head.add("平均点击成本(元)");
        head.add("消耗金额(元)");
        //文件数据
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        for (DspResStat dailyStat : resStats) {
            List<Object> data = new ArrayList<Object>();
            data.add(dailyStat.getResourceName());
            data.add(dailyStat.getDisplayCount());
            data.add(dailyStat.getClickCount());
            data.add(dailyStat.getClickRate() + "%");
            data.add(dailyStat.getCpmAmount());
            data.add(dailyStat.getCpcAmount());
            data.add(dailyStat.getAmount());
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
            logger.error("导出excel出错" + e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "导出excel出错");
        } catch (Exception e) {
            logger.error("导出excel出错" + e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "导出excel出错");
        }
        return new RestResult(FfanadStatus.S_OK, "导出成功");
    }

    /**
     * 按终端统计查询
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/details/terminal", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult statTerminal(@Validated PromoteAnalysisRequest request) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        List<DspTerminalStat> terminalStats = promoteAnalysisService.countByTerminal(request.getDspCode(), beginDate.toDate(),
                endDate.toDate());

        return new RestResult(FfanadStatus.S_OK, terminalStats);
    }

    /**
     * 按终端统计查询导出excel
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/details/terminal/export", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult statTerminalExport(@Validated PromoteAnalysisRequest request, HttpServletResponse response) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        List<DspTerminalStat> terminalStats = promoteAnalysisService.countByTerminal(request.getDspCode(), beginDate.toDate(),
                endDate.toDate());

        //文件名
        String fileName = "按投放终端分析.xls";
        //文件标题
        List<Object> head = new ArrayList<Object>();
        head.add("终端");
        head.add("展示量(次)");
        head.add("点击量(次)");
        head.add("点击率");
        head.add("千次展示成本(元)");
        head.add("平均点击成本(元)");
        head.add("消耗金额(元)");
        //文件数据
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        for (DspTerminalStat dailyStat : terminalStats) {
            List<Object> data = new ArrayList<Object>();
            data.add(dailyStat.getTerminalName());
            data.add(dailyStat.getDisplayCount());
            data.add(dailyStat.getClickCount());
            data.add(dailyStat.getClickRate() + "%");
            data.add(dailyStat.getCpmAmount());
            data.add(dailyStat.getCpcAmount());
            data.add(dailyStat.getAmount());
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
            logger.error("导出excel出错" + e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "导出excel出错");
        } catch (Exception e) {
            logger.error("导出excel出错" + e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "导出excel出错");
        }
        return new RestResult(FfanadStatus.S_OK, "导出成功");
    }

    /**
     * 按城市统计查询
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/details/city", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult statCity(@Validated PromoteAnalysisRequest request) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        List<DspCityStat> cityStats = promoteAnalysisService.countByCity(request.getDspCode(), beginDate.toDate(),
                endDate.toDate());

        return new RestResult(FfanadStatus.S_OK, cityStats);
    }

    /**
     * 按城市统计查询到出excel
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/details/city/export", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult statCityExport(@Validated PromoteAnalysisRequest request, HttpServletResponse response) {
        DateTime beginDate = new DateTime(request.getBeginDate());
        DateTime endDate = new DateTime(request.getEndDate());
        List<DspCityStat> cityStats = promoteAnalysisService.countByCity(request.getDspCode(), beginDate.toDate(),
                endDate.toDate());

        //文件名
        String fileName = "按城市对比分析.xls";
        //文件标题
        List<Object> head = new ArrayList<Object>();
        head.add("城市");
        head.add("展示量(次)");
        head.add("点击量(次)");
        head.add("点击率");
        head.add("千次展示成本(元)");
        head.add("平均点击成本(元)");
        head.add("消耗金额(元)");
        //文件数据
        List<List<Object>> dataList = new ArrayList<List<Object>>();
        for (DspCityStat dailyStat : cityStats) {
            List<Object> data = new ArrayList<Object>();
            data.add(dailyStat.getCity());
            data.add(dailyStat.getDisplayCount());
            data.add(dailyStat.getClickCount());
            data.add(dailyStat.getClickRate() + "%");
            data.add(dailyStat.getCpmAmount());
            data.add(dailyStat.getCpcAmount());
            data.add(dailyStat.getAmount());
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
            logger.error("导出excel出错" + e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "导出excel出错");
        } catch (Exception e) {
            logger.error("导出excel出错" + e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "导出excel出错");
        }
        return new RestResult(FfanadStatus.S_OK, "导出成功");
    }
}
