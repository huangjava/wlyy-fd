package com.yihu.wlyy.controllers.doctor.health;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.util.HttpClientUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/doctor/health_index")
public class DoctorHealthController extends BaseController {

	@Value("${service-gateway.username}")
	private String username;
	@Value("${service-gateway.password}")
	private String password;
	@Value("${service-gateway.url}")
	private String comUrl;

	//TODO 根据患者标志获取健康指标（原有接口）
	@ApiOperation("根据患者标志获取健康指标")
	@RequestMapping(value = "list",method = RequestMethod.GET)
	@ResponseBody
	public String getHealthIndexByPatient(
			@ApiParam(name = "patient", value = "患者唯一标识", defaultValue = " ")
			@RequestParam(value = "patient", required = false) String patient,
			@ApiParam(name = "type", value = "健康指标类型（1血糖，2血压，3体重，4腰围）", defaultValue = "1")
			@RequestParam(value = "type", required = false) int type,
			@ApiParam(name = "sortDate", value = "排序字段", defaultValue = "")
			@RequestParam(value = "sortDate", required = false) String sortDate,
			@ApiParam(name = "begin", value = " ", defaultValue = "")
			@RequestParam(value = "begin", required = false) String begin,
			@ApiParam(name = "end", value = " ", defaultValue = "")
			@RequestParam(value = "end", required = false) String end,
			@ApiParam(name = "pagesize", value = " ", defaultValue = "")
			@RequestParam(value = "pagesize", required = false) int pagesize) {
		String resultStr = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("patient", patient);
		params.put("type", type);
		params.put("sortDate", sortDate);
		params.put("begin", begin);
		params.put("end", end);
		params.put("pagesize", pagesize);

		try {
			String url =" ";
			resultStr = HttpClientUtil.doGet(comUrl + url, params, username, password);


			return write(200, "查询成功", resultStr, "");

		} catch (Exception e) {
			error(e);
			return error(-1, "操作失败！");
		}
	}

	//TODO 随访记录获取 - 过滤条件：医生 + 患者
	@ApiOperation("随访记录获取")
	@RequestMapping(value = "getFollowUpVisit", method = RequestMethod.GET)
	public String getFollowUpVisit(
			@ApiParam(name = "doctorId", value = "医生唯一标识",defaultValue = " ")
			@RequestParam(value = "doctorId", required = false) String doctorId,
			@ApiParam(name = "patientId", value = "居民唯一标识", defaultValue = " ")
			@RequestParam(value = "patientId", required = false) String patientId) throws Exception {
		String resultStr = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doctorId", doctorId);
		params.put("patientId", patientId);

		try {
			String url =" ";
			resultStr = HttpClientUtil.doGet(comUrl + url, params, username, password);
			return write(200, "查询成功", resultStr, "");

		}catch (Exception e){
			error(e);
			return error(-1, "操作失败！");
		}
	}

}
