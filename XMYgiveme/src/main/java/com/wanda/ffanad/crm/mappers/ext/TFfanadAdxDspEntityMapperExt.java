/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.mappers.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wanda.ffanad.base.dal.entities.TFfanadAdxDspEntity;
import com.wanda.ffanad.crm.dto.req.AdxDspDetailReqVo;
import com.wanda.ffanad.crm.dto.req.AdxDspPageReqVo;
import com.wanda.ffanad.crm.dto.req.AdxDspStatusReqVo;
import com.wanda.ffanad.crm.dto.resp.AdxDspDetailResVo;
import com.wanda.ffanad.crm.dto.resp.AdxDspListResVo;

/**
 * 类AdxDspManagerEntityMapper.java的实现描述：adx dsp管理mapper
 * 
 * @author liuzhenkai1 2016年10月20日 下午2:29:00
 */
public interface TFfanadAdxDspEntityMapperExt {

    /**
     * 查询这个dsp名称是否重复
     * 
     * @param name
     * @return
     */
    int getDspNameCount(String name);

    /**
     * 保存dsp
     * 
     * @param tad
     */
    int insertAdxDsp(@Param("tad") TFfanadAdxDspEntity tad);

    /**
     * 查询总数
     * 
     * @param adpv
     * @return
     */
    int getTotalCountDspPage(@Param("adpv") AdxDspPageReqVo adpv);

    /**
     * 获取pageList
     * 
     * @param adpv
     * @return
     */
    List<AdxDspListResVo> getDspPageList(@Param("adpv") AdxDspPageReqVo adpv);

    /**
     * 根据上传的参数，查询符合条件的待更新数据数量
     * 
     * @param addv
     * @return
     */
    int getModifyDspCount(@Param("adpv") AdxDspDetailReqVo addv);

    /**
     * 更新dsp
     * 
     * @param tad
     */
    int modifyDsp(@Param("tad") TFfanadAdxDspEntity tad);

    /**
     * 停用dsp
     * 
     * @param tad
     * @return
     */
    int updateDspStatus(@Param("tad") TFfanadAdxDspEntity tad);

    /**
     * 获取所有的dsp
     * 
     * @return
     */
    List<String> getAllDsp();

    /**
     * 更新dsptoken
     * 
     * @param tad
     * @return
     */
    int updateDspToken(@Param("tad") TFfanadAdxDspEntity tad);

    /**
     * 根据主键ID获取dsp
     * 
     * @param adsv
     * @return
     */
    AdxDspDetailResVo getDspById(@Param("adsv") AdxDspStatusReqVo adsv);

}
