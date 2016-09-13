package com.yihu.wlyy.controllers.doctor.patient;


import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.contract.SignContractService;
import com.yihu.wlyy.services.person.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
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

    @Autowired
    private SignContractService signContractService;
    @Autowired
    private PersonService personService;

//     获取已签约的记录列表
//     获取待签约的记录列表
//     获取未签约的记录列表
//     患者详情信息

    //-----------------------------------------获取已签约居民列表 开始-------------
    @RequestMapping(value = "signedPatients", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取已签约居民列表")
    public String getSignedPatients(
            @ApiParam(name = "orgId", value = "机构编码", defaultValue = "11")
            @RequestParam(value = "orgId", required = false) String orgId,
            @ApiParam(name = "userId", value = "当前医生用户id", defaultValue = "doctor001")
            @RequestParam(value = "userId", required = false) String userId) {
        try {
            String signedPatients = signContractService.getSignedInfoList(orgId, userId, "1", "100");
            return write(200, "获取已签约居民汇总列表成功！", "data", signedPatients);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取已签约居民汇总列表失败！");
        }

        //接口调通前测试用

//        try {
//
//            String[] fenzu = {"0-6岁儿童", "孕产妇", "65岁以上老人", "高血压", "2型糖尿病"};
//            JSONArray array = new JSONArray();
//            for (int i = 0; i < 5; i++) {
//                JSONObject groupJson = new JSONObject();
//                JSONArray groupPatientArray = new JSONArray();
//                for (int j = 0; j < fenzu.length; j++) {
//                    // 分组标识
//                    String group = "fenzu" + j;
//                    // 患者标识
//                    Object code = "huanzhebiaozhi" + j;
//                    // 姓名
//                    Object name = "王五" + j;
//                    // 头像
//                    Object photo = "touxiang" + j;
//                    // 生日
//                    Date birthday = new Date();
//                    // 性别
//                    Object sex = "xingbie" + j;
//                    // 病情：0绿标，1黄标，2红标
//                    Object diseaseCondition = 0;
//                    // 疾病类型，0健康，1高血压，2糖尿病，3高血压+糖尿病
//                    Object disease = 1;
//                    // 病历数
//                    Object recordAmount = 3;
//                    // 随手记数
//                    Object partAmount = 5;
//                    // 签约日期
//                    Date qyrq = new Date();
//                    // 签约类型（1表示三师签约，2表示家庭签约）
//                    Object signType = 1;
//
//                    JSONObject patientJson = new JSONObject();
//                    //唯一id
//                    patientJson.put("chId", "35082211111111" + j);
//                    patientJson.put("name", "李四" + i);              //姓名
//                    patientJson.put("sex", "1");                    // 性别
//                    patientJson.put("age", 65);                     //年龄（出生日期处理得到）
//                    patientJson.put("sortType", "糖尿病、高血压、老年人");          //分拣标签
//                    patientJson.put("address", "湖北省宜昌市已签约");      //地址
//                    patientJson.put("birthday", "1990-01-01");     //出生日期
//                    groupPatientArray.put(patientJson);
//                }
//                // 设置分组标识
//                groupJson.put("code", "fenzu" + i);
//                // 设置分组名称
//                groupJson.put("name", fenzu[i]);
//                // 设置患者数
//                groupJson.put("total", 8);
//                // 设置患者列表
//                groupJson.put("patients", groupPatientArray);
//                array.put(groupJson);
//            }
//            return write(200, "获取已签约居民汇总列表成功！", "data", array);
//
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "获取已签约居民汇总列表失败！");
//        }
    }

    //--------------------------------获取已签约居民列表 结束--------------------------

    //--------------------------------获取待签约居民列表 开始--------------------------
    @RequestMapping(value = "signingPatients", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取待签约居民列表")
    public String getSigningPatients(
            @ApiParam(name = "orgId", value = "机构编码", defaultValue = "11")
            @RequestParam(value = "orgId", required = false) String orgId,
            @ApiParam(name = "userId", value = "当前医生用户id", defaultValue = "doctor001")
            @RequestParam(value = "userId", required = false) String userId) {
        try {
            String toSignInfoList = signContractService.getToSignInfoList(orgId, userId, "1", "100");
            return write(200, "获取待签约居民汇总列表成功！", "data", toSignInfoList);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取待签约居民汇总列表失败！");
        }

        //接口调通前测试用

//        try{
//            String url = "";
////            Map<String,Object> params = new HashMap<String, Object>();
////            params.put("doctor_id",doctorId);
////            params.put("status","3");//字典值
////            String resultStr = HttpClientUtil.doGet(comUrl + url, params);
//
//            JSONArray array = new JSONArray();
//            for (int i=0; i<8;i++) {
//                JSONObject json = new JSONObject();
//                json.put("chId","44132219941116368"+i); //chId  居民主索引
//                json.put("name","李四"+i);              //姓名
//                json.put("sex","1");                    // 性别
//                json.put("age",65);                     //年龄（出生日期处理得到）
//                json.put("sortType","糖尿病、高血压、老年人");          //分拣标签
//                json.put("address","湖北省宜昌市");      //地址
//                json.put("birthday","1990-01-01");     //出生日期
//
//
//                json.put("qyrq","2016-08-31");
//                json.put("code","signingPatients"+i);
//                json.put("disease",0);
//                json.put("idcard","44132219941116368X");
//                json.put("partAmount",0);
//                array.put(json);
//            }
//            return write(200, "获取待签约居民汇总列表成功！", "data", array);
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "获取待签约居民汇总列表失败！");
//        }




    }
    //--------------------------------获取待签约居民列表 结束---------------------------

    //--------------------------------获取未签约居民列表 开始--------------------------
    //与获取待签约接口类似，签约状态不同
    @RequestMapping(value = "noSigningPatients", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取未签约居民列表")
    public String getNoSigningPatients(
            @ApiParam(name = "orgId", value = "机构编码", defaultValue = "11")
            @RequestParam(value = "orgId", required = false) String orgId,
            @ApiParam(name = "userId", value = "当前医生用户id", defaultValue = "doctor001")
            @RequestParam(value = "userId", required = false) String userId) {
        try {
            String notSignInfoList = signContractService.getNotSignInfoList(orgId, userId, "1", "100");
            return write(200, "获取待签约居民汇总列表成功！", "data", notSignInfoList);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取未签约居民汇总列表失败！");
        }

        //接口调通前测试用

//        try{
//            String url = "";
////            Map<String,Object> params = new HashMap<String, Object>();
////            params.put("doctor_id",userId);
////            params.put("status","0");
//
//            JSONArray array = new JSONArray();
//            for (int i=0; i<15;i++) {
//                JSONObject json = new JSONObject();
//                json.put("chId","44132219941116368"+i); //chId  居民主索引
//                json.put("name","李六"+i);              //姓名
//                json.put("sex","1");                    // 性别
//                json.put("age",65);                     //年龄（出生日期处理得到）
//                json.put("sortType","糖尿病、高血压、老年人");          //分拣标签
//                json.put("address","湖北省宜昌市");      //常住地址
//                json.put("birthday","1990-01-01");     //出生日期
//
//
//                json.put("qyrq","2016-08-31");
//                json.put("code","noSigningPatients"+i);
//                json.put("disease",0);
//                json.put("idcard","44132219941116368X");
//                json.put("signType","2");
//                json.put("partAmount",0);
//                array.put(json);
//            }
//            return write(200, "获取未签约居民汇总列表成功！", "data",array);
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "获取未签约居民汇总列表失败！");
//        }
    }
    //--------------------------------获取待签约居民列表 结束---------------------------

    //--------------------------------获取某个签约居民详情信息 开始--------------------------
    @RequestMapping(value = "patientInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取某个签约居民信息")
    public String getPatientInfo(
            @ApiParam(name = "patientId", value = "签约居民唯一标识", defaultValue = "patient001")
            @RequestParam(value = "patientId", required = false) String patientId) {

        try {
            String info = personService.getInfo(patientId);
            return write(200, "获取居民生信息成功！", "data", info);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取居民信息失败！");
        }

        //接口调通前测试用
//
//        Map<String,Object> params = new HashMap<String, Object>();
//        params.put("doctor_id",patientId);
//        try{
//
//            JSONObject json = new JSONObject();
//            // 有提供
//            json.put("chId",patientId); //chId  居民主索引
//            json.put("name","李六"+patientId);              //姓名
//            json.put("sex","1");                    // 性别
//            json.put("age",65);                     //年龄（出生日期处理得到）
//            json.put("sortType", "糖尿病,高血压,老年人");       //分拣标签
//            json.put("address","湖北省宜昌市");       //常住地址
//            json.put("birthday","1990-01-01");
//
//            //TODO 未提供
//            json.put("mobile","15805926666");           //联系电话（未提供）
//            return write(200, "获取居民生信息成功！", "data", json);
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "获取居民信息失败！");
//        }
    }
    //--------------------------------获取某个签约居民详情信息、标签、是否签约 结束---------------------------
}
