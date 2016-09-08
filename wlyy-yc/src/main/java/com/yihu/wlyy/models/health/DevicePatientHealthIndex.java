package com.yihu.wlyy.models.health;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yihu.wlyy.models.common.IdModel;

import javax.persistence.*;
import java.util.Date;

/**
 * 健康指标
 * @author George
 *
 */
@Entity
@Table(name = "wlyy_patient_health_index")
//@NamedNativeQueries({ @NamedNativeQuery(name = "findRecentByPatient", query = "select b.* from (SELECT a.* FROM wlyy_patient_health_index a where a.user = ?1 order by a.record_date desc) b GROUP BY b.type", resultClass = DevicePatientHealthIndex.class) })
public class DevicePatientHealthIndex extends IdModel {


	// 患者标志
	private String user;
	// 干预标志
	private String intervene;
	// 血糖/收缩压/体重/腰围/早餐前空腹
	private String value1;
	// 舒张压/早餐后空腹
	private String value2;
	// 午餐空腹
	private String value3;
	// 午餐后
	private String value4;
	// 晚餐空腹
	private String value5;
	// 晚餐后
	private String value6;
	// 睡前
	private String value7;
	// 健康指标类型（1血糖，2血压，3体重，4腰围）
	private int type;
	// 记录时间
	private Date recordDate;
	// 排序日期
	private Date sortDate;
	// 添加时间
	private Date czrq;
	// 是否作废，1正常，0作废
	private String del;
	//身份证号码
	private String idcard;
	//设备编号
	private String deviceSn;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getIntervene() {
		return intervene;
	}

	public void setIntervene(String intervene) {
		this.intervene = intervene;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public String getValue5() {
		return value5;
	}

	public void setValue5(String value5) {
		this.value5 = value5;
	}

	public String getValue6() {
		return value6;
	}

	public void setValue6(String value6) {
		this.value6 = value6;
	}

	public String getValue7() {
		return value7;
	}

	public void setValue7(String value7) {
		this.value7 = value7;
	}

	@Column(name = "record_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "sort_date")
	public Date getSortDate() {
		return sortDate;
	}

	public void setSortDate(Date sortDate) {
		this.sortDate = sortDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

	@Column(name = "Idcard")
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name = "device_sn")
	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
}
