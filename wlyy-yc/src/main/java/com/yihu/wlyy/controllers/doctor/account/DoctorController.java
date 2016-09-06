package com.yihu.wlyy.controllers.doctor.account;

import com.yihu.wlyy.util.HttpClientUtil;
import io.swagger.annotations.Api;
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
import com.yihu.wlyy.controllers.BaseController;

import java.util.HashMap;
import java.util.Map;

/**
 * 医生信息相关的的Controller.
 *
 * @author calvin
 */
@Controller
@RequestMapping(value = "/doctor")
@Api
public class DoctorController extends BaseController {

    @Value("${service-gateway.username}")
    private String username;
    @Value("${service-gateway.password}")
    private String password;
    @Value("${service-gateway.url}")
    private String comUrl;

    //  医生登陆
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("医生登入")
    public String doctorLogin(
//            @ApiParam(name = "doctorId",value = "医生账号",defaultValue = "dddd")
//            @RequestParam(value = "doctorId") String doctorId,
//            @ApiParam(name = "password",value = "密码")
//            @RequestParam(value = "password") String password,
//            @ApiParam(name = "ticket",value = "")
//            @RequestParam(value = "ticket") String ticket
    ){
        try {
            String url = "";
            JSONObject json = new JSONObject();
            json.put("id","1");
            json.put("uid","1");
            json.put("token","1");
            json.put("hospital","1");
            json.put("name","1");
            json.put("photo","1");
            json.put("userRole","1");
            json.put("doctorType","1");
            return write(200, "成功！", "data", json);
        } catch (Exception e) {
            error(e);
            return error(-1, "失败！");
        }
    }

    // 医生认证
    @RequestMapping(value = "authentication",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("医生认证")
    public String authentication(
            @ApiParam(name = "name",value = "登录者姓名",defaultValue = "张三")
            @RequestParam(value = "name") String name,
            @ApiParam(name = "idCard",value = "身份证号",defaultValue = "350822201600000000")
            @RequestParam(value = "idCard") String idCard,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket") String ticket){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("name",name);
        params.put("id_Card",idCard);
        params.put("ticket",ticket);
        try {
            String url = "";
            String resultStr = HttpClientUtil.doPost(comUrl+url,params,username,password);
            return write(200, "身份认证成功！", "obj", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "身份认证失败！");
        }
    }

    //获取医生的团队信息
    @RequestMapping(value = "team",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取医生团队信息")
    public String getDoctorTeam(
            @ApiParam(name = "doctorId",value = "医生唯一标识",defaultValue = "doctor001")
            @RequestParam(value = "doctorId",required = false) String doctorId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket",required = false) String ticket
    ){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("doctor_id",doctorId);
        params.put("ticket",ticket);
        try{
            String url = "";
            //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
            JSONArray array = new JSONArray();
                for (int i=0; i<5;i++) {
                    JSONObject json = new JSONObject();
                    json.put("id","201600"+i);
                    json.put("name","ww"+i);
                    json.put("dept","www");
                    json.put("jobName","ww");
                    json.put("org","wwwwwww");
                    json.put("sex","1");
                    json.put("photo","");
                    json.put("expertise","wwwwww，wwwwww");
                    json.put("introduce","wwww，wwwwww，wwww，wwwww。。。。");
                    array.put(json);
                }
            return write(200, "获取团队信息成功！", "data", array);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取团队信息失败！");
        }
    }
    // 获取医生信息
    @RequestMapping(value = "baseinfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取医生本人信息，原有")
    public String getDoctorInfo(
            @ApiParam(name = "doctorId",value = "医生唯一标识",defaultValue = "doctor001")
            @RequestParam(value = "doctorId",required = false) String doctorId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket",required = false) String ticket){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("doctor_id",doctorId);
        params.put("ticket",ticket);
        try{
            String url = "";
            //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
            JSONObject json = new JSONObject();
            json.put("photo","");
            json.put("name","yww");
            json.put("sex","1");
            json.put("mobile","15805926666");
            json.put("hospitalName","first-hospital");
            json.put("deptName","kou qiang ke");
            json.put("jobName","test");
            json.put("expertise","no  non");
            json.put("introduce","my is my ");
            json.put("provinceName","fu jian ");
            json.put("cityName","xia men ");
            return write(200, "success！", "data", json);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取医生信息失败！");
        }
    }

    // 获取医生信息
    @RequestMapping(value = "merberinfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取团队某个医生信息")
    public String getMemberDoctorInfo(
            @ApiParam(name = "doctorId",value = "医生唯一标识",defaultValue = "doctor001")
            @RequestParam(value = "doctorId",required = false) String doctorId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket",required = false) String ticket){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("doctor_id",doctorId);
        params.put("ticket",ticket);
        try{
            String url = "";
            //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
            JSONObject json = new JSONObject();
            json.put("photo","");
            json.put("name","yww");
            json.put("sex","1");
            json.put("mobile","15805926666");
            json.put("hospitalName","first-hospital");
            json.put("dept","kouqiangke");
            json.put("jobName","test");
            json.put("expertise","no  non");
            json.put("introduce","my is my ");
            json.put("provinceName","fu jian ");
            json.put("cityName","xia men ");
            return write(200, "success！", "data", json);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取医生信息失败！");
        }
    }

    /**
     * 社区医院下医生列表查询接口 有分页
     *
     * @param hospital 医院标识
     * @return
     */
//    @RequestMapping(value = "getDoctorsByhospital",method = RequestMethod.GET)
//    @ResponseBody
//    public String getDoctorsByhospital(
//            @RequestParam(required = false) String hospital,
//        @RequestParam(required = false) Integer type,
//        long id,
//        int pagesize) {
//        try {
//            return write(200, "获取医院医生列表成功！", "list", "");
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "获取医院医生列表失败！");
//        }
//    }
    /**
     * 医生基本信息查询接口
     *
     * @return
     */
//    @RequestMapping(value = "baseinfo")
//    @ResponseBody
//    public String baseinfo(String doctor) {
//        String url = "";
//        try {
//            return "";
//        } catch (Exception e) {
//            error(e);
//            return invalidUserException(e, -1, "医生信息查询失败！");
//        }
//    }
}
