/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.mappers.ext;

import java.util.List;

import com.wanda.ffanad.base.dal.entities.TFfanadAdxCreationDetectUrlEntity;

/**
 * 类AdxDspManagerEntityMapper.java的实现描述：创意监控子表
 * 
 * @author liuzhenkai1 2016年10月20日 下午2:29:00
 */
public interface TFfanadAdxCreationDetectUrlEntityMapperExt {

    /**
     * 根据创意审核主表的ID获取监控链接List
     * 
     * @param id
     * @return
     */
    List<TFfanadAdxCreationDetectUrlEntity> getDetectUrlByCreationId(String id);

}
