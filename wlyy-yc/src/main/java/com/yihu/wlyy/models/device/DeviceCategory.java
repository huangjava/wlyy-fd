package com.yihu.wlyy.models.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 设备分类表
 */
@Entity
@Table(name = "dm_device_category")
public class DeviceCategory extends IdModel {

	// 设备类型标识
	private String code;
	// 设备类型名称
	private String name;
	// 操作时间
	private Date czrq;
	private String del;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	public Date getCzrq() {
		return czrq;
	}

	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}
}
