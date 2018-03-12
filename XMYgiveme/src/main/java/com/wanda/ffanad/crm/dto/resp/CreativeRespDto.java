package com.wanda.ffanad.crm.dto.resp;

import com.wanda.ffanad.core.domains.CreationInfoPic;

import java.util.Date;
import java.util.List;

/**
 * 创意详情返回值
 */
public class CreativeRespDto {

    private Integer    id;

    private String     creationName;

    private String     resourceLocationType;

    private String     resourceId;

    private Integer    creationLinkType;

    private String     creationLinkContent;

    private Byte       contentType;

    private String     title;

    private String     subtitle;

    private String     advertiser;

    private Date       createTime;

    private Date       updateTime;

    private String     userId;

    private Byte       status;

    private Byte       auditStatus;

    private String     auditReason;

    private Byte       posStatus;

    private Integer    ifFilterLinkType;    //是否根据创意连接类型过滤创意

    private List<Long> linkTypeIds;         //创意连接类型ids

	private Boolean    isDeleted;

	/**
	 * 用户邮箱
	 */
	private String accountEmail;

	private String creationLinkName;

	private List<CreationInfoPic> creationPicList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreationName() {
		return creationName;
	}

	public void setCreationName(String creationName) {
		this.creationName = creationName;
	}

	public String getResourceLocationType() {
		return resourceLocationType;
	}

	public void setResourceLocationType(String resourceLocationType) {
		this.resourceLocationType = resourceLocationType;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getCreationLinkType() {
		return creationLinkType;
	}

	public void setCreationLinkType(Integer creationLinkType) {
		this.creationLinkType = creationLinkType;
	}

	public String getCreationLinkContent() {
		return creationLinkContent;
	}

	public void setCreationLinkContent(String creationLinkContent) {
		this.creationLinkContent = creationLinkContent;
	}

	public Byte getContentType() {
		return contentType;
	}

	public void setContentType(Byte contentType) {
		this.contentType = contentType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getAdvertiser() {
		return advertiser;
	}

	public void setAdvertiser(String advertiser) {
		this.advertiser = advertiser;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Byte getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditReason() {
		return auditReason;
	}

	public void setAuditReason(String auditReason) {
		this.auditReason = auditReason;
	}

	public Byte getPosStatus() {
		return posStatus;
	}

	public void setPosStatus(Byte posStatus) {
		this.posStatus = posStatus;
	}

	public Integer getIfFilterLinkType() {
		return ifFilterLinkType;
	}

	public void setIfFilterLinkType(Integer ifFilterLinkType) {
		this.ifFilterLinkType = ifFilterLinkType;
	}

	public List<Long> getLinkTypeIds() {
		return linkTypeIds;
	}

	public void setLinkTypeIds(List<Long> linkTypeIds) {
		this.linkTypeIds = linkTypeIds;
	}

	public Boolean getDeleted() {
		return isDeleted;
	}

	public void setDeleted(Boolean deleted) {
		isDeleted = deleted;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public void setAccountEmail(String accountEmail) {
		this.accountEmail = accountEmail;
	}

	public String getCreationLinkName() {
		return creationLinkName;
	}

	public void setCreationLinkName(String creationLinkName) {
		this.creationLinkName = creationLinkName;
	}

	public List<CreationInfoPic> getCreationPicList() {
		return creationPicList;
	}

	public void setCreationPicList(List<CreationInfoPic> creationPicList) {
		this.creationPicList = creationPicList;
	}
}
