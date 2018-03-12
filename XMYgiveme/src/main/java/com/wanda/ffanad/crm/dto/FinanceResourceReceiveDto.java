/*
 * Copyright 2016 ffan.com All right reserved. This software is the
 * confidential and proprietary information of ffan.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with ffan.com.
 */
package com.wanda.ffanad.crm.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 类FinanceResourceReceiveDto.java的实现描述：TODO 类实现描述
 * 
 * @author yanji 2016年6月12日 下午1:34:43
 */
public class FinanceResourceReceiveDto implements Serializable {

    private static final long serialVersionUID = -8077077591661503001L;
    
    long                             resourceId;      //Long ,资源Id
    List<FinanceAccountBillCheckDto> statementDayData;//返回资源位月账单daily数据
    
    public long getResourceId() {
        return resourceId;
    }
    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }
    public List<FinanceAccountBillCheckDto> getStatementDayData() {
        return statementDayData;
    }
    public void setStatementDayData(List<FinanceAccountBillCheckDto> statementDayData) {
        this.statementDayData = statementDayData;
    }
    
    
}
