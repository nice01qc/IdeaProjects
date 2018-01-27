package com.bjsxt.enums;

public enum ShippingStatus {
	//订单状态 0未发货 1已发货 2 部分发货
	no_send((byte)0,"未发货"),
	has_send((byte)1,"已发货"),
	part_send((byte)2,"部分发货");
	//状态
	private Byte status;
	//描述
	private String message;
	
	//自定义构造器
	private ShippingStatus(Byte status, String message) {
		this.status = status;
		this.message = message;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
