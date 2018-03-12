package com.wanda.ffanad.crm.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.dal.entities.AjustProfitLogEntity;
import com.wanda.ffanad.common.redis.RedisUtil;
import com.wanda.ffanad.common.utils.MD5EncodeUtils;
import com.wanda.ffanad.common.utils.MoneyUtil;
import com.wanda.ffanad.base.error.FfanadException;
import com.wanda.ffanad.core.common.FfanadStatus;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.common.redis.RedisCacheConstants;
import com.wanda.ffanad.core.common.type.FreezeState;
import com.wanda.ffanad.core.common.type.UserType;
import com.wanda.ffanad.core.constants.OperationLogAction;
import com.wanda.ffanad.core.constants.OperationLogOperation;
import com.wanda.ffanad.core.constants.OperationLogPage;
import com.wanda.ffanad.core.domains.Crmaccount;
import com.wanda.ffanad.core.domains.CrmaccountDto;
import com.wanda.ffanad.core.domains.SecMenuPo;
import com.wanda.ffanad.core.services.AccountService;
import com.wanda.ffanad.core.services.OperationLogService;
import com.wanda.ffanad.core.services.security.AccountBo;
import com.wanda.ffanad.core.services.security.SecPermissionService;
import com.wanda.ffanad.crm.integration.account.service.AjustprofitlogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "账户相关接口" })
@RestController
@RequestMapping("/account")
public class AccountController {

    private Logger                logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService        accountService;

    @Autowired
    private AjustprofitlogService ajustprofitlogService;

    @Autowired
    private SecPermissionService  permissionService;
    @Autowired
    private OperationLogService   operationLogService;

