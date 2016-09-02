package com.yihu.wlyy.web.doctor.health;

import com.yihu.wlyy.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping(value = "/doctor/health_index")
public class DoctorHealthController extends BaseController {


	/**
	 * 患者最近填写的健康指标
	 * @param patient 患者标识
	 * @return
	 */
	@RequestMapping(value = "recent")
	@ResponseBody
	public String recent(String patient) {
		try {
				return error(-1, "查询失败");
		} catch (Exception e) {
			error(e);
			return error(-1, "查询失败");
		}
	}

	/**
	 * 根据患者标志获取健康指标
	 * @param patient 患者标识
	 * @param type 健康指标类型（1血糖，2血压，3体重，4腰围）
	 * @return 操作结果
	 */
	@RequestMapping(value = "chart")
	@ResponseBody
	public String getHealthIndexChartByPatient(String patient, int type, String begin, String end) {
		try {
			return write(200, "查询成功", "list", "");
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "查询失败！");
		}
	}

	/**
	 * 根据患者标志获取健康指标
	 * @param patient 患者指标
	 * @param type 健康指标类型（1血糖，2血压，3体重，4腰围）
	 * @return 操作结果
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public String getHealthIndexByPatient(String patient, int type, @RequestParam(required = false) String sortDate, String begin, String end, int pagesize) {
		try {

			return write(200, "查询成功", "list", "");
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "查询失败！");
		}
	}

	/**
	 * 患者健康指标预警值查询
	 * @param patient 患者标识
	 * @return
	 */
	@RequestMapping(value = "standard")
	@ResponseBody
	public String standard(String patient) {
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


	/**
	 * 根据患者标志获取健康指标
	 * @param patient 患者指标
	 * @return 操作结果
	 */
	@RequestMapping(value = "last")
	@ResponseBody
	public String getHealthIndexByPatient(String patient) {
		try {
			return write(200, "查询成功", "data", "");
		} catch (Exception ex) {
			error(ex);
			return invalidUserException(ex, -1, "查询失败！");
		}
	}

}
