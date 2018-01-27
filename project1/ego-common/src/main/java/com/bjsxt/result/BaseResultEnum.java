package com.bjsxt.result;

public enum BaseResultEnum {
	/**
	 * 为什么使用枚举类？
	 * 1.提升代码的阅读性，避免硬编码。
	 * 2.程序解耦
	 */
	//自定义枚举类
	SUCESS(200,"成功"),
	ERROR(400,"失败"),
	
	PASS_ERROR_01(501,"两次输入的密码不一致"),
	PASS_ERROR_02(502,"输入的原始密码不正确");
	
	private Integer code;
	private String message;
	//自定义构造方法
	public Integer getCode() {
		return code;
	}
	private BaseResultEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
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
	
	
}
