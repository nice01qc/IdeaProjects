/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.RolePermission;
import com.wanda.ffanad.base.constants.CommonConfigCatgs;
import com.wanda.ffanad.base.constants.CommonConfigKeys;
import com.wanda.ffanad.base.dal.entities.AccountEntity;
import com.wanda.ffanad.base.dal.entities.CommonConfigurationEntity;
import com.wanda.ffanad.base.dal.manager.CommonConfigurationManager;
import com.wanda.ffanad.core.common.RestResult;
import com.wanda.ffanad.core.common.constant.SystemConstant;

/**
 * 类AdxSettingController.java的实现描述：adx设置
 * 
 * @author xuyao16 2017年02月07日 下午4:59:07
 */
@RestController
@RequestMapping("/adxSetting")
public class AdxSettingController {

	@Autowired
	private CommonConfigurationManager configurationManager;

	/**
	 * 查询配置信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getConfigs", method = RequestMethod.GET)
	@RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
	public RestResult getAdxConfig() {
		Map<String, CommonConfigurationEntity> configs = new HashMap<>();
		CommonConfigurationEntity config = configurationManager.getConfig(CommonConfigCatgs.ADX_SETTING_FIRST_CTAG,
				null, CommonConfigKeys.SAFE_LINK_CHECK_KEY);
		configs.put(CommonConfigKeys.SAFE_LINK_CHECK_KEY, config);
		return new RestResult(configs);
	}

	/**
	 * 保存配置信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/saveConfigs", method = RequestMethod.POST)
	@RolePermission(roles = { SystemConstant.ROLE_SYSTEM_CRM_OP_STAFF })
	public RestResult saveAdxConfig(HttpSession session, String safeLinkCheck) {
		AccountEntity accountEntity = (AccountEntity) session.getAttribute(SystemConstant.SESSION_ACCOUNT_OBJECT);
		CommonConfigurationEntity config = configurationManager.getConfig(CommonConfigCatgs.ADX_SETTING_FIRST_CTAG,
				null, CommonConfigKeys.SAFE_LINK_CHECK_KEY);
		if (config == null) {
			config = new CommonConfigurationEntity();
			config.setCreateBy(accountEntity.getAccountId());
			config.setCreateTime(new Date());
			config.setFirstCatg(CommonConfigCatgs.ADX_SETTING_FIRST_CTAG);
			config.setKey(CommonConfigKeys.SAFE_LINK_CHECK_KEY);
			config.setValue(safeLinkCheck);
			configurationManager.saveConfig(config);
		} else {
			config.setUpdateBy(accountEntity.getAccountId());
			config.setValue(safeLinkCheck);
			config.setUpdateTime(new Date());
			configurationManager.updateConfig(config);
		}
		return new RestResult();
	}
}
