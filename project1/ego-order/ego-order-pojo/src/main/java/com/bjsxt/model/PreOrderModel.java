package com.bjsxt.model;

import java.io.Serializable;
import java.util.List;

import com.bjsxt.pojo.PreOrder;

public class PreOrderModel implements Serializable {
	private static final long serialVersionUID = 4314145144118831362L;
	//预定单 列表
	private List<PreOrder> preOrderList;

	public List<PreOrder> getPreOrderList() {
		return preOrderList;
	}

	public void setPreOrderList(List<PreOrder> preOrderList) {
		this.preOrderList = preOrderList;
	}
}
