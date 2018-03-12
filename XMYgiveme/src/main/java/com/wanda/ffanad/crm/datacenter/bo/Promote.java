package com.wanda.ffanad.crm.datacenter.bo;

import java.io.Serializable;
import java.util.Date;


/**
 * 表t_ffad_promote对应的实体类
 * 
 * @Title: TFfadPromote.java
 * @author zhangxinfa
 * @Date 2016年5月9日  15:07
 *
 */
public class Promote implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private Long promoteId;

    private String promoteName;	//投放名称

    private Integer promoteResource;	//投放资源，0定制，1联盟

    private Long promoteTerminal;	//投放终端，定制：0APP，1飞凡M站，2Beacon，3云POS；联盟：0APP，2Beacon

    private Integer promoteApp;	//投放应用，定制APP和定制Beacon：0飞凡iOS，1飞凡安卓；联盟APP和联盟Beacon：0iOS，1安卓

    private Long resourceType;	//资源位类型，关联表t_ffad_dictionary中FFAN_AD_RES_TYPE子项

    private Long resourceId;	//资源位ID

    private Integer settleType;	//结算类型，0CPM，1CPC，2独占

    private Integer displayType;	//投放展示类型：0不限，1每日，2每周

    private Long displayFreq;	//投放展示频次(正整数)

    private Integer clickType;	//投放点击类型：0不限，1每日，2每周

    private Long clickFreq;	//投放点击频次(正整数)

    private Long budget;	//RTB时为日预算，独占时为独占费用。正整数，单位：分

    private Long maxBid;	//最高出价。正整数，单位：分

    private Integer status;	//投放状态：0停止，1有效，2过期

    private Integer auditStatus;	//审批状态：0待审批，1通过，2驳回

    private Integer timeDirect;	//时间定向：0不限，1定制（关联时间定向表）

    private Integer regionDirect;	//地域定向：0不限，1定制（关联地域定向表）

    private Long userId;	//用户ID

    private Date createTime;	//创建时间

    private Date updateTime;	//修改时间
    
    private Integer onShelf; //是否下架：0上架，1下架
    
    private Date startDate;   //投放开始日期
    
    private Date endDate;   //投放开始日期
    
    private String createDate;// String类型创建时间
    
    private String startTime;// String类型开始时间
    
    private String endTime;// String类型结束时间
    
    private Long creationId;//创意ID
    
    private String creationName;// 创意名称

	public Long getPromoteId() {
        return promoteId;
    }

    public void setPromoteId(Long promoteId) {
        this.promoteId = promoteId;
    }

    public String getPromoteName() {
        return promoteName;
    }

    public void setPromoteName(String promoteName) {
        this.promoteName = promoteName;
    }

    public Integer getPromoteResource() {
        return promoteResource;
    }

    public void setPromoteResource(Integer promoteResource) {
        this.promoteResource = promoteResource;
    }

    public Long getPromoteTerminal() {
        return promoteTerminal;
    }

    public void setPromoteTerminal(Long promoteTerminal) {
        this.promoteTerminal = promoteTerminal;
    }

    public Integer getPromoteApp() {
        return promoteApp;
    }

    public void setPromoteApp(Integer promoteApp) {
        this.promoteApp = promoteApp;
    }

    public Long getResourceType() {
        return resourceType;
    }

    public void setResourceType(Long resourceType) {
        this.resourceType = resourceType;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getSettleType() {
        return settleType;
    }

    public void setSettleType(Integer settleType) {
        this.settleType = settleType;
    }

    public Integer getDisplayType() {
        return displayType;
    }

    public void setDisplayType(Integer displayType) {
        this.displayType = displayType;
    }

    public Long getDisplayFreq() {
        return displayFreq;
    }

    public void setDisplayFreq(Long displayFreq) {
        this.displayFreq = displayFreq;
    }

    public Integer getClickType() {
        return clickType;
    }

    public void setClickType(Integer clickType) {
        this.clickType = clickType;
    }

    public Long getClickFreq() {
        return clickFreq;
    }

    public void setClickFreq(Long clickFreq) {
        this.clickFreq = clickFreq;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Long getMaxBid() {
        return maxBid;
    }

    public void setMaxBid(Long maxBid) {
        this.maxBid = maxBid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getTimeDirect() {
        return timeDirect;
    }

    public void setTimeDirect(Integer timeDirect) {
        this.timeDirect = timeDirect;
    }

    public Integer getRegionDirect() {
        return regionDirect;
    }

    public void setRegionDirect(Integer regionDirect) {
        this.regionDirect = regionDirect;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public Integer getOnShelf() {
		return onShelf;
	}

	public void setOnShelf(Integer onShelf) {
		this.onShelf = onShelf;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Long getCreationId() {
		return creationId;
	}

	public void setCreationId(Long creationId) {
		this.creationId = creationId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	
}