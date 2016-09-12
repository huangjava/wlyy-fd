package com.yihu.wlyy.controllers.doctor.account;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.doctor.DoctorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 医生信息相关的的Controller.
 * 医生信息、医生团队信息、团队成员信息
 *
 * @author calvin
 */
@RestController
@RequestMapping(value = "/doctor")
public class DoctorController extends BaseController {

    @Value("${service-gateway.username}")
    private String username;
    @Value("${service-gateway.password}")
    private String password;
    @Value("${service-gateway.url}")
    private String comUrl;
//    @Value("${service-gateway.doctorUrl}")
//    private String doctorUrl;

    //    XMLUtil xmlUtil = new XMLUtil();
//    NeuSoftAdapter neuSoftAdapter =  new NeuSoftAdapter();
    @Autowired
    private DoctorService doctorService;

    //获取医生的 团队信息
    @RequestMapping(value = "teamInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取医生团队信息")
    public String getDoctorTeamInfo(
            @ApiParam(name = "orgId", value = "医生所在机构id", defaultValue = "orgId")
            @RequestParam(value = "orgId", required = false) String orgId,
            @ApiParam(name = "userId", value = "医生唯一标识", defaultValue = "userId")
            @RequestParam(value = "userId", required = false) String userId,
            @ApiParam(name = "ticket", value = "ticket", defaultValue = "12121")
            @RequestParam(value = "ticket", required = false) String ticket) {
        try {
            String myTeam = doctorService.getMyTeam(orgId, userId);
            return write(200, "获取团队信息成功！", "data", myTeam);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取团队信息失败！");
        }

        //接口调通前测试用

//        try{
//            String url = "";
//            //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
//            JSONObject jsonTeam = new JSONObject();
//            //团队信息（页面暂未使用）
//            jsonTeam.put("团队编号","");
//            jsonTeam.put("teamName","骨科小分队");
//            jsonTeam.put("所属机构编码","");
//            jsonTeam.put("创建单位名称","");
//            jsonTeam.put("创建单位编码","");
//            jsonTeam.put("创建时间","");
//            jsonTeam.put("团队简介","");
//            //团队成员列表
//            //需要姓名、性别（或头像）、所属科室、职称
//            JSONArray array = new JSONArray();
//            for (int i=0; i<5;i++) {
//                JSONObject json = new JSONObject();
//                json.put("id","201600"+i);
//                json.put("name","张三"+i);
//                json.put("dept","骨科");
//                json.put("jobName","主治医师");
//                json.put("org","厦门第一医院");
//                json.put("sex","1");
//                json.put("photo","");
//                json.put("expertise","全部都是文字，最多多少个文字？怎么展示？这只是个示例，多少个字，这边就展示相应的多少行，不会多余，也不会减少");
//                json.put("introduce","全部都是文字，最多多少个文字？怎么展示？这只是个示例，多少个字，这边就展示相应的多少行，不会多余，也不会减少");
//                array.put(json);
//            }
//            jsonTeam.put("list",array);
//            return write(200, "获取团队信息成功！", "data", jsonTeam);
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "获取团队信息失败！");
//        }
    }

    // 获取医生信息
    @RequestMapping(value = "baseinfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取医生本人信息，原有")
    public String getDoctorInfo(
            @ApiParam(name = "userId", value = "医生唯一标识", defaultValue = "f93afa56-417c-4901-aeab-002ded330d86")
            @RequestParam(value = "userId", required = false) String userId,
            @ApiParam(name = "ticket", value = "ticket", defaultValue = "12121")
            @RequestParam(value = "ticket", required = false) String ticket) {
        try {
            String info = doctorService.getInfo(userId);
            return write(200, "success！", "data", info);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取医生信息失败！");
        }

        // 接口调通前测试用
//        try{
//            String url = "";
//            //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
//            JSONObject json = new JSONObject();
//            json.put("photo","");                   // 照片 8    原有使用的是src
//            json.put("name","张三"+userId);                 // 医生姓名1
//            json.put("sex","1");                    // 医生性别2
//            // 职业经历3 ****
//            // 教育背景4 ****
//            json.put("mobile","15805926666no");     // 医生联系方式（未提供）
//            json.put("expertise","中医内科疾病，糖尿病慢性并发症；肿瘤手术后及放、化疗后中医药调理；脾胃虚弱及睡眠障碍、多汗、亚健康调理等");        // 专业特长5
//            json.put("hospitalName","第一医院");//所属机构6
//            json.put("deptName","骨科");             // 所属科室7
//            json.put("jobName","主治医师");       // （无）
//            json.put("introduce","全部都是文字，最多多少个文字？怎么展示？这只是个示例，多少个字，这边就展示相应的多少行，不会多余，也不会减少");      // 简介（无）
//            json.put("provinceName","福建省");    // 省份（无）--湖北
//            json.put("cityName","厦门市");        // 城市（无）--宜昌
//            return write(200, "success！", "data", json);
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "获取医生信息失败！");
//        }
    }

    // 获取医生信息
    @RequestMapping(value = "memberInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取团队某个医生信息")
    public String getMemberDoctorInfo(
            @ApiParam(name = "doctorId", value = "团队成员的userId", defaultValue = "f93afa56-417c-4901-aeab-002ded330d86")
            @RequestParam(value = "doctorId", required = false) String doctorId,
            @ApiParam(name = "ticket", value = "ticket", defaultValue = "12121")
            @RequestParam(value = "ticket", required = false) String ticket) {
        return this.getDoctorInfo(doctorId, ticket);

        //接口调通前测试用
//        Map<String,Object> params = new HashMap<String, Object>();
//        params.put("doctor_id",doctorId);
//        params.put("ticket",ticket);
//        try{
//            String url = "";
//            //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
//            JSONObject json = new JSONObject();
//            json.put("photo","");
//            json.put("name","张三"+doctorId);
//            json.put("sex","1");
//            json.put("mobile","15805926666");
//            json.put("hospitalName","中山医院");
//            json.put("dept","骨科");
//            json.put("jobName","专科医师");
//            json.put("expertise","中医内科疾病，糖尿病慢性并发症；肿瘤手术后及放、化疗后中医药调理；脾胃虚弱及睡眠障碍、多汗、亚健康调理等");
//            json.put("introduce","全部都是文字，最多多少个文字？怎么展示？这只是个示例，多少个字，这边就展示相应的多少行，不会多余，也不会减少 ");
//            json.put("provinceName","福建省");
//            json.put("cityName","厦门市");
//            return write(200, "success！", "data", json);
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "获取医生信息失败！");
//        }
    }

}
