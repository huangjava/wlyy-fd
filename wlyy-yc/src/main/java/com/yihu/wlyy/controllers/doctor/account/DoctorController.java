package com.yihu.wlyy.controllers.doctor.account;

import com.yihu.wlyy.util.HttpClientUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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


    private static String username = "temp";
    private static String password = "temp";
    private static String comUrl = "temp";

    //TODO  医生登陆
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("医生登入")
    public String doctorLogin(
            @ApiParam(name = "doctorId",value = "医生账号")
            @RequestParam(value = "doctorId") String doctorId,
            @ApiParam(name = "password",value = "密码")
            @RequestParam(value = "password") String password,
            @ApiParam(name = "ticket",value = "")
            @RequestParam(value = "ticket") String ticket){
        try {
            String url = "";
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("doctor_id",doctorId);
            params.put("password",password);
            params.put("ticket",ticket);
            String resultStr = HttpClientUtil.doPost(comUrl + url, params,username,password);
            return null;
        } catch (Exception e) {
            error(e);
            return null;
        }
    }

    //TODO 医生认证
    @RequestMapping(value = "authentication",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("医生认证")
    public String authentication(
            @ApiParam(name = "name",value = "登录者姓名")
            @RequestParam(value = "name") String name,
            @ApiParam(name = "idCard",value = "身份证号")
            @RequestParam(value = "idCard") String idCard,
            @ApiParam(name = "ticket",value = "")
            @RequestParam(value = "ticket") String ticket){
        try {
            String url = "";
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("name",name);
            params.put("id_Card",idCard);
            params.put("ticket",ticket);
            String resultStr = HttpClientUtil.doPost(comUrl+url,params,username,password);
            return null;
        } catch (Exception e) {
            error(e);
            return null;
        }
    }

    //TODO 获取医生的团队信息
    @RequestMapping(value = "team",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取医生团队信息")
    public String getDoctorTeam(
            @ApiParam(name = "doctorId",value = "医生唯一标识")
            @RequestParam(value = "doctorId") String doctorId,
            @ApiParam(name = "ticket",value = "")
            @RequestParam(value = "ticket") String ticket){
        try{
            String url = "";
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("doctor_id",doctorId);
            params.put("ticket",ticket);
            String resultStr = HttpClientUtil.doGet(comUrl+url,params);
            return null;
        }catch (Exception e){
            error(e);
            return error(-1, "");
        }

    }
    //TODO 获取医生的详情 information
    @RequestMapping(value = "baseinfo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取医生信息-原有")
    public String getDoctorInfo(
            @ApiParam(name = "doctorId",value = "医生唯一标识")
            @RequestParam(value = "doctorId") String doctorId,
            @ApiParam(name = "ticket",value = "")
            @RequestParam(value = "ticket") String ticket){
        try{
            String url = "";
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("doctor_id",doctorId);
            params.put("ticket",ticket);
            String resultStr = HttpClientUtil.doGet(comUrl+url,params);
            return null;
        }catch (Exception e){
            error(e);
            return invalidUserException(e, -1, "医生信息查询失败！");
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
