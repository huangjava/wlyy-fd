package com.yihu.wlyy.services.health;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.daos.DevicePatientHealthIndexDao;
import com.yihu.wlyy.daos.PatientDao;
import com.yihu.wlyy.daos.PatientDeviceDao;
import com.yihu.wlyy.models.device.PatientDevice;
import com.yihu.wlyy.models.health.DevicePatientHealthIndex;
import com.yihu.wlyy.models.patient.PatientModel;
import com.yihu.wlyy.util.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;
import org.springside.modules.utils.Clock;

import java.util.*;

@Component
@Transactional(rollbackFor = Exception.class)
public class PatientHealthIndexService  {

	private Clock clock = Clock.DEFAULT;

	@Autowired
	private DevicePatientHealthIndexDao patientHealthIndexDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PatientDeviceDao patientDeviceDao;

	/**
	 * 保存患者健康指标  (旧)
	 */
	public DevicePatientHealthIndex save(DevicePatientHealthIndex model, int index, double value, String patientCode) {
		model.setCzrq(clock.getCurrentDate());
		model.setDel("1");
		// 当前值/收缩压，正数为高，负数为低
		double value1 = 0;
		// 上次值/舒张压，正数为高，负数为低
		double value2 = 0;
		if (model.getType() == 1) {
			if (index % 2 == 0) {
				// 餐后
				value1 = CommonUtil.checkHealthIndex(value, SystemConf.HEALTH_STANDARD_ST_MAX_AFTER,  SystemConf.HEALTH_STANDARD_ST_MIN_AFTER);
			} else {
				// 餐前
				value1 = CommonUtil.checkHealthIndex(value, SystemConf.HEALTH_STANDARD_ST_MAX_BEFORE, SystemConf.HEALTH_STANDARD_ST_MIN_BEFORE);
			}
			// 查询上一次的血糖值
			if (value1 != 0) {
				value2 = NumberUtils.toDouble(findPreValue(model.getUser(), model.getType(), index, model.getRecordDate()), 0);
			}
		} else if (model.getType() == 2) {
			// 血压记录，查询患者血压预警值
			// 收缩压
			value1 = CommonUtil.checkHealthIndex(NumberUtils.toDouble(model.getValue1(), 0), SystemConf.HEALTH_STANDARD_SSY_MAX, SystemConf.HEALTH_STANDARD_SSY_MIN);
			// 舒张压
			value2 = CommonUtil.checkHealthIndex(NumberUtils.toDouble(model.getValue2(), 0), SystemConf.HEALTH_STANDARD_SZY_MAX, SystemConf.HEALTH_STANDARD_SZY_MIN);
			if (value1 > 0 || value2 > 0) {
				value1 = NumberUtils.toDouble(model.getValue1(), 0);
				value2 = NumberUtils.toDouble(model.getValue2(), 0);
			} else if (value1 < 0 || value2 < 0) {
				value1 = -NumberUtils.toDouble(model.getValue1(), 0);
				value2 = -NumberUtils.toDouble(model.getValue2(), 0);
			}
		}
		// 保存到数据库
		model = patientHealthIndexDao.save(model);
		return model;
	}

	/**
	 * 判断当前值是否在区间内
	 */
	private boolean checkHealthIndex(Double current,Double max,Double min)
	{
		if(current>max || current<min || current<0)
		{
			return false;
		}
		 return true;
	}



