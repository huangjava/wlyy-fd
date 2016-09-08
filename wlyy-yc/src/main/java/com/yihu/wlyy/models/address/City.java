package com.yihu.wlyy.models.address;

import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 城市信息對象
 * @author George
 *
 */

@Entity
@Table(name = "dm_city")
public class City extends IdModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 366338400555505599L;
	
	// 省編碼
	private String province;
	// 城市編碼
	private String code;
	// 城市名稱
	private String name;

	public City() {

	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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
