package com.wanda.ffanad.crm.dto.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wanda.ffanad.base.dal.entities.TargettingPackageEntity;
import com.wanda.ffanad.base.dal.entities.TrackingLinkEntity;
import com.wanda.ffanad.common.utils.CurrencyUtils;
import com.wanda.ffanad.core.bo.PromoteAppBo;
import com.wanda.ffanad.core.bo.PromoteAppCateBo;
import com.wanda.ffanad.core.bo.PromoteRegionBo;
import com.wanda.ffanad.core.domains.TFfadPromoteDate;
import com.wanda.ffanad.core.domains.TFfadPromoteTime;
import com.wanda.ffanad.crm.integration.userprofile.resp.TagDto;

public class ProgramDetailDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long promoteId;

	private String promoteName;

	private Byte promoteResource;

	private Byte promoteTerminal;

	private Byte resourceType;

	private Long resourceId;

	private Byte settleType;

	private Byte displayType;

	private Long displayFreq;

	private Byte clickType;

	private Long clickFreq;

	private Long budget;

	private Long maxBid;

	private Byte status;

	private Byte timeDirect;

	private Byte regionDirect;

	private Long userId;

	private Date createTime;

	private Date updateTime;

	private Byte onShelf;

	private Long creationId;

	private Integer auditStatus;

	private Integer displayCount;

	private Integer clickCount;

	private Byte promoteTarget;

	private Byte frameIndex;

	private Integer targettingPackageId;
	
	private String promoteResourceDesc;

	private String promoteTerminalDesc;

	private String resourceTypeDesc;

	private String settleTypeDesc;

	private String displayTypeDesc;

	private String clickTypeDesc;

	private String statusDesc;

	private String auditStatusDesc;

	private String timeDirectDesc;

	private String regionDirectDesc;

	private String onShelfDesc;

	private String resourceName; // 资源名称

	private String creationName; // 创意名称

	private String creationLinkType; // 创意连接类型
	private String creationLinkTypeName; // 创意连接类型名称

	private String creationLinkUrl; // 创意连接内容

	private List<PromoteAppBo> promoteApps;

	private List<PromoteAppCateBo> promoteAppCateBoList;

	private List<TFfadPromoteDate> promoteDateList;

	private List<PromoteRegionBo> promoteRegioBoList;

	private List<TFfadPromoteTime> promoteTimeList;

	private List<TrackingLinkEntity> trackingLinks; // 监控链接

	private String accountEmail;
	
	private TargettingPackageEntity  targetPackage;
	
	private List<TagDto> tags;

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

	public Byte getPromoteResource() {
		return promoteResource;
	}

	public void setPromoteResource(Byte promoteResource) {
		this.promoteResource = promoteResource;
	}

	public Byte getPromoteTerminal() {
		return promoteTerminal;
	}

	public void setPromoteTerminal(Byte promoteTerminal) {
		this.promoteTerminal = promoteTerminal;
	}

	public Byte getResourceType() {
		return resourceType;
	}

	public void setResourceType(Byte resourceType) {
		this.resourceType = resourceType;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Byte getSettleType() {
		return settleType;
	}

	public void setSettleType(Byte settleType) {
		this.settleType = settleType;
	}

	public Byte getDisplayType() {
		return displayType;
	}

	public void setDisplayType(Byte displayType) {
		this.displayType = displayType;
	}

	public Long getDisplayFreq() {
		return displayFreq;
	}

	public void setDisplayFreq(Long displayFreq) {
		this.displayFreq = displayFreq;
	}

	public Byte getClickType() {
		return clickType;
	}

	public void setClickType(Byte clickType) {
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

	public String getBudgetStr() {
        return CurrencyUtils.format(this.getBudget());
    }
	
	public void setBudget(Long budget) {
		this.budget = budget;
	}

	public Long getMaxBid() {
		return maxBid;
	}
	
	public String getMaxBidStr() {
        return CurrencyUtils.format(this.getMaxBid());
    }

	public void setMaxBid(Long maxBid) {
		this.maxBid = maxBid;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getTimeDirect() {
		return timeDirect;
	}

	public void setTimeDirect(Byte timeDirect) {
		this.timeDirect = timeDirect;
	}

	public Byte getRegionDirect() {
		return regionDirect;
	}

	public void setRegionDirect(Byte regionDirect) {
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

	public Byte getOnShelf() {
		return onShelf;
	}

	public void setOnShelf(Byte onShelf) {
		this.onShelf = onShelf;
	}

	public Long getCreationId() {
		return creationId;
	}

	public void setCreationId(Long creationId) {
		this.creationId = creationId;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Integer getDisplayCount() {
		return displayCount;
	}

	public void setDisplayCount(Integer displayCount) {
		this.displayCount = displayCount;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Byte getPromoteTarget() {
		return promoteTarget;
	}

	public void setPromoteTarget(Byte promoteTarget) {
		this.promoteTarget = promoteTarget;
	}

	public Byte getFrameIndex() {
		return frameIndex;
	}

	public void setFrameIndex(Byte frameIndex) {
		this.frameIndex = frameIndex;
	}

	public Integer getTargettingPackageId() {
		return targettingPackageId;
	}

	public void setTargettingPackageId(Integer targettingPackageId) {
		this.targettingPackageId = targettingPackageId;
	}

	public String getPromoteResourceDesc() {
		return promoteResourceDesc;
	}

	public void setPromoteResourceDesc(String promoteResourceDesc) {
		this.promoteResourceDesc = promoteResourceDesc;
	}

	public String getPromoteTerminalDesc() {
		return promoteTerminalDesc;
	}

	public void setPromoteTerminalDesc(String promoteTerminalDesc) {
		this.promoteTerminalDesc = promoteTerminalDesc;
	}

	public String getResourceTypeDesc() {
		return resourceTypeDesc;
	}

	public void setResourceTypeDesc(String resourceTypeDesc) {
		this.resourceTypeDesc = resourceTypeDesc;
	}

	public String getSettleTypeDesc() {
		return settleTypeDesc;
	}

	public void setSettleTypeDesc(String settleTypeDesc) {
		this.settleTypeDesc = settleTypeDesc;
	}

	public String getDisplayTypeDesc() {
		return displayTypeDesc;
	}

	public void setDisplayTypeDesc(String displayTypeDesc) {
		this.displayTypeDesc = displayTypeDesc;
	}

	public String getClickTypeDesc() {
		return clickTypeDesc;
	}

	public void setClickTypeDesc(String clickTypeDesc) {
		this.clickTypeDesc = clickTypeDesc;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getAuditStatusDesc() {
		return auditStatusDesc;
	}

	public void setAuditStatusDesc(String auditStatusDesc) {
		this.auditStatusDesc = auditStatusDesc;
	}

	public String getTimeDirectDesc() {
		return timeDirectDesc;
	}

	public void setTimeDirectDesc(String timeDirectDesc) {
		this.timeDirectDesc = timeDirectDesc;
	}

	public String getRegionDirectDesc() {
		return regionDirectDesc;
	}

	public void setRegionDirectDesc(String regionDirectDesc) {
		this.regionDirectDesc = regionDirectDesc;
	}

	public String getOnShelfDesc() {
		return onShelfDesc;
	}

	public void setOnShelfDesc(String onShelfDesc) {
		this.onShelfDesc = onShelfDesc;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public String getCreationLinkType() {
		return creationLinkType;
	}

	public void setCreationLinkType(String creationLinkType) {
		this.creationLinkType = creationLinkType;
	}

	public String getCreationLinkTypeName() {
		return creationLinkTypeName;
	}

	public void setCreationLinkTypeName(String creationLinkTypeName) {
		this.creationLinkTypeName = creationLinkTypeName;
	}

	public String getCreationLinkUrl() {
		return creationLinkUrl;
	}

	public void setCreationLinkUrl(String creationLinkUrl) {
		this.creationLinkUrl = creationLinkUrl;
	}

	public List<PromoteAppBo> getPromoteApps() {
		return promoteApps;
	}

	public void setPromoteApps(List<PromoteAppBo> promoteApps) {
		this.promoteApps = promoteApps;
	}

	public List<PromoteAppCateBo> getPromoteAppCateBoList() {
		return promoteAppCateBoList;
	}

	public void setPromoteAppCateBoList(List<PromoteAppCateBo> promoteAppCateBoList) {
		this.promoteAppCateBoList = promoteAppCateBoList;
	}

	public List<TFfadPromoteDate> getPromoteDateList() {
		return promoteDateList;
	}

	public void setPromoteDateList(List<TFfadPromoteDate> promoteDateList) {
		this.promoteDateList = promoteDateList;
	}

	public List<PromoteRegionBo> getPromoteRegioBoList() {
		return promoteRegioBoList;
	}

	public void setPromoteRegioBoList(List<PromoteRegionBo> promoteRegioBoList) {
		this.promoteRegioBoList = promoteRegioBoList;
	}

	public List<TFfadPromoteTime> getPromoteTimeList() {
		return promoteTimeList;
	}

	public void setPromoteTimeList(List<TFfadPromoteTime> promoteTimeList) {
		this.promoteTimeList = promoteTimeList;
	}

	public List<TrackingLinkEntity> getTrackingLinks() {
		return trackingLinks;
	}

	public void setTrackingLinks(List<TrackingLinkEntity> trackingLinks) {
		this.trackingLinks = trackingLinks;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public List<TagDto> getTags() {
		return tags;
	}

	public void setTags(List<TagDto> tags) {
		this.tags = tags;
	}
	
	public Map<String, List<TagDto>> getTargetting() {
		if (tags == null) {
			return null;
		}
		
		Map<String, List<TagDto>> map = new HashMap<String, List<TagDto>>(tags.size());
		
		for (TagDto dto : tags) {
			if (!map.containsKey(dto.getTaxonomyName())) {
				map.put(dto.getTaxonomyName(), new ArrayList<TagDto>());
			}

			map.get(dto.getTaxonomyName()).add(dto);
		}
		
		return map;
	}

    public TargettingPackageEntity getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(TargettingPackageEntity targetPackage) {
        this.targetPackage = targetPackage;
    }
}
