package com.wanda.ffanad.crm.datacenter.bo;

import java.io.Serializable;

/**
 * 广告账户金额信息 
 * 类AdAccountInfo.java的实现描述：TODO 类实现描述 
 * @author czs 2016年6月7日 下午1:32:22
 */
public class AdAccountInfo implements Serializable{
    /**投放金额**/
    private Double adConsume;
    /**余额**/
    private Double adBalance;
    public Double getAdConsume() {
        return adConsume;
    }
    public void setAdConsume(Double adConsume) {
        this.adConsume = adConsume;
    }
    public Double getAdBalance() {
        return adBalance;
    }
    public void setAdBalance(Double adBalance) {
        this.adBalance = adBalance;
    }
    
    
}
