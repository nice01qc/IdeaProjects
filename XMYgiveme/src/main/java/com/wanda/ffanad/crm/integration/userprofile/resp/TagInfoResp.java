package com.wanda.ffanad.crm.integration.userprofile.resp;

/**
 * 类TagsInfoResp.java的实现描述：queryTags接口返回对象封装
 * 
 * @author Yao 2017年4月20日 上午10:18:24
 */
public class TagInfoResp {

    private int    status;

    private String message;

    private TagDto data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TagDto getData() {
        return data;
    }

    public void setData(TagDto data) {
        this.data = data;
    }
}
