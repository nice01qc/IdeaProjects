package com.wanda.ffanad.crm.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.domains.OperationLog;
import com.wanda.ffanad.core.services.OperationLogService;

/**
 * 操作日志查询
 * 
 * @author xuyao16
 * @date 2016年7月4日下午2:21:33
 */
@RestController
@RequestMapping("/api/v1/operationLog")
public class OperationController {
    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping(method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_ALL })
    public Map<String, Object> getOperationLogs(OperationLog operationLog,
                                                @RequestParam(required = false, defaultValue = "0") int offset,
                                                @RequestParam(required = false, defaultValue = "10") int limit,
                                                HttpSession session) {
        AccountEntity curAccount = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        String role = curAccount.getUserRoles();
        if (!role.equals("1")) {
            operationLog.setLogUserEmail(curAccount.getAccountEmail());
        }
        List<OperationLog> rows = operationLogService.getOperationLogPage(operationLog, offset, limit);
        int total = operationLogService.getOperationLogTotal(operationLog);
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("rows", rows);
        resMap.put("total", total);
        return resMap;
    }

}
