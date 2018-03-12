package com.wanda.ffanad.crm.dto.req;

/**
 * 类AdxDspDetail.java的实现描述：ADX 查询列表页vo
 * 
 * @author liuzhenkai1 2016年10月24日 上午9:20:52
 */
public class AdxDspPageReqVo {
    /**
     * dspname
     */
    private String dspName;
    /**
     * 接入方式0像对方获取广告1实时竞价
     */
    private String accessMode;
    /**
     * 状态N停用Y启用
     */
    private String status;
    private int    page  = 1;
    private int    limit = 20;
    private int    pageStartNo;

    public String getDspName() {
        return dspName;
    }

    public void setDspName(String dspName) {
        this.dspName = dspName;
    }

    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getPageStartNo() {
        return pageStartNo;
    }

    public void setPageStartNo(int pageStartNo) {
        this.pageStartNo = pageStartNo;
    }
}
