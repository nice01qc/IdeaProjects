package com.wanda.ffanad.crm.controllers;

import java.util.ArrayList;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.error.AdAsserts;
import com.wanda.ffanad.crm.dto.req.DspConfigPageReqDto;
import com.wanda.ffanad.crm.dto.req.DspConfigUpdateReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.wanda.ffanad.core.common.PaginationBo;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;
import com.wanda.ffanad.core.domains.vo.res.ResourceResVo;
import com.wanda.ffanad.core.enums.PromoteTerminalEnum;
import com.wanda.ffanad.core.enums.ResPositionTypeEnum;
import com.wanda.ffanad.crm.service.DspConfigService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * 第三方dsp接入配置 Created by kevin on 16/9/20.
 */
@RestController
@RequestMapping("/dspConfig")
@SessionAttributes("user")
public class DspConfigController {

    @Autowired
    private DspConfigService dspConfigService;

    /**
     * 分页查询第三方dsp接入配置信息
     * 
     * @param dspConfigPageReqDto
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getPage(@Validated @RequestBody DspConfigPageReqDto dspConfigPageReqDto) {
        if (dspConfigPageReqDto != null && dspConfigPageReqDto.getResTerminaltype() == PromoteTerminalEnum.BEACON.getValue()) {
            //如果是Beacon，在这个接口里面只返回beacon的资源位
            if (dspConfigPageReqDto.getResPositiontype() == null
                    || dspConfigPageReqDto.getResPositiontype() == ResPositionTypeEnum.BEACON.getValue()) {
                dspConfigPageReqDto.setResPositiontype(ResPositionTypeEnum.BEACON.getValue());
            } else {
                PaginationBo<ResourceResVo> retObject = new PaginationBo<>();
                retObject.setRows(new ArrayList<ResourceResVo>());
                return new RestResult(retObject);
            }
        }
        return new RestResult(dspConfigService.getAllResourceDspConfigPage(dspConfigPageReqDto,
                dspConfigPageReqDto.getPageNumber(), dspConfigPageReqDto.getPageSize()));
    }

    /**
     * 倒序获取所有的adxDsp
     * 
     * @return
     */
    @RequestMapping(value = "/adxDsp", method = RequestMethod.GET)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult getAllAdxDsp() {
        return new RestResult(dspConfigService.getAllAdxDsp());
    }

    /**
     * 保存配置信息
     *
     * @param dspConfigUpdateReqDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
    @ResponseBody
    public RestResult saveDspConfig(@Valid @RequestBody DspConfigUpdateReqDto dspConfigUpdateReqDto, HttpSession session) {
        AccountEntity account = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
        dspConfigService.saveDspConfig(dspConfigUpdateReqDto, account.getAccountId(), account.getAccountEmail());
        return new RestResult();
    }
}
