package com.yihu.wlyy.controllers.patient.health;

import com.yihu.wlyy.controllers.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/patient/health_index")
public class PatientHealthController extends BaseController {

	/**
	 * 患者最近填写的健康指标
	 * @return
	 */
	@RequestMapping(value = "recent", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "患者最近填写的健康指标", produces = "application/json", notes = "患者最近填写的健康指标")
	public String recent(
			@ApiParam(name = "patient", value = "患者Code", required = true)
			@RequestParam(value = "patient") String patient	) {
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
	@RequestMapping(value = "chart", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "根据患者标志获取健康指标列表（折线图数据）", produces = "application/json", notes = "根据患者标志获取健康指标列表")
	public String getHealthIndexChartByPatient(
			@ApiParam(name = "type", value = "健康指标类型（1血糖，2血压，3体重，4腰围）", required = true)
			int type,
			@ApiParam(name = "begin", value = "开始时间", required = true)
			String begin,
			@ApiParam(name = "end", value = "结束时间", required = true)
			String end) {
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
	@RequestMapping(value = "standard", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
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
