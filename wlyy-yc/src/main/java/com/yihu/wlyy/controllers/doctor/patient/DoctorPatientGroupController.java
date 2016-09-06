package com.yihu.wlyy.controllers.doctor.patient;


import com.yihu.wlyy.util.HttpClientUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yihu.wlyy.controllers.BaseController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 医生端：患者分组管理
 *
 * @author George
 */
@Controller
@RequestMapping(value = "/doctor/patient_group")
public class DoctorPatientGroupController extends BaseController {

    @Value("${service-gateway.username}")
    private String username;
    @Value("${service-gateway.password}")
    private String password;
    @Value("${service-gateway.url}")
    private String comUrl;

//     获取已签约的记录列表
//     获取待签约的记录列表
//     获取未签约的记录列表
//     根据医生的唯一ID，获取不同状态的患者列表
//     患者详情信息
//     患者标签
//     根据患者的唯一ID ，确认该患者是否签约

    //-----------------------------------------获取已签约居民列表 开始-------------
    //获取已签约居民汇总列表（0-6岁儿童(10人），孕产妇（5人）、65岁以上老人（41人）。。。。。）
    @RequestMapping(value = "signedPatientTypes",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取已签约居民汇总列表")
    public String getSignedPatientTypes(
            @ApiParam(name = "doctorId",value = "医生唯一标识",defaultValue = "doctor001")
            @RequestParam(value = "doctorId") String doctorId,
//            @ApiParam(name = "status",value = "签约状态，已签约，待签约，未签约")
//            @RequestParam(value = "status") String status,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket") String ticket){
        try{
            String url = "";
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("doctor_id",doctorId);
            params.put("status","已签约");//TODO 取实际字段值
//            params.put("status",status);
            params.put("ticket",ticket);
            String resultStr = HttpClientUtil.doGet(comUrl + url, params);

            return write(200, "获取已签约居民汇总列表成功！", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取已签约居民汇总列表失败！");
        }
    }


    //获取已签约对应居民分类（0-6岁儿童，孕产妇、65岁以上老人。。。。。）的签约居民列表
    @RequestMapping(value = "signedPatients",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取已签约居民列表")
    public String getSignedPatients(
            @ApiParam(name = "doctorId",value = "医生唯一标识",defaultValue = "doctor001")
            @RequestParam(value = "doctorId") String doctorId,
            @ApiParam(name = "patientType",value = "签约居民分类",defaultValue = "")
            @RequestParam(value = "patientType") String patientType,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket") String ticket){
        try{
            String url = "";
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("doctor_id",doctorId);
            params.put("patient_type",patientType);
            params.put("ticket",ticket);
            String resultStr = HttpClientUtil.doGet(comUrl + url, params);

            return write(200, "获取已签约居民汇总列表成功！", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取已签约居民汇总列表失败！");
        }
    }
    //--------------------------------获取已签约居民列表 结束--------------------------

    //--------------------------------获取待签约居民列表 开始--------------------------
    @RequestMapping(value = "signingPatients",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取待签约居民列表")
    public String getSigningPatients(
            @ApiParam(name = "doctorId",value = "医生唯一标识",defaultValue = "doctor001")
            @RequestParam(value = "doctorId",required = false) String doctorId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket",required = false) String ticket){
        try{
            String url = "";
//            Map<String,Object> params = new HashMap<String, Object>();
//            params.put("doctor_id",doctorId);
//            params.put("status","待签约");//TODO 取实际字段值
//            params.put("ticket",ticket);
//            String resultStr = HttpClientUtil.doGet(comUrl + url, params);

            JSONArray array = new JSONArray();
            for (int i=0; i<8;i++) {
                JSONObject json = new JSONObject();
                json.put("qyrq","2016-08-31");
                json.put("code","signingPatients"+i);
                json.put("disease",0);
                json.put("sex","2");
                json.put("idcard","44132219941116368X");
                json.put("name","sign"+i);
                json.put("signType","2");
                json.put("partAmount",0);
                json.put("age",21);
                array.put(json);
            }
            return write(200, "获取待签约居民汇总列表成功！", "data", array);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取待签约居民汇总列表失败！");
        }
    }
    //--------------------------------获取待签约居民列表 结束---------------------------

    //--------------------------------获取待签约居民列表 开始--------------------------
    //与获取待签约接口类似，签约状态不同
    @RequestMapping(value = "noSigningPatients",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取未签约居民列表")
    public String getNoSigningPatients(
            @ApiParam(name = "doctorId",value = "医生唯一标识",defaultValue = "doctor001")
            @RequestParam(value = "doctorId",required = false) String doctorId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket",required = false) String ticket){
        try{
            String url = "";
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("doctor_id",doctorId);
            params.put("status","未签约");//TODO 取实际字段值
            params.put("ticket",ticket);
            //String resultStr = HttpClientUtil.doGet(comUrl + url, params);

//            "qyrq": "2016-08-31",
//	                    "code": "a49b601d2f94466b9c5c33b5436afbe1",
//	                    "disease": 0,
//	                    "sex": 2,
//	                    "idcard": "44132219941116368X",
//	                    "name": "洪涵桃",
//	                    "signType": "2",
//	                    "partAmount": 0,
//	                    "age": 21

            JSONArray array = new JSONArray();
            for (int i=0; i<15;i++) {
                JSONObject json = new JSONObject();
                json.put("qyrq","2016-08-31");
                json.put("code","noSigningPatients"+i);
                json.put("disease",0);
                json.put("sex","2");
                json.put("idcard","44132219941116368X");
                json.put("name","nosign"+i);
                json.put("signType","2");
                json.put("partAmount",0);
                json.put("age",21);
                array.put(json);
            }
            return write(200, "获取未签约居民汇总列表成功！", "data",array);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取未签约居民汇总列表失败！");
        }
    }
    //--------------------------------获取待签约居民列表 结束---------------------------

    //--------------------------------获取某个签约居民详情信息、标签、是否签约 开始--------------------------
    @RequestMapping(value = "patientInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取某个签约居民信息")
    public String getPatientInfo(
            @ApiParam(name = "patientId",value = "签约居民唯一标识",defaultValue = "patient001")
            @RequestParam(value = "patientId",required = false) String patientId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket",required = false) String ticket){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("doctor_id",patientId);
        params.put("ticket",ticket);
        try{
            String url = "";
            //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
//            var data = {
//                    name: "张三",
//                    sex: "1",
//                    photo: "",
//                    age: "25",
//                    activeTime: "2015-01-01~2017-01-01",
//                    tag: "高血压、糖尿病",
//                    idCard: "350581199002052852",
//                    mobile: "15656565656",
//                    tel: "0592-7651545",
//                    address: "厦门市湖里区吕岭路泰和花园120号6栋-203室",
//                    linkerTel: "0592-7651545",
//                    birthday: "1990-01-01"
//            }
            JSONObject json = new JSONObject();
            json.put("name","yww");
            json.put("sex","1");
            json.put("photo","");
            json.put("age","25");
            json.put("activeTime","2015-01-01~2017-01-01");
            json.put("tag","高血压、糖尿病");
            json.put("idCard","350581199002052852");
            json.put("mobile","15805926666");
            json.put("tel","0592-7651545");
            json.put("address","厦门市湖里区吕岭路泰和花园120号6栋-203室");
            json.put("linkerTel","0592-7651545");
            json.put("birthday","1990-01-01");
            return write(200, "success！", "data", json);

            //return write(200, "获取居民生信息成功！", "obj", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取居民信息失败！");
        }
    }

    @RequestMapping(value = "patientLabel",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取某个签约居民标签")
    public String getPatientLabel(
            @ApiParam(name = "patientId",value = "签约居民唯一标识",defaultValue = "patient001")
            @RequestParam(value = "patientId") String patientId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket") String ticket){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("doctor_id",patientId);
        params.put("ticket",ticket);
        try{
            String url = "";
            String resultStr = HttpClientUtil.doGet(comUrl+url,params);

            return write(200, "获取居民标签成功！", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取居民信息失败！");
        }
    }

    @RequestMapping(value = "patientStatus",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取某个居民签约状态")
    public String getPatientStatus(
            @ApiParam(name = "patientId",value = "签约居民唯一标识",defaultValue = "patient001")
            @RequestParam(value = "patientId") String patientId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket") String ticket){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("doctor_id",patientId);
        params.put("ticket",ticket);
        try{
            String url = "";
            String resultStr = HttpClientUtil.doGet(comUrl+url,params);

            return write(200, "获取居民标签成功！", "obj", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取居民信息失败！");
        }
    }

    @RequestMapping(value = "labels",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取所有标签（患者标签）集合")
    public String getLabels(
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket") String ticket){
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("ticket",ticket);
        try{
            String url = "";
            String resultStr = HttpClientUtil.doGet(comUrl+url,params);

            return write(200, "获取标签集合成功！", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取标签集合失败！");
        }
    }

    //--------------------------------获取某个签约居民详情信息、标签、是否签约 结束---------------------------


    //统一接口：获取三种签约状态下的记录分类列表
    //

    //   1. 根据医生唯一ID获取相应的团队列表
    //   2. 根据团队ID 获取不同状态的患者列表

    /**
     * 我的患者查询接口
     *
     * @return
     */
//    @RequestMapping(value = "patients")
//    @ResponseBody
//    public String patients() {
//        try {
//
//            return write(200, "患者列表查询成功！", "data", "");
//        } catch (Exception e) {
//            error(e);
//            return invalidUserException(e, -1, "患者列表查询失败！");
//        }
//    }


    /**
     * 校验是否有签约信息
     *
     * @param idcard
     * @return
     */
//    @RequestMapping(value = "check")
//    @ResponseBody
//    public String checkSign(String idcard) {
//        try {
//
//            return write(200, "签约验证成功！", "data", "");
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "签约验证异常！");
//        }
//    }
}
