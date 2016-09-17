package com.yihu.wlyy.controllers.doctor.patient;


import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.contract.SignContractService;
import com.yihu.wlyy.services.doctor.DoctorService;
import com.yihu.wlyy.util.SystemConf;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private DoctorService doctorService;

    //-----------------------------------------获取已签约居民列表 开始-------------
    @RequestMapping(value = "signedPatients", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取已签约居民列表")
    public String getSignedPatients(
            @ApiParam(name = "orgId", value = "机构编码", defaultValue = " ")
            @RequestParam(value = "orgId", required = false) String orgId,
            @ApiParam(name = "userId", value = "当前医生用户id", defaultValue = "doctor001")
            @RequestParam(value = "userId", required = false) String userId) {
        try {
            JSONArray signedPatients = signContractService.getSignedPatients(orgId, userId, "1", "1000");
            return write(200, "获取已签约居民汇总列表成功！", "data", signedPatients);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取已签约居民汇总列表失败！");
        }
    }

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
            JSONArray toSignInfoList = signContractService.getToSignInfoList(orgId, userId, "1", "1000");
            return write(200, "获取待签约居民汇总列表成功！", "data", toSignInfoList);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取待签约居民汇总列表失败！");
        }
    }

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
            JSONArray notSignInfoList = signContractService.getNotSignInfoList(orgId, userId, "1", "1000");
            return write(200, "获取待签约居民汇总列表成功！", "data", notSignInfoList);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取未签约居民汇总列表失败！");
        }
    }

    //--------------------------------获取某个签约居民详情信息 开始--------------------------
    @RequestMapping(value = "patientInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取某个签约居民信息")
    public String getPatientInfo(
            @ApiParam(name = "patientId", value = "签约居民唯一标识", defaultValue = "patient001")
            @RequestParam(value = "patientId", required = false) String patientId) {
        try {
            JSONObject info = signContractService.getPatientInfo(patientId);
            if(info == null){
                return write(-1,"居民信息不存在！");
            }
            return write(200, "获取居民生信息成功！", "data", info);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取居民信息失败！");
        }
    }
}
