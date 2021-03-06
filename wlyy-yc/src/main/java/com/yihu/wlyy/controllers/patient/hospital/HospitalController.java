package com.yihu.wlyy.controllers.patient.hospital;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.doctor.DoctorService;
import com.yihu.wlyy.services.hospital.HospitalService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016.08.19.
 */
@RestController
@RequestMapping(value = "/patient/hospital")
public class HospitalController extends BaseController {


    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private DoctorService doctorService;

    @RequestMapping(value = "/getTownByCityCode", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ApiOperation(value = "根据市得到区", produces = "application/json", notes = "根据市得到区")
    public String getTownByCityCode(
            @ApiParam(name = "city", value = "城市Code", required = true)
            @RequestParam(required = true) String city) {
        try {
            return write(200, "查询成功", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "查询失败");
        }
    }


    @RequestMapping(value = "/getHospitalByTownCode", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ApiOperation(value = "根据区得到机构", produces = "application/json", notes = "根据区得到机构")
    public String getOrgByTownCode(
            @ApiParam(name = "town", value = "区", required = false)
            @RequestParam(required = false) Integer town,HttpServletResponse response) {
        try {
            //TODO 示例
//            List list = objectMapper.readValue("[{\"code\":\"3502050100\",\"name\":\"海沧区嵩屿街道社区卫生服务中心\"},{\"code\":\"3502050101\",\"name\":\"海沧社区卫生服务站\"},{\"code\":\"3502050200\",\"name\":\"石塘社区卫生服务中心\"},{\"code\":\"3502050300\",\"name\":\"东孚卫生院\"},{\"code\":\"3502050301\",\"name\":\"天竺社区卫生服务站\"},{\"code\":\"3502050302\",\"name\":\"国营厦门第一农场社区卫生服务站\"},{\"code\":\"3502050400\",\"name\":\"新阳社区卫生服务中心\"},{\"code\":\"0a11148d-5b04-11e6-8344-fa163e8aee56\",\"name\":\"厦门市海沧医院\",\"photo\":\"\"}]",List.class);
//            String openId = "OCEF9T2HW1GBY0KINQK0NEL_ZOSK";
            String openId = getOpenid();

            List<Map<String,Object>> list = hospitalService.getOrgsByOpenId(openId);

            return write(200, "查询成功", "list",list);
        } catch (Exception e) {
            error(e);
            return error(-1, "查询失败");
        }
    }

//    /**
//     * 根据类别获取医院列表
//     * @param type
//     * @param query 查询条件 医院名称
//     * @param id
//     * @param pageSize 页数
//     * @return
//     */
//    @RequestMapping(value = "/hospitalList", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
//    @ApiOperation(value = "根据类别获取医院列表", produces = "application/json", notes = "根据类别获取医院列表")
//    public String getHospitalList(
//            @ApiParam(name = "type", value = "医院类型", required = false)
//            @RequestParam(required = false) Integer type,
//            @ApiParam(name = "query", value = "医院名称", required = true)
//            @RequestParam(required = true) String query,
//            @ApiParam(name = "id", value = "主键", required = false)
//            @RequestParam(required = false) long id,
//            @ApiParam(name = "pageSize", value = "每页大小", required = false)
//            @RequestParam(required = false) Integer pageSize) {
//        try {
//
//            return write(200, "查询成功！", "list", "");
//        } catch (Exception ex) {
//            error(ex);
//            return error(-1, "查询失败！");
//        }
//    }

    @RequestMapping(value = "/getTeamsByOrg", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ApiOperation(value = "根据机构编码获取团队列表", produces = "application/json", notes = "根据机构编码获取团队列表")
    public String getTeamsByOrg(
            @ApiParam(name = "orgCode", value = "机构编码", required = false)
            @RequestParam(required = false) String orgCode,
            @ApiParam(name = "page", value = "页数")
            @RequestParam(required = false,defaultValue = "1") String page,
            @ApiParam(name = "pageSize", value = "每页大小")
            @RequestParam(required = false,defaultValue = "10") String pageSize) {

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("orgCode",orgCode);

        try {
            //TODO 示例
            List<Map<String,Object>> list = hospitalService.getTeamsByorgCode(orgCode, page, pageSize);

            return write(200, "查询成功！", "list", list);
        } catch (Exception ex) {
            error(ex);
            return error(-1, "查询失败！");
        }
    }

    @RequestMapping(value = "/getTeamInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ApiOperation(value = "获取医生团队详细信息", produces = "application/json", notes = "获取医生团队详细信息")
    public String getTeamInfo(
            @ApiParam(name = "teamCode", value = "团队编码", required = true)
            @RequestParam(required = true) String teamCode,
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(required = false,defaultValue = "1") String page,
            @ApiParam(name = "pageSize", value = "每页大小", required = false)
            @RequestParam(required = false,defaultValue = "15") String pageSize) {
        try {
            Map<String,Object> info = hospitalService.getTeamInfoByTeamCode(teamCode,page,pageSize);

            return write(200, "查询成功！", "data", info);
        } catch (Exception ex) {
            error(ex);
            return error(-1, "查询失败！");
        }
    }

    @RequestMapping(value = "/getDoctorList", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ApiOperation(value = "根据医院标识获取医生信息", produces = "application/json", notes = "根据医院标识获取医生信息")
    public String getDoctorList(
            @ApiParam(name = "teamCode", value = "团队编码", required = true)
            @RequestParam(required = true) String teamCode,
            @ApiParam(name = "page", value = "页数", required = false)
            @RequestParam(required = false,defaultValue = "1") String page,
            @ApiParam(name = "pageSize", value = "每页个数", required = false)
            @RequestParam(required = false,defaultValue = "10") String pageSize) {

        try {
            //TODO 调用获取团队成员列表接口
            String json = "[\n" +
                    "        {\n" +
                    "            \"code\": \"D2016080002\",\n" +
                    "            \"job_name\": \" 全科医师\",\n" +
                    "            \"introduce\": \"我是全科医生\",\n" +
                    "            \"name\": \"大米全科1\",\n" +
                    "            \"dept_name\": \"\",\n" +
                    "            \"photo\": \"\",\n" +
                    "            \"id\": 1262,\n" +
                    "            \"expertise\": \"我是全科医生\",\n" +
                    "            \"hospital_name\": \"嘉莲社区医疗服务中心\",\n" +
                    "            \"relationship\": \"本人\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"code\": \"D2016080005\",\n" +
                    "            \"job_name\": \" 全科医师\",\n" +
                    "            \"introduce\": \"我是全科医生\",\n" +
                    "            \"name\": \"大米全科2\",\n" +
                    "            \"dept_name\": \"\",\n" +
                    "            \"photo\": \"\",\n" +
                    "            \"id\": 1271,\n" +
                    "            \"expertise\": \"我是全科医生\",\n" +
                    "            \"hospital_name\": \"嘉莲社区医疗服务中心\",\n" +
                    "            \"relationship\": \"女儿\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"code\": \"D2016080225\",\n" +
                    "            \"job_name\": \" 全科医师\",\n" +
                    "            \"introduce\": \"我是全科医生\",\n" +
                    "            \"name\": \"谭仁祝(全科)\",\n" +
                    "            \"dept_name\": \"\",\n" +
                    "            \"photo\": \"\",\n" +
                    "            \"id\": 1274,\n" +
                    "            \"expertise\": \"我是全科医生\",\n" +
                    "            \"hospital_name\": \"嘉莲社区医疗服务中心\",\n" +
                    "            \"relationship\": \"女儿\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"code\": \"D2010080225\",\n" +
                    "            \"job_name\": \" 全科医师\",\n" +
                    "            \"introduce\": \"我是全科医生\",\n" +
                    "            \"name\": \"谭仁祝(全科1)\",\n" +
                    "            \"dept_name\": \"\",\n" +
                    "            \"photo\": \"\",\n" +
                    "            \"id\": 1276,\n" +
                    "            \"expertise\": \"我是全科医生\",\n" +
                    "            \"hospital_name\": \"嘉莲社区医疗服务中心\",\n" +
                    "            \"relationship\": \"女儿\"\n" +
                    "        }\n" +
                    "    ]";
//            JSONArray jsonArray = new JSONArray(json);
//            Map resultMap = new HashMap<>();
//            resultMap.put("list", jsonArray);
//            ResultModel resultModel = ResultModel.success("查询成功！");
//            resultModel.setResultMap(resultMap);
//            return resultModel.toJson();
//            teamCode = "74529931-1445-468d-8873-abfa63734e7c    ";
            JSONArray jsonArray = doctorService.getDoctorsByTeam(teamCode,page,pageSize);
            return write(200, "查询成功！", "list", jsonArray);
        } catch (Exception ex) {
            error(ex);
//            return ResultModel.error("查询失败！").toJson();
            return error(-1, "查询失败！");
        }
    }

    @RequestMapping(value = "getDoctorInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "医生详细信息接口", produces = "application/json", notes = "医生详细信息接口")
    public String doctorInfo(
            @ApiParam(name = "doctorCode", value = "医生标识", required = true)
            @RequestParam(value = "doctorCode") String doctorCode) {
        try {
            String json = "{\n" +
                    "\"photo\":\"\",\n" +
                    "\"doctorCode\":\""+doctorCode+"\",\n" +
                    "\"doctorName\":\"涂慧娟\",\n" +
                    "\"jobName\":\"主任医师\",\n" +
                    "\"teamCode\":\"1\",\n" +
                    "\"teamName\":\"第一社团\",\n" +
                    "\"orgCode\":\"1\",\n" +
                    "\"orgName\":\"思明区中华街道社区卫生服务中心\",\n" +
                    "\"expertise\":\"擅长各种急救，医疗知识\",\n" +
                    "\"introduce\":\"主治医师，2004年毕业于福建医科大学\",\n" +
                    "}";
            JSONObject jsonObject = JSONObject.fromObject(json);

            JSONObject info =doctorService.getDoctorInfo(doctorCode);

            return write(200, "查询成功！", "data", info);
        } catch (Exception e) {
            error(e);
            return error(-1, "医生详细信息失败！");
        }
    }
}
