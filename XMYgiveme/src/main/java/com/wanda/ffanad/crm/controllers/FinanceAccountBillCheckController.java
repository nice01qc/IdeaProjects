/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.enums.DspEnum;
import com.wanda.ffanad.base.error.AdAsserts;
import com.wanda.ffanad.common.utils.EXCELUtils;
import com.wanda.ffanad.base.error.FfanadException;
import com.wanda.ffanad.core.common.FfanadStatus;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.common.type.UserType;
import com.wanda.ffanad.core.constants.OperationLogOperation;
import com.wanda.ffanad.core.constants.OperationLogPage;
import com.wanda.ffanad.core.services.AccountService;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.crm.dto.FinanceAccountBillCheckDto;
import com.wanda.ffanad.crm.dto.resp.DspBillCheckDetailRespDto;
import com.wanda.ffanad.crm.dto.resp.DspBillCheckSummaryRespDto;
import com.wanda.ffanad.crm.integration.finance.service.FinanceAccountBillCheckService;
import com.wanda.ffanad.crm.utils.DateUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 财务管理
 * 
 * @author kevin
 */
@RestController
@RequestMapping("/finance")
public class FinanceAccountBillCheckController {
    private Logger                         log = LoggerFactory.getLogger(FinanceAccountBillCheckController.class);

    @Autowired
    private AccountService                 accountService;

    @Autowired
    private FinanceAccountBillCheckService financeAccountBillCheckService;

    @Autowired
    private OperationLogService            operationLogService;

