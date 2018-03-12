package com.wanda.ffanad.crm.integration.userprofile.resp;

/**
 * 类TagDto.java的实现描述：标签
 * 
 * @author Yao 2017年4月19日 上午10:47:23
 */
public class TagDto {

    private Integer id;

    private String  name;

    private String  label;

    private Integer parentId;

    private Integer taxonomyId;

    private String  taxonomyName;

    private Long    enlistedNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Long getEnlistedNumber() {
        return enlistedNumber;
    }

    public void setEnlistedNumber(Long enlistedNumber) {
        this.enlistedNumber = enlistedNumber;
    }

    public Integer getTaxonomyId() {
        return taxonomyId;
    }

    public void setTaxonomyId(Integer taxonomyId) {
        this.taxonomyId = taxonomyId;
    }

    public String getTaxonomyName() {
        return taxonomyName;
    }

    public void setTaxonomyName(String taxonomyName) {
        this.taxonomyName = taxonomyName;
    }
}
