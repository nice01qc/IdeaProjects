package com.wanda.ffanad.crm.dto.req;

import java.io.Serializable;

/**
 * 频道管理分页参数 Created by kevin on 16/11/17.
 */
public class ResourceChannelPageReqDto implements Serializable {

    /**
     * 频道Id
     */
    private Integer channelId;

    /**
     * 资源位id
     */
    private Integer resId;
    /**
     * 资源位名称
     */
    private String  resName;

    private int     page  = 1;

    private int     limit = 20;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
