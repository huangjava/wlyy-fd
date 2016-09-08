package com.yihu.wlyy.models.address;

import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 區縣信息對象
 * @author George
 *
 */

@Entity
@Table(name = "dm_town")
public class Town extends IdModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3851597133734507811L;
	
	// 省編碼
	private String province;
	// 城市編碼
	private String city;
	// 區縣編碼
	private String code;
	// 區縣名稱
	private String name;

	private String photo;

	public Town() {

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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
