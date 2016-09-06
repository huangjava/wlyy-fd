package com.yihu.wlyy.models.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 设备列表
 */
@Entity
@Table(name = "dm_device")
public class Device extends IdModel {

	private String categoryCode;
	private String photo;
	private String brands;
	private String model;
	private String isMultiUser;
	private String multiUser;
	private String name;
	private Date czrq;
	private String del;

	@Column(name="category_code")
	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	@Column(name="photo")
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name="brands")
	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	@Column(name="model")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name="is_multi_user")
	public String getIsMultiUser() {
		return isMultiUser;
	}

	public void setIsMultiUser(String isMultiUser) {
		this.isMultiUser = isMultiUser;
	}

	@Column(name="multi_user")
	public String getMultiUser() {
		return multiUser;
	}

	public void setMultiUser(String multiUser) {
		this.multiUser = multiUser;
	}

	@Column(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	@Column(name="czrq")
	public Date getCzrq() {
		return czrq;
	}

	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}

	@Column(name="del")
	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}
}
