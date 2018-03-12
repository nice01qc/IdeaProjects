package com.wanda.ffanad.crm.common;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author hanpeng
 * @date 2016年6月13日 
 */
public class RestResponse {
	private int status = HttpStatus.OK.value();

	private String message = HttpStatus.OK.name();

	private Object data;

	public RestResponse() {
	}

	public RestResponse(Object data) {
		this.data = data;
	}

	public RestResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public RestResponse(int status, String message, Object data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
