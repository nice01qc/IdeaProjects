package com.wanda.ffanad.crm.common;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.wanda.ffanad.core.remote.resp.AppStatisticsRemoteRespDetail;

public class ResourceDatastatisticsResponse {
	private int status = HttpStatus.OK.value();

	private String message = HttpStatus.OK.name();

	private List<AppStatisticsRemoteRespDetail> data;

	public ResourceDatastatisticsResponse() {
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

	public List<AppStatisticsRemoteRespDetail> getData() {
		return data;
	}

	public void setData(List<AppStatisticsRemoteRespDetail> data) {
		this.data = data;
	}
}
