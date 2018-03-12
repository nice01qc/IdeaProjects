package com.wanda.ffanad.crm.dto.req;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * 频道保存 Created by kevin on 16/11/17.
 */
public class ChannelSaveReqDto {
    /**
     * 频道ID
     */
    @Min(value = 1, message = "频道id格式错误")
    private Integer id;

    @NotBlank(message = "频道名称不能为空")
    @Length(max = 30, message = "频道名称的长度应该为1-30")
    private String  name;

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
}
