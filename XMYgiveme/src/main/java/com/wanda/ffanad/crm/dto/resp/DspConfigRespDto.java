package com.wanda.ffanad.crm.dto.resp;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanda.ffanad.common.utils.CurrencyUtils;

/**
 * Created by kevin on 16/9/20.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DspConfigRespDto implements Serializable {

    private Integer resId;

    private String  resName;

    private String  alias;

    private Byte    resPositiontype;

    private Byte    resBidtype;

    private Byte    resStatus;

    private String  resBelong;

    private Date    resCreatedate;

    private Integer resCarouselNum;

    private String  resContent;

    private Integer resAccountId;

    private Byte    resUsertype;

    private Byte    resTerminaltype;

    private Byte    resSwitch;

    private Byte    resAreaOrient;

    private Long    resCpc;

    private Long    resCpm;

    private String  resAuditComment;

    private Date    resUpdatedate;

    private Byte    originPlatform;

    private String  originId;

    private String  originName;

    /**
     * 用户邮箱
     */
    private String  accountEmail;

    private String  resApptype;

    /**
     * 配置行的主键
     */
    private Integer dspConfigId;

    /**
     * 是否开启
     */
    private String  dspEnable;

    /**
     * 帧号
     */
    private Byte    frameIndex;

    private Long    reservePrice;

    /**
     * 对应的dsp的Id
     */
    private Long    dpsId;

    /**
     * ios资源位在第三方dsp系统中的id
     */
    private String  externalResIosId;

    /**
     * android资源位在第三方dsp系统中的id
     */
    private String  externalResAndroidId;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Byte getResPositiontype() {
        return resPositiontype;
    }

    public void setResPositiontype(Byte resPositiontype) {
        this.resPositiontype = resPositiontype;
    }

    public Byte getResBidtype() {
        return resBidtype;
    }

    public void setResBidtype(Byte resBidtype) {
        this.resBidtype = resBidtype;
    }

    public Byte getResStatus() {
        return resStatus;
    }

    public void setResStatus(Byte resStatus) {
        this.resStatus = resStatus;
    }

    public String getResBelong() {
        return resBelong;
    }

    public void setResBelong(String resBelong) {
        this.resBelong = resBelong;
    }

    public Date getResCreatedate() {
        return resCreatedate;
    }

    public void setResCreatedate(Date resCreatedate) {
        this.resCreatedate = resCreatedate;
    }

    public Integer getResCarouselNum() {
        return resCarouselNum;
    }

    public void setResCarouselNum(Integer resCarouselNum) {
        this.resCarouselNum = resCarouselNum;
    }

    public String getResContent() {
        return resContent;
    }

    public void setResContent(String resContent) {
        this.resContent = resContent;
    }

    public Integer getResAccountId() {
        return resAccountId;
    }

    public void setResAccountId(Integer resAccountId) {
        this.resAccountId = resAccountId;
    }

    public Byte getResUsertype() {
        return resUsertype;
    }

    public void setResUsertype(Byte resUsertype) {
        this.resUsertype = resUsertype;
    }

    public Byte getResTerminaltype() {
        return resTerminaltype;
    }

    public void setResTerminaltype(Byte resTerminaltype) {
        this.resTerminaltype = resTerminaltype;
    }

    public Byte getResSwitch() {
        return resSwitch;
    }

    public void setResSwitch(Byte resSwitch) {
        this.resSwitch = resSwitch;
    }

    public Byte getResAreaOrient() {
        return resAreaOrient;
    }

    public void setResAreaOrient(Byte resAreaOrient) {
        this.resAreaOrient = resAreaOrient;
    }

    public Long getResCpc() {
        return resCpc;
    }

    public void setResCpc(Long resCpc) {
        this.resCpc = resCpc;
    }

    public Long getResCpm() {
        return resCpm;
    }

    public void setResCpm(Long resCpm) {
        this.resCpm = resCpm;
    }

    public String getResAuditComment() {
        return resAuditComment;
    }

    public void setResAuditComment(String resAuditComment) {
        this.resAuditComment = resAuditComment;
    }

    public Date getResUpdatedate() {
        return resUpdatedate;
    }

    public void setResUpdatedate(Date resUpdatedate) {
        this.resUpdatedate = resUpdatedate;
    }

    public Long getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(Long reservePrice) {
        this.reservePrice = reservePrice;
    }

    public Byte getOriginPlatform() {
        return originPlatform;
    }

    public void setOriginPlatform(Byte originPlatform) {
        this.originPlatform = originPlatform;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public String getResApptype() {
        return resApptype;
    }

    public void setResApptype(String resApptype) {
        this.resApptype = resApptype;
    }

    public Integer getDspConfigId() {
        return dspConfigId;
    }

    public void setDspConfigId(Integer dspConfigId) {
        this.dspConfigId = dspConfigId;
    }

    public String getDspEnable() {
        return dspEnable;
    }

    public void setDspEnable(String dspEnable) {
        this.dspEnable = dspEnable;
    }

    public Byte getFrameIndex() {
        return frameIndex;
    }

    public void setFrameIndex(Byte frameIndex) {
        this.frameIndex = frameIndex;
    }

    public String getExternalResIosId() {
        return externalResIosId;
    }

    public void setExternalResIosId(String externalResIosId) {
        this.externalResIosId = externalResIosId;
    }

    public String getExternalResAndroidId() {
        return externalResAndroidId;
    }

    public void setExternalResAndroidId(String externalResAndroidId) {
        this.externalResAndroidId = externalResAndroidId;
    }

    public String getResCpcStr() {
        return CurrencyUtils.format(this.getResCpc());
    }

    public String getResCpmStr() {
        return CurrencyUtils.format(this.getResCpm());
    }

    public String getReservePriceStr() {
        if (this.getReservePrice() == null) {
            return null;
        }
        return CurrencyUtils.format(this.getReservePrice());
    }

    public Long getDpsId() {
        return dpsId;
    }

    public void setDpsId(Long dpsId) {
        this.dpsId = dpsId;
    }
}
