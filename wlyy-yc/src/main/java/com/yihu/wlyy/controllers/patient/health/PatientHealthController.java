package com.yihu.wlyy.controllers.patient.health;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.models.health.DevicePatientHealthIndex;
import com.yihu.wlyy.services.health.PatientHealthIndexService;
import com.yihu.wlyy.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/patient/health_index")
public class PatientHealthController extends BaseController {

	@Autowired
	private PatientHealthIndexService healthIndexService;

	/**
	 * 患者最近填写的健康指标
	 * @return
	 */
	@RequestMapping(value = "recent", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "患者最近填写的健康指标", produces = "application/json", notes = "患者最近填写的健康指标")
	public String recent(
			@ApiParam(name = "patient", value = "患者Code", required = true)
			@RequestParam(value = "patient") String patient	) {
		try {
//			String user = getUID();
			String user = "CS20160830001";

			JSONArray array = healthIndexService.findRecentByPatient(user);
			if (array != null) {
				return write(200, "查询成功", "list", array);
			} else {
				return error(-1, "查询失败");
			}
		} catch (Exception e) {
			error(e);
			return error(-1, "查询失败");
		}
	}

	/**
	 * 保存患者健康指标 (旧)
	 * @param intervene 干预标志
	 * @param time 记录时间
	 * @param value1 血糖/收缩压/体重/腰围/早餐前空腹
	 * @param value2 舒张压/早餐后空腹
	 * @param value3 午餐空腹
	 * @param value4 午餐后
	 * @param value5 晚餐空腹
	 * @param value6 晚餐后
	 * @param value7 睡前
	 * @param type 健康指标类型（1血糖，2血压，3体重，4腰围）
	 * @return 操作结果
	 */
	@RequestMapping(value = "add", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "保存患者健康指标 (旧)（手输）", produces = "application/json", notes = "保存患者健康指标 (旧)（手输）")
	public String add(
			@ApiParam(name = "intervene", value = "干预标志", required = false)
			@RequestParam(required = false) String intervene,
			@ApiParam(name = "time", value = "记录时间", required = true)
			String time,
			@ApiParam(name = "value1", value = " 血糖/收缩压/体重/腰围/早餐前空腹", required = true)
			String value1,
			@ApiParam(name = "value2", value = "舒张压/早餐后空腹", required = true)
			String value2,
			@ApiParam(name = "value3", value = "午餐空腹", required = true)
			String value3,
			@ApiParam(name = "value4", value = "午餐后", required = true)
			String value4,
			@ApiParam(name = "value5", value = "晚餐空腹", required = true)
			String value5,
			@ApiParam(name = "value6", value = "晚餐后", required = true)
			String value6,
			@ApiParam(name = "value7", value = "睡前", required = true)
			String value7,
			@ApiParam(name = "type", value = "健康指标类型（1血糖，2血压，3体重，4腰围）", required = true)
			int type) {
		try {

//			String user = getUID();
			String user = "CS20160830001";
			DevicePatientHealthIndex healthIndex = null;
			if (type == 1) {
				// 血糖等一天只能存在一条数据
				Iterable<DevicePatientHealthIndex> list = healthIndexService.findByPatienDate(user, type, DateUtil.strToDate(time, DateUtil.YYYY_MM_DD));
				if (list != null) {
					for (DevicePatientHealthIndex model : list) {
						healthIndex = model;
					}
				}
			}
			if (healthIndex == null) {
				healthIndex = new DevicePatientHealthIndex();
			}
			// 设置患者标识
			healthIndex.setUser(user);
			// 设置干预标识，默认为NULL
			healthIndex.setIntervene(intervene);

			int index = 0;
			double value = 0;

			// 设置血糖/收缩压/体重/腰围/早餐前空腹
			if (NumberUtils.toDouble(value1, 0) > 0) {
				healthIndex.setValue1(value1);
				index = 1;
				value = NumberUtils.toDouble(value1, 0);
			}
			// 设置 舒张压/早餐后血糖
			if (NumberUtils.toDouble(value2, 0) > 0) {
				healthIndex.setValue2(value2);
				index = 2;
				value = NumberUtils.toDouble(value2, 0);
			}
			// 设置午餐前血糖
			if (NumberUtils.toDouble(value3, 0) > 0) {
				healthIndex.setValue3(value3);
				index = 3;
				value = NumberUtils.toDouble(value3, 0);
			}
			// 设置午餐后血糖
			if (NumberUtils.toDouble(value4, 0) > 0) {
				healthIndex.setValue4(value4);
				index = 4;
				value = NumberUtils.toDouble(value4, 0);
			}
			// 设置晚餐前血糖
			if (NumberUtils.toDouble(value5, 0) > 0) {
				healthIndex.setValue5(value5);
				index = 5;
				value = NumberUtils.toDouble(value5, 0);
			}
			// 设置晚餐后血糖
			if (NumberUtils.toDouble(value6, 0) > 0) {
				healthIndex.setValue6(value6);
				index = 6;
				value = NumberUtils.toDouble(value6, 0);
			}
			// 设置睡前血糖
			if (NumberUtils.toDouble(value7, 0) > 0) {
				healthIndex.setValue7(value7);
				index = 7;
				value = NumberUtils.toDouble(value7, 0);
			}
			// 设置健康指标类型（1血糖，2血压，3体重，4腰围）
			healthIndex.setType(type);
			// 设置记录时间
			if(type == 2){
				healthIndex.setRecordDate(DateUtil.strToDate(time, DateUtil.YYYY_MM_DD_HH_MM_SS));
			}else {
				healthIndex.setRecordDate(DateUtil.strToDate(time, DateUtil.YYYY_MM_DD));
			}
			healthIndex.setSortDate(DateUtil.strToDateAppendNowTime(time, DateUtil.YYYY_MM_DD_HH_MM_SS));
			// 保存到数据库
			healthIndex = healthIndexService.save(healthIndex, index, value,user);
			if (healthIndex == null) {
				return error(-1, "保存失败！");
			}
			return success("保存成功！");
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "保存失败！");
		}
	}

	@RequestMapping(value = "addPatientHealthIndex",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "保存指标（设备）", produces = "application/json", notes = "保存指标（设备）")
	public String addPatientHealthIndex(
			@ApiParam(name = "data", value = "指标数据", required = true)
			@RequestParam(value="data",required = true) String data,
			@ApiParam(name = "type", value = "设备类型", required = true)
			@RequestParam(value="type",required = true) String type)
	{
		try {
//			String user = getUID();
			String user = "CS20160830001";
			DevicePatientHealthIndex obj = healthIndexService.addPatientHealthIndex(data, type,user, null);
			return success("新增患者指标成功！");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return invalidUserException(ex, -1, ex.getMessage());
		}
	}

	/**
	 * 根据患者标志获取健康指标
	 * @param type 健康指标类型（1血糖，2血压，3体重，4腰围）
	 * @return 操作结果
	 */
	@RequestMapping(value = "chart", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据患者标志获取健康指标列表（折线图数据）", produces = "application/json", notes = "根据患者标志获取健康指标列表")
	public String getHealthIndexChartByPatient(
			@ApiParam(name = "type", value = "健康指标类型（1血糖，2血压，3体重，4腰围）", required = true)
			int type,
			@ApiParam(name = "begin", value = "开始时间", required = true)
			String begin,
			@ApiParam(name = "end", value = "结束时间", required = true)
			String end) {
//		try {
//			//TODO  demo数据
//			String demo = null;
//			if (type==1){
//				demo = "[{\"date\":\"2016-09-05\",\"value1\":\"3\",\"czrq\":\"2016-09-05 00:00:00\",\"type\":1},{\"date\":\"2016-09-06\",\"value2\":\"11\",\"value1\":\"55\",\"czrq\":\"2016-09-06 00:00:00\",\"type\":1},{\"date\":\"2016-09-07\",\"value2\":\"46\",\"value1\":\"34\",\"czrq\":\"2016-09-07 00:00:00\",\"type\":1}]";
//			}else if (type==2){
//				demo = "[{\"date\":\"2016-09-07 10:58:00\",\"value2\":\"97\",\"value1\":\"66\",\"patient\":\"CS20160830001\",\"czrq\":\"2016-09-07 16:58:36\",\"type\":2},{\"date\":\"2016-09-07 16:57:00\",\"value2\":\"22\",\"value1\":\"22\",\"patient\":\"CS20160830001\",\"czrq\":\"2016-09-07 16:57:29\",\"type\":2},{\"date\":\"2016-09-07 16:57:00\",\"value2\":\"63\",\"value1\":\"55\",\"patient\":\"CS20160830001\",\"czrq\":\"2016-09-07 16:57:43\",\"type\":2},{\"date\":\"2016-09-07 16:58:00\",\"value2\":\"78\",\"value1\":\"36\",\"patient\":\"CS20160830001\",\"czrq\":\"2016-09-07 16:58:00\",\"type\":2},{\"date\":\"2016-09-07 16:58:00\",\"value2\":\"95\",\"value1\":\"90\",\"patient\":\"CS20160830001\",\"czrq\":\"2016-09-07 16:58:14\",\"type\":2}]";
//			}
//			List list =  objectMapper.readValue(demo,List.class);
//			return write(200, "查询成功", "list", list);
//		} catch (Exception ex) {
//			error(ex);
//			return invalidUserException(ex, -1, "查询失败！");
//		}

//		tring user = getUID();
		String user = "CS20160830001";

		try {
			Iterable<DevicePatientHealthIndex> list = healthIndexService.findChartByPatien(user, type, begin, end);
			if (list == null) {
				return success("查询成功!");
			}
			JSONArray jsonArray = new JSONArray();
			for (DevicePatientHealthIndex model : list) {
				JSONObject modelJson = new JSONObject();
				modelJson.put("patient", model.getUser());
				modelJson.put("value1", model.getValue1());
				modelJson.put("value2", model.getValue2());
				modelJson.put("value3", model.getValue3());
				modelJson.put("value4", model.getValue4());
				modelJson.put("value5", model.getValue5());
				modelJson.put("value6", model.getValue6());
				modelJson.put("value7", model.getValue7());
				modelJson.put("type", model.getType());
				if(type == 2){
					modelJson.put("date", DateUtil.dateToStr(model.getRecordDate(), DateUtil.YYYY_MM_DD_HH_MM_SS));
				}else{
					modelJson.put("date", DateUtil.dateToStr(model.getRecordDate(), DateUtil.YYYY_MM_DD));
				}
				modelJson.put("czrq", DateUtil.dateToStr(model.getCzrq(), DateUtil.YYYY_MM_DD_HH_MM_SS));
				jsonArray.put(modelJson);
			}
			return write(200, "查询成功", "list", jsonArray);
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "查询失败！");
		}

	}

	/**
	 * 根据患者标志获取健康指标
	 * @param type 健康指标类型（1血糖，2血压，3体重，4腰围）
	 * @param pagesize 页数
	 * @return 操作结果
	 */
	@RequestMapping(value = "list", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "根据患者标志获取健康指标", produces = "application/json", notes = "根据患者标志获取健康指标")
	public String getHealthIndexByPatient(
			@ApiParam(name = "type", value = "健康指标类型（1血糖，2血压，3体重，4腰围）", required = true)
			@RequestParam(value="type",required = true) int type,
			@ApiParam(name = "start", value = "开始时间", required = true)
			@RequestParam(value="start",required = true) String start,
			@ApiParam(name = "end", value = "结束时间", required = true)
			@RequestParam(value="end",required = true) String end,
			@ApiParam(name = "page", value = "当前页", required = true)
			@RequestParam(value="page",required = true) int page,
			@ApiParam(name = "pagesize", value = "页数", required = true)
			@RequestParam(value="pagesize",required = true) int pagesize) {
//		try {
//			//TODO  demo数据
//			String demo = null;
//			if (type==1){
//				demo = "[{\"date\":\"2016-09-07\",\"value2\":\"46\",\"value1\":\"34\",\"czrq\":\"2016-09-07 00:00:00\",\"sortDate\":\"2016-09-07 00:00:00\",\"type\":1},{\"date\":\"2016-09-06\",\"value2\":\"11\",\"value1\":\"55\",\"czrq\":\"2016-09-06 00:00:00\",\"sortDate\":\"2016-09-06 00:00:00\",\"type\":1},{\"date\":\"2016-09-05\",\"value1\":\"3\",\"czrq\":\"2016-09-05 00:00:00\",\"sortDate\":\"2016-09-05 00:00:00\",\"type\":1}]";
//			}else if (type==2){
//				demo = "[{\"date\":\"2016-09-07 16:58:00\",\"value2\":\"95\",\"value1\":\"90\",\"patient\":\"CS20160830001\",\"czrq\":\"2016-09-07 16:58:14\",\"sortDate\":\"2016-09-07 16:58:00\",\"id\":3367,\"type\":2},{\"date\":\"2016-09-07 16:58:00\",\"value2\":\"78\",\"value1\":\"36\",\"patient\":\"CS20160830001\",\"czrq\":\"2016-09-07 16:58:00\",\"sortDate\":\"2016-09-07 16:58:00\",\"id\":3366,\"type\":2},{\"date\":\"2016-09-07 16:57:00\",\"value2\":\"63\",\"value1\":\"55\",\"patient\":\"CS20160830001\",\"czrq\":\"2016-09-07 16:57:43\",\"sortDate\":\"2016-09-07 16:57:00\",\"id\":3365,\"type\":2},{\"date\":\"2016-09-07 16:57:00\",\"value2\":\"22\",\"value1\":\"22\",\"patient\":\"CS20160830001\",\"czrq\":\"2016-09-07 16:57:29\",\"sortDate\":\"2016-09-07 16:57:00\",\"id\":3364,\"type\":2},{\"date\":\"2016-09-07 10:58:00\",\"value2\":\"97\",\"value1\":\"66\",\"patient\":\"CS20160830001\",\"czrq\":\"2016-09-07 16:58:36\",\"sortDate\":\"2016-09-07 10:58:00\",\"id\":3368,\"type\":2}]";
//			}
//			List list =  objectMapper.readValue(demo,List.class);
//
//			return write(200, "查询成功", "list", list);
//		} catch (Exception ex) {
//			error(ex);
//			return invalidUserException(ex, -1, "查询失败！");
//		}

//		String user = getUID();
		String user = "CS20160830001";
		try {
			List<DevicePatientHealthIndex> list = healthIndexService.findIndexByPatient(user, type, start,end,page, pagesize);

			JSONArray jsonArray = new JSONArray();
			if (list != null) {
				for (DevicePatientHealthIndex model : list) {
					JSONObject modelJson = new JSONObject();
					modelJson.put("id", model.getId());
					modelJson.put("patient", model.getUser());
					modelJson.put("value1", model.getValue1());
					modelJson.put("value2", model.getValue2());
					modelJson.put("value3", model.getValue3());
					modelJson.put("value4", model.getValue4());
					modelJson.put("value5", model.getValue5());
					modelJson.put("value6", model.getValue6());
					modelJson.put("value7", model.getValue7());
					modelJson.put("type", model.getType());
					if(type == 2) {
						modelJson.put("date", DateUtil.dateToStr(model.getRecordDate(), DateUtil.YYYY_MM_DD_HH_MM_SS));
					}else{
						modelJson.put("date", DateUtil.dateToStr(model.getRecordDate(), DateUtil.YYYY_MM_DD));
					}
					modelJson.put("sortDate", DateUtil.dateToStrLong(model.getSortDate()));
					modelJson.put("czrq", DateUtil.dateToStr(model.getCzrq(), DateUtil.YYYY_MM_DD_HH_MM_SS));
					jsonArray.put(modelJson);
				}
			}

			return write(200, "查询成功", "list", jsonArray);
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "查询失败！");
		}

	}

	/**
	 * 患者健康指标预警值查询
	 * @return
	 */
	@RequestMapping(value = "standard", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "患者健康指标预警值查询", produces = "application/json", notes = "患者健康指标预警值查询")
	public String standard(
			@ApiParam(name = "patient", value = "患者Code", required = true)
			@RequestParam(value = "patient") String patient	) {
		try {
			return write(200, "查询成功", "data", "");
		} catch (Exception e) {
			error(e);
			return invalidUserException(e, -1, "查询失败！");
		}
	}

	/**
	 * 保存患者健康指标预警值
	 * @param patient 患者标识
	 * @param json 预警值
	 * @return
	 */
	@RequestMapping(value = "standard_save", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "保存患者健康指标预警值", produces = "application/json", notes = "保存患者健康指标预警值")
	public String standardSave(
			@ApiParam(name = "patient", value = "患者Code", required = true)
			String patient,
			@ApiParam(name = "json", value = "预警值", required = true)
			String json) {
		try {
			return write(200, "保存成功");
		} catch (Exception e) {
			error(e);
			return invalidUserException(e, -1, "保存失败！");
		}
	}


}
