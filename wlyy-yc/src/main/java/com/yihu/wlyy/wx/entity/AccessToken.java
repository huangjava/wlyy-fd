package com.yihu.wlyy.wx.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yihu.wlyy.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "wx_access_token")
public class AccessToken extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3297644428262254694L;
	
	private String access_token;

	private Long add_timestamp;

	private Long expires_in;
	
	private Date czrq;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public Long getAdd_timestamp() {
		return add_timestamp;
	}

	public void setAdd_timestamp(Long add_timestamp) {
		this.add_timestamp = add_timestamp;
	}

	public Long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(Long expires_in) {
		this.expires_in = expires_in;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getCzrq() {
		return czrq;
	}

	public void setCzrq(Date czrq) {
		this.czrq = czrq;
	}



   

}
