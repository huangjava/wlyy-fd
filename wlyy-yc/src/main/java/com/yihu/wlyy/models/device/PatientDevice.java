package com.yihu.wlyy.models.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "wlyy_patient_device")
public class PatientDevice extends IdModel {

	// 设备ID
	private Long deviceId;
	// 设备标识
	private String deviceSn;
	// 设备名称
	private String deviceName;
	// 用户code
	private String user;
	// 设备类型标识
	private String categoryCode;
	// 用户类型标准 -1代表单用户
	private String userType;
	// 用户身份证
	private String userIdcard;
	// 操作时间
	private Date czrq;

	@Column(name="device_id")
	public Long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}

	@Column(name="device_sn")
	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}

	@Column(name="device_name")
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Column(name="user")
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Column(name="category_code")
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Column(name="user_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Column(name="user_idcard")
	public String getUserIdcard() {
		return userIdcard;
	}

	public void setUserIdcard(String userIdcard) {
		this.userIdcard = userIdcard;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name="czrq")
	public Date getCzrq() {
		return czrq;
	}

	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}

}
