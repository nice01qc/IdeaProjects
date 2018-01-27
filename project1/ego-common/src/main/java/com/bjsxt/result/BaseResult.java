package com.bjsxt.result;

import java.io.Serializable;

import com.bjsxt.result.BaseResultEnum;
import com.github.pagehelper.PageInfo;

public class BaseResult implements Serializable {
	private static final long serialVersionUID = 5222216402938736979L;
	//状态编码
	private Integer code;
	//状态描述 
	private String message;
	//分页对象
	PageInfo<?> pageInfo;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}


	public BaseResult(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	public BaseResult() {
		super();
	}
	public PageInfo<?> getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo<?> pageInfo) {
		this.pageInfo = pageInfo;
	}
	//成功返回的对象
	public static BaseResult success(){
		BaseResult result = new BaseResult();
		result.setCode(BaseResultEnum.SUCESS.getCode());
		result.setMessage(BaseResultEnum.SUCESS.getMessage());
		return result;
	}

	//成功返回的对象
	public static BaseResult success(PageInfo<?> pageInfo){
		BaseResult result = new BaseResult();
		result.setCode(BaseResultEnum.SUCESS.getCode());
		result.setMessage(BaseResultEnum.SUCESS.getMessage());
		result.setPageInfo(pageInfo);
		return result;
	}

	//失败返回的对象
	public static BaseResult error(){
		BaseResult result = new BaseResult();
		result.setCode(BaseResultEnum.ERROR.getCode());
		result.setMessage(BaseResultEnum.ERROR.getMessage());
		return result;
	}
	@Override
	public String toString() {
		return "BaseResult [code=" + code + ", message=" + message + "]";
	}
}
