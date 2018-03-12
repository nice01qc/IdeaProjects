/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.controllers;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanda.ffanad.base.annotation.PermitAll;
import com.wanda.ffanad.crm.service.LandingPageService;

/**
 * 类ViewLandingPageController.java的实现描述：TODO 类实现描述
 * 
 * @author liuzhenkai1 2016年12月7日 下午4:45:51
 */
@PermitAll
@RestController
@RequestMapping("/viewPage")
public class ViewLandingPageController {

    @Autowired
    private LandingPageService landingPageService;

    /**
     * 模仿Lua脚本访问redis
     */
    @RequestMapping(value = "/landingPage/{pageMd5}")
    public String ViewLandingPage(@PathVariable @NotNull String pageMd5) {

        return landingPageService.viewPageLanding(pageMd5);
    }
}
