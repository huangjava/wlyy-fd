package com.yihu.wlyy.models.address;

import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 街道信息对象
 * @author George
 *
 */

@Entity
@Table(name = "dm_committee")
public class Committee extends IdModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 2307784886255268677L;

	// 省編碼
	private String province;
	// 城市編碼
	private String city;
	// 区县编码
	private String town;
	// 區縣編碼
	private String street;
	// 居委会编码
	private String code;
	// 居委会名稱
	private String name;

	public Committee() {

	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

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
}
