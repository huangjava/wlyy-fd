package com.yihu.wlyy.services.device;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.configuration.BloodSuggerConfig;
import com.yihu.wlyy.daos.*;
import com.yihu.wlyy.models.device.*;
import com.yihu.wlyy.util.DateUtil;
import com.yihu.wlyy.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备管理
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class DeviceService  {

	@Autowired
	DeviceInfoDao deviceInfoDao;
	@Autowired
	private PatientDeviceDao patientDeviceDao;
	@Autowired
	private PatientHealthIndexDao patientHealthIndexDao;
	@Autowired
	private BloodSuggerConfig bloodSuggerConfig;
	@Autowired
	private DeviceDetailDao deviceDetailDao;

	@Autowired
	private DeviceCategoryDao deviceCategoryDao;

	@Autowired
	private DeviceDao deviceDao;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private Integer aStart;

	/**
	 * 查询所有的设备类型
	 * @return
	 */
	public List<DeviceCategory> findAllCategory() {
		return deviceCategoryDao.findAll();
	}


	/**
	 * 查询设备信息
	 */
	public List<Device> findDeviceByCategory(String categoryCode) {
		return deviceDao.findByCategoryCode(categoryCode);
	}

	/**
	 * 获取设备信息
     */
	public Device findById(String id)
	{
		return deviceDao.findOne(Long.valueOf(id));
	}

	public DeviceResultModel pushData(String deviceData, String deviceType) throws Exception {

		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setDeviceData(deviceData);
		deviceInfo.setDeviceType(deviceType);
		deviceInfo.setPushDate(DateUtil.getSysDateTime());
		deviceInfo.setStatus("1");
		deviceInfoDao.save(deviceInfo);
		Map<String, String> paramMap = new HashMap<>();
		if (deviceType.equals("0")) {
			Map<String, String> dataMap = new HashMap<>();
			String[] dataArr = deviceData.split(",");
			String deviceCode =  StringUtil.replaceStrAll(dataArr[0], "imei=", "");
			String user =  StringUtil.replaceStrAll(dataArr[4], "user=", "");
			String sys =  StringUtil.replaceStrAll(dataArr[5], "sys=", "");
			String dia =  StringUtil.replaceStrAll(dataArr[6], "dia=", "");
			String pul =  StringUtil.replaceStrAll(dataArr[7], "pul=", "");
			String ano =  StringUtil.replaceStrAll(dataArr[8], "ano=", "");
			String time =  StringUtil.replaceStrAll(dataArr[9], "time=", "");
			dataMap.put("user", user);
			dataMap.put("sys", sys);
			dataMap.put("dia", dia);
			dataMap.put("pul", pul);
			dataMap.put("ano", ano);
			time = StringUtil.replaceStrAll(time, "\"", "");
			time = StringUtil.replaceStrAll(time, "/", " ");
			dataMap.put("time", time);
			paramMap.put("type", "2");
			paramMap.put("deviceSn", deviceCode);
			paramMap.put("data", JSONObject.fromObject(dataMap).toString());
		}
		if (deviceType.equals("1")) {
			aStart = 0;
			Map<String, String> dataMap = new HashMap<>();
			byte[] src = deviceData.getBytes();
			String str1 = getStringFromBuf(src, 2);//5A
			String str2 = getStringFromBuf(src, 2);//23
			String str3 = getStringFromBuf(src, 1);//0
			String str4 = getStringFromBuf(src, 2);//01
			String str5 = getStringFromBuf(src, 2);//09
			String str6 = getStringFromBuf(src, 2);//02
			String str7 = getStringFromBuf(src, 9);//B123456 	设备编号
			String str8 = getStringFromBuf(src, 3);//000
			String str9 = getStringFromBuf(src, 3);//000
			String str10 = getStringFromBuf(src, 3);//106   血糖数值
			String str11 = getStringFromBuf(src, 2);//12
			String str12 = getStringFromBuf(src, 2);//11
			String str13 = getStringFromBuf(src, 2);//10
			String str14 = getStringFromBuf(src, 2);//16
			String str15 = getStringFromBuf(src, 2);//25
			String str16 = getStringFromBuf(src, 4);//07BE
			paramMap.put("type", "1");
			paramMap.put("deviceSn", str7);
			DecimalFormat df = new DecimalFormat("0.0");
			dataMap.put("gi",  StringUtil.toString(df.format((float)Integer.parseInt(str10) / 18)));
			String time = "20"+str11+"-"+str12+"-"+str13+" "+str14+":"+str15+":00";
			dataMap.put("time", time);
			paramMap.put("data", JSONObject.fromObject(dataMap).toString());
		}

		try {
			String result = savePatientDeviceData(paramMap.get("data"), paramMap.get("type"), paramMap.get("deviceSn"));
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return DeviceResultModel.success("Device data incoming success");
	}

	public DeviceResultModel getData(String deviceCode) throws Exception {
		DeviceResultModel result = DeviceResultModel.success("Device data acquisition success");
		Map<String, Object> resultMap = new HashMap<>();
		Iterable<DeviceDetail> deviceDetailIterable;
		if (StringUtil.isEmpty(deviceCode)) {
			deviceDetailIterable = deviceDetailDao.findAll();
		} else {
			deviceDetailIterable = deviceDetailDao.findByCode(deviceCode);
		}
		List<DeviceDetail> deviceDetailList;
		deviceDetailList = IteratorUtils.toList(deviceDetailIterable.iterator());
		resultMap.put("deviceDetailList", deviceDetailList);
		result.setObjectMap(resultMap);
		return result;
	}

	/**
	 * 保存设备数据
	 */
	public String savePatientDeviceData(String data,String type,String deviceSn) throws Exception {
		String msg = "";
		Map<String,String> map = (Map<String,String>)objectMapper.readValue(data,Map.class);

		PatientHealthIndex obj = new PatientHealthIndex();
		obj.setCzrq(DateUtil.getSysDateTime());
		obj.setDeviceSn(deviceSn);
		obj.setDel("1");
		Date currentTime = DateUtil.getSysDateTime();
		Date time = currentTime;
		if (!StringUtil.isEmpty(map.get("time"))) {
			time = DateUtil.toTimestamp(map.get("time"), DateUtil.YYYY_MM_DD_HH_MM_SS);
			Date monthTime = DateUtil.addMonth(-1, currentTime);
			if (DateUtil.compareDate(time, monthTime) < 0) {
				time = currentTime;
			}
		}
		obj.setRecordDate(time);    //记录时间
		obj.setSortDate(time);      //排序时间

		String userType = "-1";
		if(map.containsKey("user")) { //存在身份标识 ,多用户
			userType = map.get("user");
		}
		//根据设备获取患者
		PatientDevice device = patientDeviceDao.findByDeviceSnAndCategoryCodeAndUserType(deviceSn,type,userType);
		if(device!=null) {
			obj.setUser(device.getUser());
			obj.setIdcard(device.getUserIdcard());
			// 1血糖 2血压 3体重 4腰围
			switch (type) {
				case "1":
					obj.setType(1);
					obj.setValue1(map.get("gi"));     //血糖值
					obj.setValue2(formatBloodSuger(DateUtil.toString(time, DateUtil.YYYY_MM_DD_HH_MM_SS)));
					break;
				case "2":
					obj.setType(2);
					obj.setValue1(map.get("sys"));     //收缩压
					obj.setValue2(map.get("dia"));     //舒张压
					obj.setValue3(map.get("pul"));     //脉搏
					obj.setValue4(map.get("ano"));     //有无心率不齐
					break;
				case "3":
					obj.setType(3);
					obj.setValue1(map.get("weight")); //体重
					break;
				case "4":
					obj.setType(4);
					obj.setValue1(map.get("waistline"));  //腰围
					break;
				default:
					msg =  "Can not support the metric!";
					break;
			}
		} else {
			msg = "This device is not relate patient!";
		}

		if(msg.length()==0) {
			patientHealthIndexDao.save(obj);
		}

		return msg;
	}

	public String getStringFromBuf(byte[] aBuf, Integer aLen) {
		byte[] byteTemp = new byte[aLen];
		System.arraycopy(aBuf, aStart, byteTemp, 0, aLen);

		aStart += aLen;

		return new String(byteTemp);
	}

	public String formatBloodSuger(String dateTime) {
		String[] fasting = bloodSuggerConfig.getFasting().split("-");
		String[] afterBreakfast = bloodSuggerConfig.getAfterBreakfast().split("-");
		String[] beforeLunch = bloodSuggerConfig.getBeforeLunch().split("-");
		String[] afterLunch = bloodSuggerConfig.getAfterLunch().split("-");
		String[] beforeDinner = bloodSuggerConfig.getBeforeDinner().split("-");
		String[] afterDinner = bloodSuggerConfig.getAfterDinner().split("-");
		String[] beforeSleep = bloodSuggerConfig.getBeforeSleep().split("-");
		if (isInArea(dateTime, fasting)) {
			return "1";//"空腹血糖"
		}
		if (isInArea(dateTime, afterBreakfast)) {
			return "2";//"早餐后血糖"
		}
		if (isInArea(dateTime, beforeLunch)) {
			return "3";//"午餐前血糖"
		}
		if (isInArea(dateTime, afterLunch)) {
			return "4";//"午餐后血糖"
		}
		if (isInArea(dateTime, beforeDinner)) {
			return "5";//"晚餐前血糖"
		}
		if (isInArea(dateTime, afterDinner)) {
			return "6";//"晚餐后血糖"
		}
		if (isInArea(dateTime, beforeSleep)) {
			return "7";//"睡前血糖"
		}

		return "1";//"空腹血糖"
	}

	public Boolean isInArea(String time, String[] timeArea) {
		String date = DateUtil.getDateFromDateTime(time);
		Long beginTime = DateUtil.compareDateTime(DateUtil.toTimestamp(time), DateUtil.toTimestamp(date + " " + timeArea[0] + ":00"));
		Long endTime = DateUtil.compareDateTime(DateUtil.toTimestamp(time), DateUtil.toTimestamp(date + " " + timeArea[1] + ":00"));
		if (beginTime > 0 && endTime < 0) {
			return true;
		} else {
			return false;
		}
	}
}
