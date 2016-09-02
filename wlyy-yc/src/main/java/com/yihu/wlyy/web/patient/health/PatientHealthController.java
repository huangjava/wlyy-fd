package com.yihu.wlyy.web.patient.health;

import com.yihu.wlyy.web.BaseController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value = "/patient/health_index")
public class PatientHealthController extends BaseController {

	/**
	 * 患者最近填写的健康指标
	 * @return
	 */
	@RequestMapping(value = "recent")
	@ResponseBody
	public String recent() {
		try {
				return write(200, "查询成功", "list", "");
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
	@RequestMapping(value = "add")
	@ResponseBody
	public String add(@RequestParam(required = false) String intervene, String time, String value1, String value2, String value3, String value4, String value5, String value6, String value7, int type) {
		try {
			return success("保存成功！");
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "保存失败！");
		}
	}

	@RequestMapping(value = "addPatientHealthIndex",method = RequestMethod.POST)
	@ResponseBody
	public String addPatientHealthIndex(
										@RequestParam(value="data",required = true) String data,
										@RequestParam(value="type",required = true) String type)
	{
		try {
			return success("新增患者指标成功！");
		}
		catch (Exception ex)
		{
			return invalidUserException(ex, -1, ex.getMessage());
		}
	}

	/**
	 * 根据患者标志获取健康指标
	 * @param type 健康指标类型（1血糖，2血压，3体重，4腰围）
	 * @return 操作结果
	 */
	@RequestMapping(value = "chart")
	@ResponseBody
	public String getHealthIndexChartByPatient(int type, String begin, String end) {
		try {
			return write(200, "查询成功", "list", "");
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
	@RequestMapping(value = "list",method = RequestMethod.POST)
	@ResponseBody
	public String getHealthIndexByPatient(
										   @RequestParam(value="type",required = true) int type,
										  @RequestParam(value="start",required = true) String start,
										   @RequestParam(value="end",required = true) String end,
										   @RequestParam(value="page",required = true) int page,
										  @RequestParam(value="pagesize",required = true) int pagesize) {
		try {
			return write(200, "查询成功", "list", "");
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "查询失败！");
		}
	}

	/**
	 * 患者健康指标预警值查询
	 * @return
	 */
	@RequestMapping(value = "standard")
	@ResponseBody
	public String standard() {
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
	@RequestMapping(value = "standard_save")
	@ResponseBody
	public String standardSave(String patient, String json) {
		try {
			return write(200, "保存成功");
		} catch (Exception e) {
			error(e);
			return invalidUserException(e, -1, "保存失败！");
		}
	}


}
