package com.wanda.ffanad.crm.dto.req;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 修改第三方dsp配置信息
 */
public class DspConfigUpdateReqDto {

    /**
     * 资源位id
     */
    @NotNull(message = "参数resId不能为空")
    @Min(value = 1, message = "参数resId不在正确的取值范围内")
    private Integer resId;

    /**
     * 是否打开
     */
    @NotBlank(message = "参数dspEnable不能为空")
    private String  dspEnable;

    /**
     * 保留价(元)
     */
    @DecimalMin(value = "0.01", message = "参数reservePrice不在正确的取值范围内")
    @DecimalMax(value = "99.99", message = "参数reservePrice不在正确的取值范围内")
    private Double  reservePrice;

    /**
     * 插入位置
     */
    @Min(value = 1, message = "参数frameIndex不在正确的取值范围内")
    @Max(value = 99, message = "参数frameIndex不在正确的取值范围内")
    private Byte    frameIndex;

    /**
     * ios资源在第三方DSP系统中的id
     */
    @Length(max = 99, message = "参数externalResIosId长度不能超过99")
    private String  externalResIosId;

    /**
     * android资源在第三方DSP系统中的id
     */
    @Length(max = 99, message = "参数externalResAndroidId长度不能超过99")
    private String  externalResAndroidId;

    @NotNull(message = "参数dspId不能为空")
    private Long    dspId;

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getDspEnable() {
        return dspEnable;
    }

    public void setDspEnable(String dspEnable) {
        this.dspEnable = dspEnable;
    }

    public Double getReservePrice() {
        return reservePrice == null ? 0 : reservePrice;
    }

    public void setReservePrice(Double reservePrice) {
        this.reservePrice = reservePrice;
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

    public Long getDspId() {
        return dspId;
    }

    public void setDspId(Long dspId) {
        this.dspId = dspId;
    }
}
