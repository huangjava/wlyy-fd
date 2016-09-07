package com.yihu.wlyy.controllers.doctor.health;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.util.DateUtil;
import com.yihu.wlyy.util.HttpClientUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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

	//TODO 根据患者标志获取健康指标（原有接口）
	@ApiOperation("根据患者标志获取健康指标")
	@RequestMapping(value = "list",method = RequestMethod.POST)
	@ResponseBody
	public String getHealthIndexByPatient(
			@ApiParam(name = "patient", value = "患者唯一标识", defaultValue = " ")
			@RequestParam(value = "patient", required = false) String patient,
			@ApiParam(name = "type", value = "健康指标类型（1血糖，2血压，3体重，4腰围）")
			@RequestParam(value = "type", required = false) int type,
			@ApiParam(name = "sortDate", value = "排序字段", defaultValue = "")
			@RequestParam(value = "sortDate", required = false) String sortDate,
			@ApiParam(name = "begin", value = " ", defaultValue = "")
			@RequestParam(value = "begin", required = false) String begin,
			@ApiParam(name = "end", value = " ", defaultValue = "")
			@RequestParam(value = "end", required = false) String end,
			@ApiParam(name = "pagesize", value = " ")
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
//            String patient, int type, @RequestParam(required = false) String sortDate, String begin, String end, int pagesize
//			String url =" ";
//			resultStr = HttpClientUtil.doGet(comUrl + url, params, username, password);

//            Page<PatientHealthIndex> list = healthIndexService.findByPatien(getUID(), type, DateUtil.strToDateShort(sortDate), pagesize);
            switch (type){
                case 1:
                    JSONArray jsonArray = new JSONArray();
                    for (int i=1;i<8;i++) {
                        JSONObject modelJson = new JSONObject();
                        modelJson.put("id", "w"+i);
                        modelJson.put("patient","ww");
                        modelJson.put("value1", 23);
                        modelJson.put("value2", 23);
                        modelJson.put("value3", 23);
                        modelJson.put("value4", 23);
                        modelJson.put("value5", 23);
                        modelJson.put("value6",23);
                        modelJson.put("value7", 23);
                        modelJson.put("type", type);
                        modelJson.put("date", "2016-07-0"+i);
                        modelJson.put("sortDate", DateUtil.dateToStrLong(new Date()));
                        modelJson.put("czrq", DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
                        jsonArray.put(modelJson);
                    }
                    JSONObject values = new JSONObject();
                    values.put("list", jsonArray);
                    return write(200, "查询成功", "list", jsonArray);
                case 2:




                    break;
                case 3:
                    break;
                case 4:
                    break;
            }
            return write(200, "查询成功", "list", "");
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
