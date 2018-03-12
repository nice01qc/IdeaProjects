package com.wanda.ffanad.crm.dto.resp;

import com.wanda.ffanad.base.enums.AllocationRequestOsEnum;
import com.wanda.ffanad.base.enums.PromoteSettleTypeEnum;
import com.wanda.ffanad.common.utils.CurrencyUtils;

/**
 * Created by kevin on 16/9/23.
 */
public class DspBillCheckDetailRespDto {

    /**
     * 投放ID
     */
    private Long   promoteId;

    private String allocateTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 资源别名
     */
    private String resAlias;

    /**
     * 系统
     */
    private Short  os;

    /**
     * 城市ID
     */
    private String city;

    /**
     * 展示量
     */
    private Long   displayCount;

    /**
     * 点击量
     */
    private Long   clickCount = 0L;

    /**
     * 结算类型
     */
    private Byte   settleType;

    /**
     * 消费金额
     */
    private Long   amount;

    private String amountStr;

    private String resName;

    private String osStr;

    private String cityStr;

    private String settleTypeStr;

    public Long getPromoteId() {
        return promoteId;
    }

    public void setPromoteId(Long promoteId) {
        this.promoteId = promoteId;
    }

    public String getAllocateTime() {
        return allocateTime;
    }

    public void setAllocateTime(String allocateTime) {
        this.allocateTime = allocateTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getResAlias() {
        return resAlias;
    }

    public void setResAlias(String resAlias) {
        this.resAlias = resAlias;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(Long displayCount) {
        this.displayCount = displayCount;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Byte getSettleType() {
        return settleType;
    }

    public void setSettleType(Byte settleType) {
        this.settleType = settleType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public Short getOs() {
        return os;
    }

    public void setOs(Short os) {
        this.os = os;
    }

    public String getOsStr() {
        if (os != null) {
            switch (os) {
                case 1:
                    return AllocationRequestOsEnum.Android.getName();
                case 2:
                    return AllocationRequestOsEnum.iOS.getName();
                case 3:
                    return AllocationRequestOsEnum.WP.getName();
            }
        }
        return osStr;
    }

    public void setOsStr(String osStr) {
        this.osStr = osStr;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getAmountStr() {
        if (amount != null) {
            return CurrencyUtils.format(amount);
        }
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    public String getSettleTypeStr() {
        if (settleType != null) {
            switch (settleType.intValue()) {
                case 0:
                    settleTypeStr = PromoteSettleTypeEnum.CPM.name().toString();
                    break;
                case 1:
                    settleTypeStr = PromoteSettleTypeEnum.CPC.name().toString();
                    break;
                case 2:
                    settleTypeStr = PromoteSettleTypeEnum.CPD.name().toString();
                    break;
            }
        }
        return settleTypeStr;
    }

    public void setSettleTypeStr(String settleTypeStr) {
        this.settleTypeStr = settleTypeStr;
    }
}
