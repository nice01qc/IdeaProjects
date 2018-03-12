/**
 * <p>Copyright &copy; 2016 大连万达集图所有。</p>
 */
package com.wanda.ffanad.crm.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.TFfadPromoteEntity;
import com.wanda.ffanad.base.message.CacheEventDispatcher;
import com.wanda.ffanad.common.utils.JsonUtils;
import com.wanda.ffanad.core.common.FfanadStatus;
import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.domains.SysTblColCode;
import com.wanda.ffanad.core.domains.ext.TFfadPromoteExt;
import com.wanda.ffanad.core.req.dto.TFfadPromoteReqDto;
import com.wanda.ffanad.core.services.promote.TFfadPromoteService;
import com.wanda.ffanad.core.services.system.SysTblColCodeService;
import com.wanda.ffanad.crm.dto.resp.ProgramDetailDto;
import com.wanda.ffanad.crm.service.ProgramService;

/**
 * <b>Application name:</b>万达广告资源管理-CRM后台系统 <br>
 * <b>Application describing:CRM系统投放管理Controller</b> <br>
 * <b>Copyright:</b>Copyright &copy; 2016 大连万达集图所有。 <br>
 * <b>Date:</b>2016年5月18日<br>
 *
 * @author 韩鹏
 * @version $Revision: 1.0 $
 */
@RestController
@RequestMapping("/promote")
public class TFfadPromoteController {

    private Logger               logger = LoggerFactory.getLogger(TFfadPromoteController.class);

    @Autowired
    private TFfadPromoteService  promoteService;
    
    @Autowired
    private ProgramService  crmProgramService;

    @Autowired
    private SysTblColCodeService sysTblColCodeService;
    @Autowired
    private CacheEventDispatcher cacheEventDispatcher;

    /**
     * 查看投放详情
     *
     * @author 韩鹏 2016年5月18日
     * @param promoteInfo
     * @return TFfadPromote
     */
    @RequestMapping(path = "/info", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public ProgramDetailDto selectPromoteById(@RequestBody(required = false) TFfadPromoteEntity promoteInfo) {

        logger.info("POST----查看投放详情 " + "投放ID为: " + promoteInfo.getPromoteId());

        ProgramDetailDto programDetailDto = crmProgramService.queryProgramDetail(promoteInfo.getPromoteId());

        return programDetailDto;
    }

    /**
     * 查询代码表
     *
     * @author 韩鹏 2016年5月20日
     * @param pKey
     * @return
     */
    @RequestMapping(value = "/selectByCode", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public Object selectByCode(String pKey) {
        List<SysTblColCode> codes = sysTblColCodeService.getParams(pKey);
        return JsonUtils.toJSON(codes);
    }

    /**
     * 获取投放管理列表
     *
     * @author wangyu
     * @return
     */
    @RequestMapping(path = "/list", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public PaginationBo<TFfadPromoteExt> list(@RequestParam(required = false, defaultValue = "0") int pageNo,
                                           @RequestParam(required = false, defaultValue = "20") int pageSize, TFfadPromoteReqDto record,
                                           final String startDate) {
        logger.info("收到GET请求:list");
        PaginationBo<TFfadPromoteExt> pbo = new PaginationBo<TFfadPromoteExt>();
        List<TFfadPromoteExt> list = promoteService.getTFfadPromotePage(record, pageNo, pageSize);
        pbo.setRows(list);
        pbo.setTotal(promoteService.getTFfadPromotePageTotal(record));
        return pbo;
    }

    /**
     * 更新投放管理上下架状态
     *
     * @author wangyu
     * @param app
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    public RestResult update(TFfadPromoteEntity record, HttpSession session) {
        String email = session.getAttribute(SystemConstant.SESSION_ACCOUNT_EMAIL).toString();
        int onShelf = 0;
        if (record.getOnShelf() == onShelf) {
            onShelf = 1;
        }
        try {
            promoteService.updatePromoteOnShelf(record.getPromoteId(), onShelf, email, record.getPromoteName());
            cacheEventDispatcher.dispatchCpdPromote(record.getPromoteId());
        }

        catch (Exception e) {
            return new RestResult(FfanadStatus.S_UNSUPPORTED, e.getMessage());
        }
        return new RestResult(FfanadStatus.S_OK, null);
    }
}
