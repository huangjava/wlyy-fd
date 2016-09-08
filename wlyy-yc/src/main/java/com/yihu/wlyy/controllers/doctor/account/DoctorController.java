package com.yihu.wlyy.controllers.doctor.account;

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
 * 医生信息、医生团队信息、团队成员信息
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


    //获取医生的 团队信息
    @RequestMapping(value = "teamInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取医生团队信息")
    public String getDoctorTeamInfo(
            @ApiParam(name = "orgId",value = "医生所在机构id",defaultValue = "orgId")
            @RequestParam(value = "orgId",required = false) String orgId,
            @ApiParam(name = "userId",value = "医生唯一标识",defaultValue = "userId")
            @RequestParam(value = "userId",required = false) String userId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket",required = false) String ticket
    ){


        //TODO 东软接口  （成员为列表）
            // ------4.1 获取当前医生参与的团队列表
//        1	ORGCODE	VARCHAR2(36)	Y	医生所属机构编码
//        2	USERID	VARCHAR2(36)	Y	医生主索引
//        1	TEAMID	VARCHAR2(30)		团队编号
//        2	TEAMNAME	VARCHAR2(80)		团队名称
        // ------1.2 获取家庭医生团队信息

//        1	TEAMID	VARCHAR2(30)		团队编号
//        2	TEAMNAME	VARCHAR2(80)		团队名称
//        3	ORGCODE	VARCHAR2(36)	Y	所属机构编码
//        4	CREATEDUNITNAME	VARCHAR2(50)		创建单位名称
//        5	CREATEDUNITCODE	VARCHAR2(12)		创建单位编码
//        6	CREATEDTIME	DATE		创建时间
//        7	TEAMDESC	VARCHAR2(200)		团队简介

//        8	USERID	VARCHAR2(36)		医生主键
//        9	USER_FULLNAME	VARCHAR2(32)		医生姓名
//        10	DEPT_NAME	VARCHAR2(255)		所属科室

        //????团队成员性别？？、？？？？


//        Map<String,Object> params = new HashMap<String, Object>();
//        params.put("doctor_id",doctorId);
//        params.put("ticket",ticket);
        try{
            String url = "";
            //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
            JSONObject jsonTeam = new JSONObject();
            //团队信息（页面暂未使用）
            jsonTeam.put("团队编号","");
            jsonTeam.put("团队名称","");
            jsonTeam.put("所属机构编码","");
            jsonTeam.put("创建单位名称","");
            jsonTeam.put("创建单位编码","");
            jsonTeam.put("创建时间","");
            jsonTeam.put("团队简介","");
            //团队成员列表
            //需要姓名、性别（或头像）、所属科室、职称
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
            jsonTeam.put("list",array);
            return write(200, "获取团队信息成功！", "data", jsonTeam);
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
            @ApiParam(name = "userId",value = "医生唯一标识",defaultValue = "doctor001")
            @RequestParam(value = "userId",required = false) String userId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket",required = false) String ticket){

        //TODO 东软接口  1.3 获取家庭医生信息   返回结果有个别差异
        //使用处：1 mine 页面 医生信息（或者在storage保存一份用户基本信息：名字，性别、头像、职称）
//               2 mine 页面点击医生信息，打开新的医生信息页面（医生的详细信息）
//         请求参数  USERID   用户id（登入医生id）
//         返回结果
//        USER_FULLNAME	VARCHAR2(32)	Y	医生姓名
//        GENDER	VARCHAR2(12)		    性别
//        PROFESSION	NVARCHAR2(200)		职业经历
//        EDUCATION	NVARCHAR2(200)		    教育背景
//        SPECIALTY	NVARCHAR2(200)		    专业特长
//        UNIT_NAME	VARCHAR2(255)		    所属机构
//        DEPT_NAME	VARCHAR2(255)		    所属科室
//        PHOTO	BLOB（二进制码）		        照片

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("user_id",userId);//
        params.put("ticket",ticket);
        try{
            String url = "";
            //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
            JSONObject json = new JSONObject();
            json.put("photo","");                   // 照片 8    原有使用的是src
            json.put("name","yww杨");                 // 医生姓名1
            json.put("sex","1");                    // 医生性别2
                                                    // 职业经历3 ****
                                                    // 教育背景4 ****
            json.put("mobile","15805926666no");     // 医生联系方式（未提供）
            json.put("expertise","little");        // 专业特长5
            json.put("hospitalName","first-hospital");//所属机构6
            json.put("deptName","guke");             // 所属科室7
            json.put("jobName","zhuzhiyishino");       // （无）
            json.put("introduce","my is my no");      // 简介（无）
            json.put("provinceName","fu jian no");    // 省份（无）--湖北
            json.put("cityName","xia men no");        // 城市（无）--宜昌
            return write(200, "success！", "data", json);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取医生信息失败！");
        }
    }

    // 获取医生信息
    @RequestMapping(value = "memberInfo",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取团队某个医生信息")
    public String getMemberDoctorInfo(
            @ApiParam(name = "doctorId",value = "团队成员的userId",defaultValue = "doctor001")
            @RequestParam(value = "doctorId",required = false) String doctorId,
            @ApiParam(name = "ticket",value = "ticket",defaultValue = "12121")
            @RequestParam(value = "ticket",required = false) String ticket){

        //TODO  东软接口（未提供） 根据团队成员的唯一标识，获取团队成员信息（接口应该提供过滤敏感信息后的数据）
        //使用处：点击团队列表行，展示某个团队成员信息

        Map<String,Object> params = new HashMap<String, Object>();
        params.put("doctor_id",doctorId);
        params.put("ticket",ticket);
        try{
            String url = "";
            //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
            JSONObject json = new JSONObject();
            json.put("photo","");
            json.put("name","yww"+doctorId);
            json.put("sex","1");
            json.put("mobile","15805926666");
            json.put("hospitalName","first-hospital");
            json.put("dept","kouqiangke");
            json.put("jobName","test");
            json.put("expertise","no  non----"+doctorId);
            json.put("introduce","my is my ");
            json.put("provinceName","fu jian ");
            json.put("cityName","xia men ");
            return write(200, "success！", "data", json);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取医生信息失败！");
        }
    }

}
