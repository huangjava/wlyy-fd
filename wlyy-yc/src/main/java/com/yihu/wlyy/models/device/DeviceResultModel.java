package com.yihu.wlyy.models.device;

import java.util.Map;

public class DeviceResultModel {

	protected boolean successFlg = true;
	protected String message;
	protected Map<String, Object> objectMap;

	public boolean isSuccessFlg() {
		return successFlg;
	}

	public void setSuccessFlg(boolean successFlg) {
		this.successFlg = successFlg;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getObjectMap() {
		return objectMap;
	}

	public void setObjectMap(Map<String, Object> objectMap) {
		this.objectMap = objectMap;
	}

	/**
	 * 错误消息
	 */
	public static DeviceResultModel error(String message) {
		DeviceResultModel re= new DeviceResultModel();
		re.successFlg = false;
		re.message = message;
		return re;
	}

	/**
	 * 成功消息
	 */
	public static DeviceResultModel success(String message) {
		DeviceResultModel re= new DeviceResultModel();
		re.successFlg = true;
		re.message = message;
		return re;
	}
}