    /**
     * 资源管理系统CRM后台:新建用户
     *
     * @param account
     * @return
     * @author:韩雪梅 Created on 2016年4月28日 下午1:49:30
     */
    @ApiOperation(value = "创建账户")
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    @RequestMapping(path = "/addcrmemail", method = RequestMethod.POST)
    public synchronized int insertCrm(@RequestBody Crmaccount account, HttpSession session) {
        logger.debug("insertCrm-------------------begin: ");
        int sum = 0;
        AccountEntity checkAccount = new AccountEntity();
        checkAccount.setAccountEmail(account.getAccountEmail());
        checkAccount.setUserType(account.getUserType());
        checkAccount.setUserRoles(account.getUserRoles());
        AccountEntity resultAccount = accountService.findBySelective(checkAccount);
        if(resultAccount!=null){
            logger.info("账户已经存在");
            return sum;
        }
       
        try {
            account.setPassword(MD5EncodeUtils.encode(account.getPassword()).toLowerCase());
            sum = this.accountService.insert(account);
            logger.debug("insertCrm------------------: " + sum);
            if (account.getProfitShare() > Double.valueOf(0)) {
                //根据账户信息查询账户ID
                AccountEntity selectedAccount = new AccountEntity();
                selectedAccount.setAccountEmail(account.getAccountEmail());
                selectedAccount.setUserType(account.getUserType());
                selectedAccount.setUserRoles(account.getUserRoles());
                AccountEntity insertedAccount = accountService.findBySelective(selectedAccount);

                AjustProfitLogEntity log = new AjustProfitLogEntity();
                log.setAccountId(insertedAccount.getAccountId()); //账户ID赋值
                log.setOriginalShare(new BigDecimal(account.getProfitShare()));
                log.setModifyShare(new BigDecimal(account.getProfitShare()));
                log.setModifyTime(new Date()); //修改时间
                log.setAdminId((Integer) session.getAttribute(SystemConstant.SESSION_ACCOUNT_ID));//TODO 需要验证
                ajustprofitlogService.updateProfitShare(log);
                logger.debug("insertCrm------------------: ");
            }
            // 即使日志失败了,保证创建用户成功
            try {
                String str = "";
                if (account.getUserType().equals(UserType.CRM.getValue())) {
                    str = OperationLogAction.NEW_CRM_USER;
                } else if (account.getUserType().equals(UserType.DSP.getValue())) {
                    str = OperationLogAction.NEW_DSP_USER;
                } else if (account.getUserType().equals(UserType.SSP.getValue())) {
                    str = OperationLogAction.NEW_SSP_USER;
                }else if(account.getUserType().equals(UserType.DMP.getValue())){
                    str = OperationLogAction.NEW_DMP_USER;
                }
                operationLogService.addOperationLog(OperationLogPage.NEW_USER, OperationLogOperation.OPEN,
                        session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL).toString(), str, account.getAccountEmail(),
                        "");
            } catch (Exception e) {
            }

        } catch (Exception e) {
            logger.error("insertCrm:   ", e);
            e.printStackTrace();
        }
        logger.debug("insertCrm-------------------end: ");
        return sum;
    }

    /**
     * CRM登录
     * 
     * @author 姜涛
     * @param account
     * @param session
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public RestResult login(@RequestBody AccountEntity account, HttpSession session) {
        try {
            AccountEntity loginAccount = this.accountService.doLogin(account);
            session.setAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT, loginAccount);
            session.setAttribute(SystemConstant.SESSION_ACCOUNT_ID, loginAccount.getAccountId());
            session.setAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL, loginAccount.getAccountEmail());
            session.setAttribute(SystemConstant.SESSION_ACCOUNT_USERNAME, loginAccount.getUsername());
            session.setAttribute(SystemConstant.SESSION_ACCOUNT_PHONE, loginAccount.getPhone());
            List<SecMenuPo> pList = permissionService.getMenuTree(loginAccount.getUserType() + "#" + loginAccount.getUserRoles());
            session.setAttribute(SystemConstant.SESSION_ACCOUNT_MENUS, pList);
            SecMenuPo firstMenu = pList.get(0);
            String url = firstMenu.getUrl();
            if (!firstMenu.getChildren().isEmpty()) {
                url = firstMenu.getChildren().get(0).getUrl();
            }
            return new RestResult(FfanadStatus.S_OK, url);
        } catch (FfanadException e) {
            return new RestResult(e.getStatus(), e.getMessage());
        }

    }

    /**
     * 资源管理系统CRM后台:验证用户
     *
     * @param account
     * @return
     * @author:韩雪梅 Created on 2016年4月26日 上午10:49:30
     */
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    @RequestMapping(path = "/email", method = RequestMethod.POST)
    public int isEmail(@RequestBody Crmaccount account) {
        logger.debug("isEmail-------------------begin: ");
        int sum = 0;
        try {
            sum = this.accountService.isUser(account);
            logger.debug("isEmail------------------: " + sum);
        } catch (Exception e) {
            logger.error("isEmail:   ", e);
            e.printStackTrace();
        }
        logger.debug("isEmail-------------------end: ");
        return sum;
    }

    /**
     * 资源管理系统CRM后台:验证用户
     *
     * @param account
     * @return
     * @author:韩雪梅 Created on 2016年4月26日 上午10:49:30
     */
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    @RequestMapping(path = "/dsemail", method = RequestMethod.POST)
    public int isDSEmail(@RequestBody Crmaccount account) {
        logger.debug("isDSEmail-------------------begin: ");
        int sum = 0;
        try {
            sum = this.accountService.isEmail(account);
            logger.debug("isDSEmail------------------: " + sum);
        } catch (Exception e) {
            logger.error("isDSEmail:   ", e);
            e.printStackTrace();
        }
        logger.debug("isDSEmail-------------------end: ");
        return sum;
    }

    /**
     * 资源管理系统CRM后台:查询用户
     *
     * @param account
     * @return
     * @author:韩雪梅 Created on 2016年4月29日 上午10:49:30
     */
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    @RequestMapping(path = "/searchcrm", method = RequestMethod.POST)
    public List<CrmaccountDto> searchcrm(@RequestBody Crmaccount account) {
        logger.debug("searchcrm-------------------begin: ");
        List<CrmaccountDto> list = null;
        try {
            list = this.accountService.selectByAccount(account);
            System.out.println(account.getFrontCreateTime() + "  *******  " + account.getBackCreateTime());
            logger.debug("searchcrm------------------: " + list);
        } catch (Exception e) {
            logger.error("searchcrm:   ", e);
            e.printStackTrace();
        }
        logger.debug("searchcrm-------------------end: ");
        return list;
    }

    /**
     * 资源管理系统CRM后台:查询用户
     *
     * @param account
     * @return
     * @author:韩雪梅 Created on 2016年5月3日 上午10:49:30
     */
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    @RequestMapping(path = "/updateStateById", method = RequestMethod.POST)
    public int updateStateById(@RequestBody Crmaccount account) {
        logger.debug("updateStateById-------------------begin: ");
        int sum = 0;
        try {
            sum = this.accountService.updateStateById(account);
            logger.debug("updateStateById------------------: " + sum);
        } catch (Exception e) {
            logger.error("updateStateById:   ", e);
            e.printStackTrace();
        }
        logger.debug("updateStateById-------------------end: ");
        return sum;
    }

    /**
     * 资源管理系统CRM后台:查询用户
     *
     * @param account
     * @return
     * @author:韩雪梅 Created on 2016年5月3日 上午10:49:30
     */
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    @RequestMapping(path = "/updatePasswordById", method = RequestMethod.POST)
    public int updatePasswordById(@RequestBody Crmaccount account) {
        logger.debug("updatePasswordById-------------------begin: ");
        int sum = 0;
        try {
            sum = this.accountService.updatePasswordById(account);
            logger.debug("updatePasswordById------------------: " + sum);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("updatePasswordById:   ", e);
        }
        logger.debug("updatePasswordById-------------------end: ");
        return sum;
    }

    /**
     * 资源管理系统CRM后台:查询用户
     *
     * @param account
     * @return
     * @author:韩雪梅 Created on 2016年5月4日 上午10:49:30
     */
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    @RequestMapping(path = "/admin", method = RequestMethod.POST)
    public int isAdmin(@RequestBody Crmaccount account) {
        logger.debug("isAdmin-------------------begin: ");
        int sum = 0;
        try {
            sum = this.accountService.isAdmin(account);
            logger.debug("isAdmin------------------: " + sum);
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("isAdmin------------------: " + e);
        }
        logger.debug("isAdmin-------------------end: ");
        return sum;
    }

    /**
     * 登出
     * 
     * @param session
     * @param response
     * @author: 姜涛
     * @throws IOException
     */
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.invalidate();
        response.sendRedirect("/login.html");
    }

    /**
     * 账户名重复性检查
     * 
     * @author 姜涛
     * @param email
     * @param userType
     * @return
     */
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    @RequestMapping(path = "/checkEmail/{email}/{userType}", method = RequestMethod.GET)
    public RestResult checkEmail(@PathVariable String email, @PathVariable String userType) {
        AccountEntity param = new AccountEntity();
        param.setAccountEmail(email);
        param.setUserType(userType);
        AccountEntity result = accountService.findBySelective(param);
        if (null != result) {
            return new RestResult(FfanadStatus.S_DUPLICATE_ACCOUNT_EMAIL, "此邮箱已存在");
        } else {
            return new RestResult(FfanadStatus.S_OK, null);
        }
    }

    /**
     * 用户列表查询
     * 
     * @author 姜涛
     * @param accountBo
     * @return
     */
    @ApiOperation(value = "根据查询条件查询用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN, SystemConstant.ROLE_SYSTEM_CRM_FINANCIAL_STAFF })
    public PaginationBo<AccountBo> list(@RequestBody AccountBo accountBo) {
        return accountService.getListByParam(accountBo);
    }

    /**
     * 重置用户密码
     * 
     * @author 姜涛
     * @param password
     * @param passwordConfirm
     * @return
     */
    @ApiOperation(value = "重置用户密码")
    @RequestMapping(value = "/resetPassword/{accountId}", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    public RestResult resetPassword(@PathVariable Integer accountId, String newPassword, HttpSession session) {
        try {
            AccountBo aBo = accountService.getByAccountId(accountId);
            // 密码重置log
            operationLogService.addOperationLog(OperationLogPage.USER_LIST, OperationLogOperation.RESET_PASSWORD,
                    session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL).toString(), OperationLogAction.RESET_PASSWORD,
                    aBo.getAccountEmail(), "");
        } catch (Exception e) {
        }
        accountService.resetPassword(accountId, newPassword);
        return new RestResult(FfanadStatus.S_OK, null);
    }

    /**
     * 根据主键获取账户信息
     * 
     * @author 姜涛
     * @param accountId
     * @param newPassword
     * @return
     */
    @ApiOperation(value = "根据主键获取账户信息")
    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    public RestResult get(@PathVariable Integer accountId) {
        AccountBo aBo = accountService.getByAccountId(accountId);
        aBo.setPassword(null);
        return new RestResult(FfanadStatus.S_OK, aBo);
    }

    /**
     * 非空值更新账户信息
     * 
     * @author 姜涛
     * @param accountBo
     * @return
     */
    @ApiOperation(value = "非空值更新账户信息")
    @RequestMapping(value = "/{accountId}", method = RequestMethod.PUT)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    public RestResult update(@RequestBody AccountBo accountBo, HttpSession session) {
        try {
            String curAccountId = session.getAttribute(SystemConstant.SESSION_ACCOUNT_ID).toString();
            AccountBo aBo = accountService.getByAccountId(accountBo.getAccountId());
            if (accountBo.getFreezeState() != null && !aBo.getFreezeState().equals(accountBo.getFreezeState())) {
                if (curAccountId.equals(aBo.getAccountId().toString())) {
                    return new RestResult(FfanadStatus.S_ERROR_UNDEFINED, "不可以冻结登录账户");
                }
                String redisKey = RedisCacheConstants.FREEZED_ACCOUNT_KEY_PREFIX + "_" + aBo.getAccountId();
                if (accountBo.getFreezeState().equals(FreezeState.FREEZE.getValue())) {
                    RedisUtil.setString(redisKey, aBo.getAccountEmail());
                    // 冻结账户log
                    operationLogService.addOperationLog(OperationLogPage.USER_LIST, OperationLogOperation.FROZEN,
                            session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL).toString(), OperationLogAction.FROZEN_USER,
                            aBo.getAccountEmail(), "");
                } else {
                    RedisUtil.del(redisKey);
                }
            } else if (!aBo.getUsername().equals(accountBo.getUsername()) || !aBo.getPhone().equals(accountBo.getPhone())
                    || !aBo.getCompany().equals(accountBo.getCompany())) {
                // 编辑信息log
                operationLogService.addOperationLog(OperationLogPage.USER_LIST, OperationLogOperation.EDIT_INFO,
                        session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL).toString(), OperationLogAction.EDIT_INFO,
                        aBo.getAccountEmail(), "");
            }
        } catch (Exception e) {
        }
        accountService.updateBySelective(accountBo);
        return new RestResult(FfanadStatus.S_OK, null);
    }

    /**
     * 调整账户分成信息
     * 
     * @author 姜涛
     * @param accountBo
     * @return
     */
    @ApiOperation(value = "调整账户分成信息")
    @RequestMapping(value = "/adjustProfitShare/{accountId}", method = RequestMethod.PUT)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    public RestResult adjustProfitShare(@PathVariable Integer accountId,
                                        @RequestParam(value = "newProfitshare", required = true) Double newProfitshare,
                                        HttpSession session) {
        AjustProfitLogEntity ajustProfitLogEntity = new AjustProfitLogEntity();
        ajustProfitLogEntity.setAccountId(accountId);
        ajustProfitLogEntity.setModifyShare(new BigDecimal(newProfitshare));
        ajustProfitLogEntity.setAdminId((Integer) (session.getAttribute(SystemConstant.SESSION_ACCOUNT_ID)));
        try {
            // 调整分成log
            AccountBo aBo = accountService.getByAccountId(accountId);
            operationLogService.addOperationLog(OperationLogPage.USER_LIST, OperationLogOperation.ADJUST_PROFIT,
                    session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL).toString(), OperationLogAction.ADJUST_PROFIT,
                    aBo.getAccountEmail(),
                    "原分成为【" + MoneyUtil.formatToSepara(aBo.getProfitShare().multiply(new BigDecimal(100)), "#,##0.00") + "】，调整为【"
                            + MoneyUtil.formatToSepara(newProfitshare * 100, "#,##0.00") + "】");
        } catch (Exception e) {
        }
        ajustprofitlogService.updateProfitShare(ajustProfitLogEntity);
        return new RestResult(FfanadStatus.S_OK, null);
    }
}
