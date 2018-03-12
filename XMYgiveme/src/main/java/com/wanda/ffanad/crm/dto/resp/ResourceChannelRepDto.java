package com.wanda.ffanad.crm.dto.resp;

import com.wanda.ffanad.base.dal.entities.ResourceInfoEntity;

import java.io.Serializable;

/**
 * 频道管理列表中的实体 Created by kevin on 16/11/17.
 */
public class ResourceChannelRepDto extends ResourceInfoEntity implements Serializable {

    /**
     * 频道名称
     */
    private String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