	/**
	 *新增患者指标
	 * @return
     */
	public DevicePatientHealthIndex addPatientHealthIndex(String data,String type,String patientCode,String deviceSn) throws Exception
	{
		Map<String,String> map = (Map<String,String>)objectMapper.readValue(data,Map.class);
		Map<String,Object> params = new HashMap<>();
		DevicePatientHealthIndex obj = new DevicePatientHealthIndex();
		Date currentTime = new Date();
		obj.setCzrq(currentTime);
		obj.setDel("1");
		Date time = currentTime;
		if (map.containsKey("time")) {
			time = DateUtil.strToDateLong(map.get("time"));
		}
		obj.setRecordDate(time);    //记录时间
		obj.setSortDate(time);      //排序时间

		String idcard = "";
		if(deviceSn!=null && deviceSn.length()>0)   //设备数据
		{
			obj.setDeviceSn(deviceSn);
			String userType = "-1";
			if(map.containsKey("user")) { //存在身份标识 ,多用户
				userType = map.get("user");
			}
			//根据设备获取患者
			PatientDevice device = patientDeviceDao.findByDeviceSnAndCategoryCodeAndUserType(deviceSn,type,userType);
			if(device!=null)
			{
				patientCode = device.getUser();
			}
		}

		//身份证不为空
		if(StringUtil.isNotEmpty(idcard)) {
			obj.setUser(patientCode);
			obj.setIdcard(idcard);

			String msgContent = "";
			// 1血糖 2血压 3体重 4腰围
			switch (type) {
				case "1": {
					obj.setType(1);
					String value1 = map.get("gi");  //血糖值
					String value2 = map.get("gi_type");  //血糖值类型
					obj.setValue1(value1);
					obj.setValue2(value2);
					params.put("bloodsugarType",value1);
					params.put("bloodsugar",value2);
					break;
				}
				case "2":{
					obj.setType(2);
					String value1 = map.get("sys");  //收缩压
					String value2 = map.get("dia");  //舒张压
					obj.setValue1(value1);
					obj.setValue2(value2);
					obj.setValue3(map.get("pul"));     //脉搏
					obj.setValue4(map.get("ano"));     //有无心率不齐
					params.put("lowPressure",value1);
					params.put("highPressure",value2);
					break;
				}
				case "3":
				{
					obj.setType(3);
					obj.setValue1(map.get("weight")); //体重
					params.put("weight",map.get("weight"));
					break;
				}
				case "4":{
					obj.setType(4);
					obj.setValue1(map.get("waistline"));  //腰围
					params.put("waistline",map.get("waistline"));
					break;
				}
				default: {
					throw new Exception("暂不支持该指标！");
				}
			}

			params.put("monitorType",obj.getType());
			params.put("monitorTime",obj.getRecordDate());
//			params.put("uId",obj.getIdcard());
			//将数据发送到公司
			HttpClientUtil.doPost(SystemConf.getInstance().getHealthPutUrl(),params,"","");
			patientHealthIndexDao.save(obj);
		} else{
			throw new Exception("不存在该患者！");
		}

		return obj;
	}


	/**
	 * 按录入时间和患者标识查询健康记录
	 * @param patientCode
	 * @param date
	 * @return
	 */
	public Iterable<DevicePatientHealthIndex> findByPatienDate(String patientCode, int type, Date date) {
		return patientHealthIndexDao.findByPatienDate(patientCode, type, date);
	}

