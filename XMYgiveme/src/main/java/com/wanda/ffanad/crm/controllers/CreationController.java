package com.wanda.ffanad.crm.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.wanda.ffanad.crm.service.CreationCrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.common.ResultBo;
import com.wanda.ffanad.common.constants.Constant;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.domains.vo.req.CreationReqVo;
import com.wanda.ffanad.core.domains.vo.req.CreationStatusReqVo;
import com.wanda.ffanad.core.services.CreationService;
import com.wanda.ffanad.crm.service.LandingPageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 创意审核相关服务的控制器.
 */
@Api(tags = { "创意审核相关接口" })
@RestController
@RequestMapping("/creation")
public class CreationController {
    /**
     * 自动注入{@link com.wanda.ffanad.core.services.CreationService}的实例
     */
    @Autowired
    private CreationService    crService;

    @Autowired
    private CreationCrmService creationCrmService;

    @Autowired
    private LandingPageService landingPageService;

    /**
     * 按页查询符合条件创意请求。 调用
     * {@link com.wanda.ffanad.core.services.CreationService#getCreationsByPage(CreationReqVo resReqVo, Integer pageIndex, Integer pageSize)}
     * 
     * @param crReqVo 查询条件
     * @param pageIndex 页码
     * @param pageSize 页大小
     * @return 返回符合条件的创意
     */
    @ApiOperation(value = "按页查询符合条件的创意")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getCreationsByPage(@Validated @RequestBody CreationReqVo crReqVo,
                                         @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
                                         @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        return new RestResult(crService.getCreationsByPage(crReqVo, pageIndex, pageSize));
    }

    /**
     * 获取创意的详细信息。 调用
     * {@link com.wanda.ffanad.core.services.CreationService#getCreationById(Integer)}
     * 
     * @param creationId 创意id
     * @return 返回创意详细信息
     */
    @ApiOperation(value = "查询指定创意信息")
    @RequestMapping(value = "/{creationId}", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getCreationsById(@PathVariable Integer creationId) {
        return new RestResult(creationCrmService.getCreationById(creationId));
    }

    /**
     * 创意审核状态更新。调用｛
     * {@link com.wanda.ffanad.core.services.CreationService#updateCreationAuditResult(CreationStatusReqVo,HttpSession)}
     * 
     * @param crAuditResult 前台传入的数据
     * @return 返回更新的创意审核状态信息
     */
    @ApiOperation(value = "更新创意的审核信息")
    @RequestMapping(value = "/auditresult", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult updateCreationAuditResult(@Validated @RequestBody CreationStatusReqVo crAuditResult, HttpSession session) {
        return new RestResult(crService.updateCreationAuditResult(crAuditResult, session));
    }

    /**
     * 获取h5着陆页详情信息
     */
    @RequestMapping(value = "/getH5LandingPage", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult getH5LandingPage(@Valid @NotNull Integer id) {
        RestResult rest = new RestResult();
        rest.setResult(landingPageService.getH5LandingPageDetail(id));
        return rest;
    }

    /**
     * 审核h5着陆页
     */
    @RequestMapping(value = "/auditH5LandingPage", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult auditH5LandingPage(@RequestBody CreationStatusReqVo crAuditResult, HttpSession session) {
        RestResult rest = new RestResult();
        String accountEmail = (String) session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL);
        ResultBo rb = landingPageService.auditH5LandingPage(crAuditResult, accountEmail);
        if (rb.getStatus() == Constant.ResultStatus.RESULT_STATUS_SUCCESS) {
            rest.setMessage("更新成功");
        } else {
            rest.setStatus(500);
            rest.setMessage(rb.getMessage());
        }
        return rest;
    }

    /**
     * 获取h5着陆页并缓存至redis
     */
    @RequestMapping(value = "/auditH5LandingPage/{pageMd5}")
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_ALL })
    public String getH5LandingPageView(@PathVariable @NotNull String pageMd5) {

        return landingPageService.getH5LandingPageView(pageMd5);
    }

    /**
     * refush h5landingPage redis cache
     */
    @RequestMapping(value = "/refushLadingPageCache")
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_ADMIN })
    public String refushLadingPageCache(HttpSession session) {
        String accountEmail = (String) session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL);
        landingPageService.refushLadingPageCache(accountEmail);
        return "refush cache success";
    }

}
