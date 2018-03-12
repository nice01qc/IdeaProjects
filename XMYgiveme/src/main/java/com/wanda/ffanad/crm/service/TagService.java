package com.wanda.ffanad.crm.service;

import com.wanda.ffanad.crm.integration.userprofile.resp.TagDto;

/**
 * 类TagService.java的实现描述：标签接口服务类
 * 
 * @author Yao 2017年4月20日 上午9:52:42
 */
public interface TagService {
    /**
     * 通过标签机器名查询标签信息
     * 
     * @param name
     * @return
     */
    public TagDto queryTagByName(String name) ;
}