	/**
	 * 按时间段查询患者健康指标
	 * @param patientCode 患者指标
	 * @param type 健康指标类型（1血糖，2血压，3体重，4腰围）
	 * @param begin 开始时间
	 * @param end 结束时间
	 * @return
	 */
	public Iterable<DevicePatientHealthIndex> findChartByPatien(String patientCode, int type, String begin, String end) {
		// 排序
		return patientHealthIndexDao.findByPatient(patientCode, type, DateUtil.strToDate(begin, DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.strToDate(end, DateUtil.YYYY_MM_DD_HH_MM_SS));
	}

	/**
	 * 根据患者标志获取健康指标
	 * @param patientCode 患者标志
	 * @param pageSize 页数
	 * @return 健康指标列表
	 */
	public Page<DevicePatientHealthIndex> findByPatien(String patientCode, int type, Date sortDate, int pageSize) {
		if (pageSize <= 0) {
			pageSize = 10;
		}
		// 排序
		Sort sort = new Sort(Direction.DESC, "sortDate");
		// 分页信息
		PageRequest pageRequest = new PageRequest(0, pageSize, sort);

		// 设置查询条件
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		// 患者标志
		filters.put("user", new SearchFilter("user", Operator.EQ, patientCode));
		if (sortDate != null) {
			filters.put("sortDate", new SearchFilter("sortDate", Operator.LT, sortDate));
		}
		filters.put("type", new SearchFilter("type", Operator.EQ, type));
		// 未作废
		filters.put("del", new SearchFilter("del", Operator.EQ, "1"));
		Specification<DevicePatientHealthIndex> spec = DynamicSpecifications.bySearchFilter(filters.values(), DevicePatientHealthIndex.class);

		return patientHealthIndexDao.findAll(spec, pageRequest);
	}

	/**
	 * 查询指标记录
	 *
	 * @param patient
	 * @param type
	 * @param start
	 * @param end
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<DevicePatientHealthIndex> findIndexByPatient(String patient, int type, String start,String end,int page, int pageSize){
		List<DevicePatientHealthIndex> re = new ArrayList<>();
		if(page > 0) {
			page = page -1;
		}
		PageRequest pageRequest = new PageRequest(page,pageSize);
		Date startDate =  DateUtil.strToDate(start, DateUtil.YYYY_MM_DD_HH_MM_SS);
		Date endDate = DateUtil.strToDate(end, DateUtil.YYYY_MM_DD_HH_MM_SS);
		if(type == 1)   //血糖特殊处理
		{
			//根据时间过滤排序
			List<String>  dateList = patientHealthIndexDao.findDateList(patient,startDate,endDate,pageRequest);
			if(dateList!=null && dateList.size()>0)
			{
				for(String dateString : dateList)
				{
					DevicePatientHealthIndex obj = new DevicePatientHealthIndex();
					boolean hadData = false;
					Date date = DateUtil.strToDateShort(dateString);
					/***************** 按时间排序 ***************************/
					List<DevicePatientHealthIndex> list = patientHealthIndexDao.findByDate(patient,dateString);
					if(list!=null && list.size()>0)
					{
						obj.setType(type);
						obj.setCzrq(date);
						obj.setRecordDate(date);
						obj.setSortDate(date);
						for(DevicePatientHealthIndex item:list)
						{
							String data = item.getValue1();
							String dataType = item.getValue2();
							if(data!=null && dataType!=null) {
								if (dataType.equals("1")) {
									obj.setValue1(data);
									hadData = true;
								}
								else if(dataType.equals("2")) {
									obj.setValue2(data);
									hadData = true;
								}
								else if(dataType.equals("3")) {
									obj.setValue3(data);
									hadData = true;
								}
								else if(dataType.equals("4")) {
									obj.setValue4(data);
									hadData = true;
								}
								else if(dataType.equals("5")) {
									obj.setValue5(data);
									hadData = true;
								}
								else if(dataType.equals("6")) {
									obj.setValue6(data);
									hadData = true;
								}
								else if(dataType.equals("7")) {
									obj.setValue7(data);
									hadData = true;
								}
							}
						}
					}
					if(hadData)
					{
						re.add(obj);
					}
				}
			}
		}
		else{
			Page<DevicePatientHealthIndex> list= patientHealthIndexDao.findIndexByPatient(patient,type,startDate,endDate,pageRequest);
			re = list.getContent();
		}
		return re;
	}

	/**
	 * 根据患者标志获取健康指标
	 * @param patientCode 患者标志
	 * @param pageSize 页数
	 * @return 健康指标列表
	 */
	public Page<DevicePatientHealthIndex> findByPatien(String patientCode, int type, Date sortDate, Date begin, Date end, int pageSize) {
		if (pageSize <= 0) {
			pageSize = 10;
		}
		// 排序
		Sort sort = new Sort(Direction.DESC, "sortDate");
		// 分页信息
		PageRequest pageRequest = new PageRequest(0, pageSize, sort);

		// 设置查询条件
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();
		// 患者标志
		filters.put("user", new SearchFilter("user", Operator.EQ, patientCode));
		if (sortDate != null) {
			filters.put("sortDate", new SearchFilter("sortDate", Operator.LT, sortDate));
		}
		filters.put("recordDate1", new SearchFilter("recordDate", Operator.GTE, begin));
		filters.put("recordDate2", new SearchFilter("recordDate", Operator.LTE, end));
		filters.put("type", new SearchFilter("type", Operator.EQ, type));
		// 未作废
		filters.put("del", new SearchFilter("del", Operator.EQ, "1"));
		Specification<DevicePatientHealthIndex> spec = DynamicSpecifications.bySearchFilter(filters.values(), DevicePatientHealthIndex.class);

		return patientHealthIndexDao.findAll(spec, pageRequest);
	}


	/**
	 * 查询患者最近填写的运动、用药、饮食内容
	 * @param patient
	 * @return
	 */
	public JSONArray findRecentByPatient(String patient) {
		JSONArray array = new JSONArray();
		Iterable<DevicePatientHealthIndex> iterable = patientHealthIndexDao.findRecentByPatient(patient);
		if (iterable != null) {
			Iterator<DevicePatientHealthIndex> iterator = iterable.iterator();
			while (iterator != null && iterator.hasNext()) {
				DevicePatientHealthIndex phi = iterator.next();
				if (phi == null) {
					continue;
				}
				JSONObject json = new JSONObject();
				// 设置健康指标类型（1血糖，2血压，3体重，4腰围）
				json.put("type", phi.getType());
				// 设置血糖/收缩压/体重/腰围/早餐前空腹
				json.put("value1", phi.getValue1());
				// 设置舒张压/早餐后血糖
				json.put("value2", phi.getValue2());
				// 设置午餐前血糖
				json.put("value3", phi.getValue3());
				// 设置午餐后血糖
				json.put("value4", phi.getValue4());
				// 设置晚餐前血糖
				json.put("value5", phi.getValue5());
				// 设置晚餐后血糖
				json.put("value6", phi.getValue6());
				// 设置睡前血糖
				json.put("value7", phi.getValue7());
				json.put("date", DateUtil.dateToStrShort(phi.getRecordDate()));
				array.put(json);
			}
		}
		return array;
	}

	/**
	 * 查询上一次的值
	 * @param patient 患者标识
	 * @param type 类型：1血糖，2血压
	 * @param index 第几个值，1~7
	 * @return
	 */
	private String findPreValue(String patient, int type, int index, Date recordDate) {
		// 分页信息
		PageRequest pageRequest = new PageRequest(0, 1);
		Page<String> page = null;
		switch (index) {
		case 1:
			page = patientHealthIndexDao.findValue1ByPatient(patient, type, recordDate, pageRequest);
			break;
		case 2:
			page = patientHealthIndexDao.findValue2ByPatient(patient, type, recordDate, pageRequest);
			break;
		case 3:
			page = patientHealthIndexDao.findValue3ByPatient(patient, type, recordDate, pageRequest);
			break;
		case 4:
			page = patientHealthIndexDao.findValue4ByPatient(patient, type, recordDate, pageRequest);
			break;
		case 5:
			page = patientHealthIndexDao.findValue5ByPatient(patient, type, recordDate, pageRequest);
			break;
		case 6:
			page = patientHealthIndexDao.findValue6ByPatient(patient, type, recordDate, pageRequest);
			break;
		case 7:
			page = patientHealthIndexDao.findValue7ByPatient(patient, type, recordDate, pageRequest);
			break;
		}
		if (page != null && page.getNumberOfElements() > 0) {
			return page.getContent().get(0);
		}
		return "0";
	}


	/**
	 * 根据患者标志获取健康指标
	 * @param patientCode 患者标志
	 * @return 健康指标列表
	 */
	public List<Map<String, Object>> findLastByPatien(String patientCode) {
		String sql = "SELECT * FROM " +
				"(SELECT * FROM wlyy_patient_health_index where user='"+ patientCode +"' and type in(1,2)  ORDER BY czrq DESC) b " +
				"GROUP BY type";

		return jdbcTemplate.queryForList(sql);
	}

}
