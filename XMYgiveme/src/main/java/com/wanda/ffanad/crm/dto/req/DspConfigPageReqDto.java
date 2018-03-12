package com.wanda.ffanad.crm.dto.req;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * 第三方dsp分页信息请求参数
 */
public class DspConfigPageReqDto {

    /**
     * 资源位id
     */
    private Integer resId;
    /**
     * 资源位名称
     */
    private String  resName;
    /**
     * 资源位别名
     */
    private String  alias;
    /**
     * 用户邮箱
     */
    private String  resUserEmail;

    /**
     * 资源位类型
     */
    private Integer resPositiontype;

    /**
     * 资源位属性：用户类型
     */
    private Integer resUsertype;
    /**
     * 资源位属性：终端类型
     */
    private Integer resTerminaltype;
    /**
     * 资源位属性：App类型
     */
    private Integer resApptype;

    private String  dspEnable;

    @NotNull
    private Long    dspId;

    private int     pageNumber = 1;

    private int     pageSize   = 20;

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

    public String getResUserEmail() {
        return resUserEmail;
    }

    public void setResUserEmail(String resUserEmail) {
        this.resUserEmail = resUserEmail;
    }

    public Integer getResPositiontype() {
        return resPositiontype;
    }

    public void setResPositiontype(Integer resPositiontype) {
        this.resPositiontype = resPositiontype;
    }

    public Integer getResUsertype() {
        return resUsertype;
    }

    public void setResUsertype(Integer resUsertype) {
        this.resUsertype = resUsertype;
    }

    public Integer getResTerminaltype() {
        return resTerminaltype;
    }

    public void setResTerminaltype(Integer resTerminaltype) {
        this.resTerminaltype = resTerminaltype;
    }

    public Integer getResApptype() {
        return resApptype;
    }

    public void setResApptype(Integer resApptype) {
        this.resApptype = resApptype;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDspEnable() {
        return dspEnable;
    }

    public void setDspEnable(String dspEnable) {
        this.dspEnable = dspEnable;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getDspId() {
        return dspId;
    }

    public void setDspId(Long dspId) {
        this.dspId = dspId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
