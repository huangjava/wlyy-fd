package com.yihu.wlyy.controllers.doctor.health;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.models.health.DevicePatientHealthIndex;
import com.yihu.wlyy.services.health.PatientHealthIndexService;
import com.yihu.wlyy.util.DateUtil;
import com.yihu.wlyy.util.HttpClientUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
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

    @Autowired
    private PatientHealthIndexService healthIndexService;

	//TODO 根据患者标志获取健康指标（原有接口）
	@ApiOperation("根据患者标志获取健康指标")
	@RequestMapping(value = "list",method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getHealthIndexByPatient(
			@ApiParam(name = "patientId", value = "患者唯一标识", defaultValue = " ")
			@RequestParam(value = "patientId", required = false) String patientId,
			@ApiParam(name = "type", value = "健康指标类型（1血糖，2血压，3体重，4腰围）")
			@RequestParam(value = "type", required = false) int type,
			@ApiParam(name = "sortDate", value = "排序字段", defaultValue = "sortDate")
			@RequestParam(value = "sortDate", required = false) String sortDate,
			@ApiParam(name = "begin", value = " ", defaultValue = "")
			@RequestParam(value = "begin", required = false) String begin,
			@ApiParam(name = "end", value = " ", defaultValue = "")
			@RequestParam(value = "end", required = false) String end,
			@ApiParam(name = "pagesize", value = " ")
			@RequestParam(value = "pagesize", required = false) int pagesize) {
		String resultStr = "";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("patient", patientId);
		params.put("type", type);
		params.put("sortDate", sortDate);
		params.put("begin", begin);
		params.put("end", end);
		params.put("pagesize", pagesize);

		try {
//            String patient, int type, @RequestParam(required = false) String sortDate, String begin, String end, int pagesize
//			String url =" ";
//			resultStr = HttpClientUtil.doGet(comUrl + url, params, username, password);

            Page<DevicePatientHealthIndex> list = healthIndexService.findByPatien("P20160812002", type, DateUtil.strToDateShort(sortDate),DateUtil.strToDateShort(begin),DateUtil.strToDateShort(end), pagesize);
            if (list == null) {
                return success("查询成功!");
            }
            JSONArray jsonArray = new JSONArray();
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
                modelJson.put("date", DateUtil.dateToStrShort(model.getRecordDate()));
                modelJson.put("sortDate", DateUtil.dateToStrLong(model.getSortDate()));
                modelJson.put("czrq", DateUtil.dateToStr(model.getCzrq(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                jsonArray.put(modelJson);
            }
            JSONObject values = new JSONObject();
            values.put("list", jsonArray);
            return write(200, "查询成功", "list", jsonArray);
        } catch (Exception ex) {
            error(ex);
            return invalidUserException(ex, -1, "查询失败！");
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
