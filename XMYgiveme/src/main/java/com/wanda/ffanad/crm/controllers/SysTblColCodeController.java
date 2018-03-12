package com.wanda.ffanad.crm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.common.utils.JsonUtils;
import com.wanda.ffanad.core.domains.SysTblColCode;
import com.wanda.ffanad.core.services.system.SysTblColCodeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 广告投放计划投放地域控制类
 * 
 * created by songweiliang on 2016/5/19.
 *
 */
@Api(tags = { "系统字段数据" })
@RestController
@RequestMapping(value = "/sysTblColCode")
public class SysTblColCodeController{

	@Autowired
	private SysTblColCodeService sysTblColCodeService;
	
	@ApiOperation(value = "查询字典数据")
	@RequestMapping(value = "/selectByCode", method = RequestMethod.GET)
	public Object selectByCode(String pKey) {
		List<SysTblColCode> codes =sysTblColCodeService.getParams(pKey);
		return JsonUtils.toJSON(codes);
		//return new RestResult(FfanadStatus.S_OK, codes);
	}
}
