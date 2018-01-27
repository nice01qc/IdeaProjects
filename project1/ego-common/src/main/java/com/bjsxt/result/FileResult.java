package com.bjsxt.result;

/**
 * 文件上传返回对象
 * @author zhiduo
 *
 */
public class FileResult {
	//success字符串，为bootstrap file input 必须包含该属性
	private String success;
	//error字符串，为bootstrap file input 必须包含该属性
	private String error;
	//描述信息
	private String message;
	//文件路径
	private String fileUrl;
	
	public FileResult() {
		super();
	}
	
	public FileResult(String success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
