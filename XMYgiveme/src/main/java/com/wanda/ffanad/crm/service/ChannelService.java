package com.wanda.ffanad.crm.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.wanda.ffanad.base.common.PageBo;
import com.wanda.ffanad.base.dal.entities.ChannelEntity;
import com.wanda.ffanad.crm.dto.req.ChannelSaveReqDto;

/**
 * 频道管理 Created by kevin on 16/11/17.
 */
public interface ChannelService {

    List<ChannelEntity> getAllChannel();

    /**
     * 获取频道的分页数据
     * 
     * @param pageNumber
     * @param pageSize
     * @return
     */
    PageBo<ChannelEntity> getChannelPage(@NotNull Integer pageNumber, @NotNull Integer pageSize);

    /**
     * 保存操作,带主键就更新,不带主键就插入
     * 
     * @return
     */
    boolean save(ChannelSaveReqDto channelSaveReqDto, String accountEmail);

    /**
     * 软删
     * 
     * @param channelId
     * @return
     */
    boolean delete(Integer channelId, String accountEmail);

}