    /**
     * 需求方月对账单
     *
     * @param accountDate
     * @param accountMail
     * @return
     * @author:yanji
     */
    @RequestMapping(path = "/requirement", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult requirement(@RequestParam String accountDate, @RequestParam(required = false) String accountMail) {
        try {
            String accountIds = "";
            if (StringUtils.isNotBlank(accountMail)) {
                AccountEntity account = new AccountEntity();
                account.setAccountEmail(accountMail);
                account.setUserType(UserType.DSP.getValue());// 有三种类型：1.CRM,2.SSP,3.DSP
                List<AccountEntity> userAccounts = accountService.selectByAccountMailUserType(account);
                if (CollectionUtils.isEmpty(userAccounts) || null == userAccounts) {
                    log.info("没有查到账户");
                    return new RestResult(0, "没有查到账户!");
                }
                int k = 0;
                for (AccountEntity userAccount : userAccounts) {
                    if (userAccount == null || userAccount.getAccountId() == null) {
                        log.error("用户账户异常");
                        // return new RestResult(0, "用户账户异常");
                    }
                    accountIds = accountIds + String.valueOf(userAccount.getAccountId());
                    if (k < userAccounts.size() - 1) {
                        accountIds = accountIds + ",";
                    }
                    k++;
                }
            }
            // 根据用户账户id和日期获取需求方月对账单
            List<FinanceAccountBillCheckDto> financeCheckDtos = financeAccountBillCheckService
                    .requirementAccountBillCheckByMonth(accountDate, accountIds.trim());
            if (CollectionUtils.isNotEmpty(financeCheckDtos)) {
                RestResult result = new RestResult();
                result.setResult(financeCheckDtos);
                return result;
            } else {
                log.info("需求方月账单数据返回空!");
                return new RestResult(0, "需求方月账单数据返回空");
            }

        } catch (FfanadException e) {
            log.error("获取需求方月对账单异常: error={}  ", e);
            return new RestResult(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("获取需求方月对账单异常: error={}  ", e);
            return new RestResult(0, e.getMessage());
        }

    }

    /**
     * 需求方月对账单
     *
     * @param accountDate
     * @return
     * @author:yanji
     */
    @RequestMapping(path = "/requirementByAccount", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult requirementByAccount(@RequestParam(required = true) String accountDate,
                                           @RequestParam(required = true) Integer accountId) {
        try {
            if (accountId == null) {
                return new RestResult(0, "用户Id不能为空");
            }
            if (StringUtils.isBlank(accountDate)) {
                return new RestResult(0, "账期不能为空");
            }
            // 根据用户账户id和日期获取需求方月对账单
            List<FinanceAccountBillCheckDto> financeCheckDtos = financeAccountBillCheckService
                    .requirementAccountBillCheckByMonth(accountDate, accountId + "");
            if (CollectionUtils.isNotEmpty(financeCheckDtos)) {
                RestResult result = new RestResult();
                result.setResult(financeCheckDtos);
                return result;
            } else {
                log.info("需求方月账单数据返回空!");
                return new RestResult(0, "需求方月账单数据返回空");
            }

        } catch (FfanadException e) {
            log.error("获取需求方月对账单异常: error={}  ", e);
            return new RestResult(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("获取需求方月对账单异常: error={}  ", e);
            return new RestResult(0, e.getMessage());
        }

    }

    /**
     * 需求方月对账单详情
     *
     * @param accountDate
     * @param accountMail
     * @return
     * @author:yanji
     */
    @RequestMapping(path = "/requirementDetail", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult requirementDetail(@RequestParam String accountDate, @RequestParam String accountMail) {
        try {
            AccountEntity account = new AccountEntity();
            account.setAccountEmail(accountMail);
            account.setUserType(UserType.DSP.getValue());// 有三种类型：1.CRM,2.SSP,3.DSP
            AccountEntity userAccount = accountService.findBySelective(account);

            if (userAccount == null || userAccount.getAccountId() == null) {
                log.error("用户账户异常");
                return new RestResult(0, "用户账户异常");
            }
            // 根据用户账户id和日期获取需求方月对账单
            List<FinanceAccountBillCheckDto> financeCheckDtos = financeAccountBillCheckService
                    .requirementAccountBillCheckByDaily(accountDate, userAccount.getAccountId() + "");
            if (CollectionUtils.isNotEmpty(financeCheckDtos)) {
                // 设置邮箱账户和公司名称
                for (FinanceAccountBillCheckDto dto : financeCheckDtos) {
                    dto.setAccountMail(accountMail);
                    dto.setCompany(dto.getCompany());
                }
                RestResult result = new RestResult();
                result.setResult(financeCheckDtos);
                return result;
            } else {
                log.info("需求方月账单数据返回空!");
                return new RestResult(0, "需求方月账单数据返回空");
            }

        } catch (FfanadException e) {
            log.error("获取需求方月对账单异常: error={}  ", e);
            return new RestResult(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("获取需求方月对账单异常: error={}  ", e);
            return new RestResult(0, e.getMessage());
        }

    }

    /**
     * 资源方月对账单
     *
     * @param accountDate
     * @param accountMail
     * @return
     * @author:yanji
     */
    @RequestMapping(path = "/resource", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult resource(@RequestParam String accountDate, @RequestParam(required = false) String accountMail,
                               @RequestParam String userRole) {
        if (StringUtils.isBlank(userRole)) {
            return new RestResult(0, "用户类型不能为空");
        }
        if (StringUtils.isBlank(accountDate)) {
            return new RestResult(0, "账期不能为空");
        }

        try {
            StringBuilder accountIds = new StringBuilder();
            // 高级用户 查询系统默认账户信息
            if (SystemConstant.ROLE_SYSTEM_SSP_SENIOR.equals(UserType.SSP.getValue() + "#" + userRole)) {
                accountIds.append(SystemConstant.SYSTEM_ACCOUNT_ID);
            } else if (SystemConstant.ROLE_SYSTEM_SSP_UNION.equals(UserType.SSP.getValue() + "#" + userRole)) {
                if (StringUtils.isNotBlank(accountMail)) {
                    AccountEntity account = new AccountEntity();
                    account.setAccountEmail(accountMail);
                    account.setUserType(UserType.SSP.getValue());// 有三种类型：1.CRM,2.SSP,3.DSP
                    List<AccountEntity> userAccounts = accountService.selectByAccountMailUserType(account);
                    if (CollectionUtils.isEmpty(userAccounts) || null == userAccounts) {
                        log.info("没有查到账户");
                        return new RestResult(0, "没有查到账户!");
                    }
                    for (AccountEntity userAccount : userAccounts) {
                        if (userAccount == null || userAccount.getAccountId() == null) {
                            log.error("用户账户异常");
                            // return new RestResult(0, "用户账户异常");
                            continue;
                        }
                        accountIds.append(userAccount.getAccountId()).append(",");
                    }
                    int lastIdx = accountIds.lastIndexOf(",");
                    if (lastIdx > 0) {
                        accountIds = accountIds.deleteCharAt(lastIdx);
                    }
                }

            } else {
                return new RestResult(0, "用户类型非法");
            }
            // 根据用户账户id和日期获取需求方月对账单
            List<FinanceAccountBillCheckDto> financeCheckDtos = financeAccountBillCheckService
                    .resourceAccountBillCheckByMonth(accountDate, accountIds.toString(), userRole);
            if (CollectionUtils.isNotEmpty(financeCheckDtos)) {
                RestResult result = new RestResult();
                result.setResult(financeCheckDtos);
                return result;
            } else {
                log.info("资源方月账单数据返回空!");
                return new RestResult(0, "资源方月账单数据返回空");
            }

        } catch (FfanadException e) {
            log.error("获取资源方月对账单异常: error={}  ", e);
            return new RestResult(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("获取资源方月对账单异常: error={}  ", e);
            return new RestResult(0, e.getMessage());
        }

    }

    /**
     * 资源方月对账单
     *
     * @param accountDate
     * @return
     * @author:yanji
     */
    @RequestMapping(path = "/resourceByAccount", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult resourceByAccount(@RequestParam String accountDate, @RequestParam(required = true) Integer accountId,
                                        @RequestParam String userRole) {
        if (StringUtils.isBlank(userRole)) {
            return new RestResult(0, "用户类型不能为空");
        }
        if (StringUtils.isBlank(accountDate)) {
            return new RestResult(0, "账期不能为空");
        }
        if (accountId == null) {
            return new RestResult(0, "用户Id不能为空");
        }

        try {
            // 根据用户账户id和日期获取需求方月对账单
            List<FinanceAccountBillCheckDto> financeCheckDtos = financeAccountBillCheckService
                    .resourceAccountBillCheckByMonth(accountDate, accountId + "", userRole);
            if (CollectionUtils.isNotEmpty(financeCheckDtos)) {
                RestResult result = new RestResult();
                result.setResult(financeCheckDtos);
                return result;
            } else {
                log.info("资源方月账单数据返回空!");
                return new RestResult(0, "资源方月账单数据返回空");
            }

        } catch (FfanadException e) {
            log.error("获取资源方月对账单异常: error={}  ", e);
            return new RestResult(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("获取资源方月对账单异常: error={}  ", e);
            return new RestResult(0, e.getMessage());
        }

    }

    /**
     * 资源方月对账单详情
     *
     * @param accountDate
     * @return
     * @author:yanji
     */
    @RequestMapping(path = "/resourceDetail", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult resourceDetail(@RequestParam String accountDate, @RequestParam String accountId) {
        if (StringUtils.isBlank(accountId)) {
            return new RestResult(0, "用户类型不能为空");
        }
        if (StringUtils.isBlank(accountDate)) {
            return new RestResult(0, "账期不能为空");
        }

        try {
            AccountEntity account = accountService.getByAccountId(Integer.parseInt(accountId));

            if (account == null || account.getAccountId() == null) {
                log.error("用户账户异常");
                return new RestResult(0, "用户账户异常");
            }
            // 根据用户账户id和日期获取需求方月对账单
            Map<String, Map<Long, List<FinanceAccountBillCheckDto>>> financeChecs = financeAccountBillCheckService
                    .resourceAccountBillCheckByDaily(accountDate, account.getAccountId() + "");
            if (MapUtils.isNotEmpty(financeChecs)) {
                // 设置邮箱账户和公司名称
                // for (Map.Entry<String, Map<Long,
                // List<FinanceAccountBillCheckDto>>> enty :
                // financeChecs.entrySet()) {
                // Map<Long, List<FinanceAccountBillCheckDto>> mapRes =
                // enty.getValue();
                // for (Map.Entry<Long, List<FinanceAccountBillCheckDto>>
                // entyRes : mapRes.entrySet()) {
                // for (FinanceAccountBillCheckDto dto : entyRes.getValue()) {
                // dto.setAccountMail(account.getAccountEmail());
                // dto.setCompany(dto.getCompany());
                // }
                // }
                // }
                RestResult result = new RestResult();
                result.setResult(financeChecs);
                return result;
            } else {
                log.info("资源方月账单detail数据返回空!");
                return new RestResult(0, "资源方月账单数据返回空");
            }

        } catch (FfanadException e) {
            log.error("获取资源方月对账单detail异常: error={}  ", e);
            return new RestResult(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("获取资源方月对账单detail异常: error={}  ", e);
            return new RestResult(0, e.getMessage());
        }
    }

    /**
     * 资源方月对账单打款状态更新 修改月账单打款状态为已打款
     * 
     * @return
     * @author:yanji
     */
    @RequestMapping(path = "/paymentStatus", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult paymentStatus(@RequestParam String accountDate, @RequestParam Integer accountId, HttpSession session) {
        if (accountId == null) {
            return new RestResult(0, "用户账户异常");
        }
        if (StringUtils.isBlank(accountDate)) {
            return new RestResult(0, "账期不能为空");
        }
        try {
            AccountEntity account = accountService.getByAccountId(accountId);
            if (account == null || account.getAccountId() == null) {
                log.error("用户账户异常");
                return new RestResult(0, "用户账户异常");
            }
            RestResult restResult = financeAccountBillCheckService.updateResourceAccountBillCheckPaysStatus(accountDate,
                    account.getAccountId() + "");

            //记录日志
            AccountEntity nowLoginAccount = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
            operationLogService.addOperationLog(OperationLogPage.RESOURCE_BILLS, OperationLogOperation.SETTED,
                    nowLoginAccount.getAccountEmail(), OperationLogPage.UPDATE_STATUS_PAYED, account.getAccountEmail(),
                    "更新" + accountDate + "的账单为已打款状态");

            return restResult;
        } catch (FfanadException e) {
            log.error("获取资源方月对账单detail异常: error={}  ", e);
            return new RestResult(e.getStatus(), e.getMessage());
        } catch (Exception e) {
            log.error("获取资源方月对账单detail异常: error={}  ", e);
            return new RestResult(0, e.getMessage());
        }
    }

    /**
     * 月对账单导出
     *
     * @return
     * @author:yanji
     */
    @ApiOperation(value = "导出月账单数据")
    @RequestMapping(value = "/export", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult export(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                             @RequestParam String accountDate, @RequestParam Integer accountId, @RequestParam int checkParty) {
        //当前登录用户
        AccountEntity account = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);

        String checkPartyStr = (checkParty == 1 ? "花费" : "收入") + " (元）";
        List<Object> head = new ArrayList<>();
        if (checkParty == 2) {
            head.add("APP名称");
            head.add("资源位名称");
        }
        head.add("账户邮箱");
        head.add("公司名称");
        head.add("日期");
        if (checkParty == 1) {
            head.add("有效投放（个）");
        }
        head.add("总展示（次）");
        head.add("总点击（次）");
        head.add("CTR");
        head.add("CPM展示数（次）");
        head.add("CPM" + checkPartyStr);
        head.add("CPC点击数（次）");
        head.add("CPC" + checkPartyStr);
        head.add("独占" + checkPartyStr);
        head.add("总" + checkPartyStr);

        List<List<Object>> dataList = new ArrayList<List<Object>>();
        AccountEntity userAccount = accountService.getByAccountId(accountId);
        if (userAccount == null || userAccount.getAccountId() == null) {
            log.error("用户账户异常");
            return new RestResult(0, "用户账户异常");
        }
        if (checkParty == 1) {// 调需求方detail
            // 根据用户账户id和日期获取需求方月对账单
            List<FinanceAccountBillCheckDto> financeCheckDtos = financeAccountBillCheckService
                    .requirementAccountBillCheckByDaily(accountDate, userAccount.getAccountId() + "");
            if (CollectionUtils.isNotEmpty(financeCheckDtos)) {
                for (FinanceAccountBillCheckDto dto : financeCheckDtos) {
                    List<Object> data = new ArrayList<Object>();
                    data.add(userAccount.getAccountEmail());
                    data.add(userAccount.getCompany());
                    data.add(dto.getDate());
                    data.add(dto.getEffectivePromoteCount());
                    data.add(dto.getDisplayCount());
                    data.add(dto.getClickCount());
                    data.add(dto.getCtr());
                    data.add(dto.getCpmDisplayCount());
                    data.add(dto.getCpmAmount());
                    data.add(dto.getCpcClickCount());
                    data.add(dto.getCpcAmount());
                    data.add(dto.getOccupyAmount());
                    data.add(dto.getAmount());
                    dataList.add(data);
                }
                // return new RestResult();
            } else {
                log.info("需求方月账单数据返回空!");
                return new RestResult(0, "需求方月账单数据返回空");
            }

        } else {// 资源方detail
            // 根据用户账户id和日期获取资源方月对账单
            Map<String, Map<Long, List<FinanceAccountBillCheckDto>>> financeChecs = financeAccountBillCheckService
                    .resourceAccountBillCheckByDaily(accountDate, userAccount.getAccountId() + "");
            if (MapUtils.isNotEmpty(financeChecs)) {
                for (Map.Entry<String, Map<Long, List<FinanceAccountBillCheckDto>>> enty : financeChecs.entrySet()) {
                    Map<Long, List<FinanceAccountBillCheckDto>> mapRes = enty.getValue();
                    for (Map.Entry<Long, List<FinanceAccountBillCheckDto>> entyRes : mapRes.entrySet()) {
                        for (FinanceAccountBillCheckDto dto : entyRes.getValue()) {
                            List<Object> data = new ArrayList<Object>();
                            data.add(dto.getAppName());
                            data.add(dto.getResourcePostion());
                            data.add(userAccount.getAccountEmail());
                            data.add(userAccount.getCompany());
                            data.add(dto.getDate());
                            data.add(dto.getDisplayCount());
                            data.add(dto.getClickCount());
                            data.add(dto.getCtr());
                            data.add(dto.getCpmDisplayCount());
                            data.add(dto.getCpmAmount());
                            data.add(dto.getCpcClickCount());
                            data.add(dto.getCpcAmount());
                            data.add(dto.getOccupyAmount());
                            data.add(dto.getAmount());
                            dataList.add(data);
                        }
                    }
                }
            } else {
                log.info("资源方月账单数据返回空!");
                return new RestResult(0, "资源方月账单数据返回空");
            }
        }

        String fileName = "";
        if (checkParty == 1) {// 调需求方detail
            fileName = String.format("【%s年%s月】%s客户对账单.xls", DateUtil.getYear(accountDate), DateUtil.getMonth(accountDate),
                    userAccount.getAccountEmail());
            operationLogService.addOperationLog(OperationLogPage.DEMAND_BILLS, OperationLogOperation.EXPORT,
                    account.getAccountEmail(), OperationLogPage.EXPORT_DEMAND_BILLS, userAccount.getAccountEmail(), fileName);
        } else {
            fileName = String.format("【%s年%s月】%s资源对账单.xls", DateUtil.getYear(accountDate), DateUtil.getMonth(accountDate),
                    userAccount.getAccountEmail());
            operationLogService.addOperationLog(OperationLogPage.RESOURCE_BILLS, OperationLogOperation.EXPORT,
                    account.getAccountEmail(), OperationLogPage.EXPORT_RESOURCE_BILLS, userAccount.getAccountEmail(), fileName);
        }

        try {
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            EXCELUtils.exportExcel(head, dataList, response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            log.error(e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, e.getMessage());
        }

        return new RestResult();
    }

    /**
     * 第三方dsp对账单汇总
     * 
     * @return
     */
    @RequestMapping(value = "/dsp/billCheck/summary", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult getDspPromoteBillCheckSummary(@RequestParam Short dsp, @RequestParam String beginDate,
                                                    @RequestParam String endDate) {
        // 转换时间
        DateTime beginDateTime = new DateTime(beginDate);
        DateTime endDateTime = new DateTime(endDate);
        // 校验日期
        AdAsserts.isTrue(!new DateTime(beginDate).isAfterNow(), "开始时间不能晚于当前时间");
        AdAsserts.isTrue(!beginDateTime.isAfter(endDateTime), "开始时间不能晚于结束时间");

        //如果结束时间在当前时间后面，直接将当前时间作为结束时间条件
        endDateTime = endDateTime.isAfterNow() ? DateTime.now() : endDateTime;

        Date _beginDate = com.wanda.ffanad.common.utils.DateUtil.getDayBegin(beginDateTime.toDate());
        Date _endDate = com.wanda.ffanad.common.utils.DateUtil.getDayBegin(endDateTime.toDate());

        return new RestResult(financeAccountBillCheckService.getDspBillCheckSummary(dsp, _beginDate, _endDate));
    }

    /**
     * 第三方dsp对账单分页明细
     * 
     * @return
     */
    @RequestMapping(value = "/dsp/billCheck/page", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult getDspPromoteBillCheckPage(@RequestParam Short dsp, @RequestParam String beginDate,
                                                 @RequestParam String endDate, @RequestParam(defaultValue = "1") Integer pageNum,
                                                 @RequestParam(defaultValue = "20") Integer pageSize) {
        // 转换时间
        DateTime beginDateTime = new DateTime(beginDate);
        DateTime endDateTime = new DateTime(endDate);
        // 校验日期
        AdAsserts.isTrue(!new DateTime(beginDate).isAfterNow(), "开始时间不能晚于当前时间");
        AdAsserts.isTrue(!beginDateTime.isAfter(endDateTime), "开始时间不能晚于结束时间");

        //如果结束时间在当前时间后面，直接将当前时间作为结束时间条件
        endDateTime = endDateTime.isAfterNow() ? DateTime.now() : endDateTime;

        Date _beginDate = com.wanda.ffanad.common.utils.DateUtil.getDayBegin(beginDateTime.toDate());
        Date _endDate = com.wanda.ffanad.common.utils.DateUtil.getDayBegin(endDateTime.plusDays(1).toDate());

        return new RestResult(financeAccountBillCheckService.getDspBillCheckPage(dsp, _beginDate, _endDate, pageNum, pageSize));
    }

    /**
     * 第三方dsp对账单导出
     * 
     * @return
     */
    @RequestMapping(value = "/dsp/billCheck/export", method = { RequestMethod.GET, RequestMethod.POST })
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF, SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public void getDspPromoteBillCheckExport(@RequestParam Short dsp, @RequestParam String beginDate,
                                             @RequestParam String endDate, HttpSession session, HttpServletResponse response) {

        //        AccountEntity account = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);

        // 转换时间
        DateTime beginDateTime = new DateTime(beginDate);
        DateTime endDateTime = new DateTime(endDate);
        // 校验日期
        AdAsserts.isTrue(!new DateTime(beginDate).isAfterNow(), "开始时间不能晚于当前时间");
        AdAsserts.isTrue(!beginDateTime.isAfter(endDateTime), "开始时间不能晚于结束时间");

        //如果结束时间在当前时间后面，直接将当前时间作为结束时间条件
        endDateTime = endDateTime.isAfterNow() ? DateTime.now() : endDateTime;

        Date _beginDate = com.wanda.ffanad.common.utils.DateUtil.getDayBegin(beginDateTime.toDate());
        Date _endDate = com.wanda.ffanad.common.utils.DateUtil.getDayBegin(endDateTime.plusDays(1).toDate());

        DspBillCheckSummaryRespDto dspBillCheckSummaryRespDto = financeAccountBillCheckService.getDspBillCheckSummary(dsp,
                _beginDate, _endDate);
        List<DspBillCheckDetailRespDto> lisDetail = financeAccountBillCheckService.getDspBillCheckList(dsp, _beginDate, _endDate);

        List<Object> headSummary = new ArrayList<>();
        headSummary.add("投放时段");
        headSummary.add("请求数(次)");
        headSummary.add("有效请求数(次)");
        headSummary.add("展示量(次)");
        headSummary.add("点击量(次)");
        headSummary.add("消耗金额(元)");
        headSummary.add("当前余额(元)");
        List<Object> headDetail = new ArrayList<>();
        headDetail.add("投放ID");
        headDetail.add("投放时间");
        headDetail.add("广告位");
        headDetail.add("投放终端");
        headDetail.add("投放城市");
        headDetail.add("展示量(次)");
        headDetail.add("点击量(次)");
        headDetail.add("计费方式");
        headDetail.add("计费单价(元)");
        headDetail.add("消耗金额(元)");
        headDetail.add("余额(元)");

        List<List<Object>> lisSummaryData = new ArrayList<>();
        List<List<Object>> lisDetailData = new ArrayList<>();
        if (dspBillCheckSummaryRespDto != null) {
            List<Object> rowData = new ArrayList<>();
            rowData.add(dspBillCheckSummaryRespDto.getDateRange());
            rowData.add(dspBillCheckSummaryRespDto.getRequestCount());
            rowData.add(dspBillCheckSummaryRespDto.getEffectiveRequestCount());
            rowData.add(dspBillCheckSummaryRespDto.getDisplayCount());
            rowData.add(dspBillCheckSummaryRespDto.getClickCount());
            rowData.add(dspBillCheckSummaryRespDto.getAmountStr());
            rowData.add("-");
            lisSummaryData.add(rowData);
        }

        if (!org.springframework.util.CollectionUtils.isEmpty(lisDetail)) {
            for (DspBillCheckDetailRespDto dspBillCheckDetailRespDto : lisDetail) {
                List<Object> rowData = new ArrayList<>();
                rowData.add(dspBillCheckDetailRespDto.getPromoteId());
                rowData.add(dspBillCheckDetailRespDto.getCreateTime());
                rowData.add(dspBillCheckDetailRespDto.getResName());
                rowData.add(dspBillCheckDetailRespDto.getOsStr());
                rowData.add(dspBillCheckDetailRespDto.getCityStr());
                rowData.add(dspBillCheckDetailRespDto.getDisplayCount());
                rowData.add(dspBillCheckDetailRespDto.getClickCount());
                rowData.add(dspBillCheckDetailRespDto.getSettleTypeStr());
                rowData.add(dspBillCheckDetailRespDto.getAmountStr());
                rowData.add(dspBillCheckDetailRespDto.getAmountStr());
                rowData.add("-");
                lisDetailData.add(rowData);
            }
        }

        List<String> sheetNames = new ArrayList<>();
        List<List<List<Object>>> filedData = new ArrayList<>();
        List<List<Object>> filedName = new ArrayList<>();
        String summarySheetName = "汇总表";
        String detailSheetName = "明细表,共" + lisDetailData.size() + "条记录";
        sheetNames.add(summarySheetName);
        sheetNames.add(detailSheetName);
        filedName.add(headSummary);
        filedName.add(headDetail);
        filedData.add(lisSummaryData);
        filedData.add(lisDetailData);

        String fileName = String.format("%s%s对账单.xls", DspEnum.getDescByValue(dsp) + "DSP",
                dspBillCheckSummaryRespDto.getDateRange());
        //             operationLogService.addOperationLog(OperationLogPage.DEMAND_BILLS, OperationLogOperation.EXPORT, account.getAccountEmail(),
        //                     OperationLogPage.EXPORT_DEMAND_BILLS, account.getAccountEmail(), fileName);

        try {
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            EXCELUtils.exportWithSheet(sheetNames, filedName, filedData, response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
