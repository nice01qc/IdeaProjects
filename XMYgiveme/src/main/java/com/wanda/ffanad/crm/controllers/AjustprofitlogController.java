/**
 * <p>Copyright &copy; 2016 大连万达集团所有。</p>
 */
package com.wanda.ffanad.crm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.crm.integration.account.req.AjustProfitLogReqDto;
import com.wanda.ffanad.crm.integration.account.resp.AjustProfitLogRespDto;
import com.wanda.ffanad.crm.integration.account.service.AjustprofitlogService;

/**
 * <b>Application name:</b>万达广告系统<br>
 * <b>Application describing:</b> {类功能中文描述} <br>
 * <b>Copyright:</b>Copyright &copy; 2016 大连万达集团所有。<br>
 * <b>Date:</b>2016年6月6日<br>
 * 
 * @author 姜涛
 * @version $Revision: 2.0 $
 */
@RestController
@RequestMapping(value = "/ajustprofitlog")
public class AjustprofitlogController {

    
    @Autowired
    private AjustprofitlogService ajustprofitlogService1;

    /**
     * 
     * 分成调整日志分页列表
     * @param ajustprofitlogBo
     * @return
     * @author: 姜涛
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    public PaginationBo<AjustProfitLogRespDto> list(AjustProfitLogReqDto req, int pageNo, int pageSize) {
        return ajustprofitlogService1.list(req, pageNo, pageSize);
    }
}
