package com.yihu.wlyy.controllers.patient.sign;

import com.yihu.wlyy.controllers.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016.08.26.
 */
@Controller
@RequestMapping(value = "/patient/sign")
public class SignController extends BaseController {

    @RequestMapping(value = "getSignStatus", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询患者是否有签约信息", produces = "application/json", notes = "查询患者是否有签约信息")
    public String isSign(
            @ApiParam(name = "patientCode", value = "患者Code", required = true)
            @RequestParam(value = "patientCode") String patientCode) {
        try {

            return write(200, "获取签约状态成功！", "data",-1);
        } catch (Exception e) {
            error(e);
            return error(-1, "签约状态查询失败！");
        }
    }


    @RequestMapping(value = "isAssign", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询居民是否已经分拣", produces = "application/json", notes = "查询居民是否已经分拣")
    public String isAssign(
            @ApiParam(name = "patient", value = "患者Code", required = true)
            @RequestParam(value = "patient") String patient) {
        try {

            return write(200, "获取分拣状态成功！", "data",-1);
        } catch (Exception e) {
            error(e);
            return error(-1, "分拣状态查询失败！");
        }
    }

    /**
     * 取消签约申请
     * @param doctor
     * @return
     */
    @RequestMapping(value = "unsign", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "取消签约申请", produces = "application/json", notes = "取消签约申请")
    public String unsign(
            @ApiParam(name = "doctor", value = "医生Code", required = true)
            String doctor,
            @ApiParam(name = "patientCode", value = "患者Code", required = false)
            @RequestParam(required = false) String patientCode
    ) {
        try {
            return error(-1, "签约状态已变化，无法申请取消签约！");

        } catch (Exception e) {
            error(e);
            return error(-1, "取消签约失败！");
        }
    }

    /**
     * 申请解约
     * @param doctor 医生标识
     * @param doctorName 医生姓名
     * @return
     */
    @RequestMapping(value = "surrender", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "申请解约", produces = "application/json", notes = "申请解约")
    public String surrender(
            @ApiParam(name = "doctor", value = "医生Code", required = true)
            String doctor,
            @ApiParam(name = "doctorName", value = "医生名称", required = true)
            String doctorName,
            @ApiParam(name = "reason", value = "理由", required = true)
            String reason,
            @ApiParam(name = "patientCode", value = "患者Code", required = true)
            @RequestParam(required = true) String patientCode) {
        try {

            return success("解约申请已发送！");
        } catch (Exception e) {
            error(e);
            return error(-1, "解约申请发送失败！");
        }
    }

    /**
     * 得到患者的签约的医生的信息
     * @param patientCode 患者标识
     * @return
     */
    @RequestMapping(value = "getSignMessage", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "得到患者的签约的医生的信息", produces = "application/json", notes = "得到患者的签约的医生的信息")
    public String getSignDoctorMessage(
            @ApiParam(name = "patientCode", value = "患者Code", required = true)
            String patientCode){
        try {



            return write(200, "获取列表成功！", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取列表失败！");
        }
    }

    /**
     * 发送签约申请
     //     * @param province 省代码
     //     * @param provinceName 省名称
     //     * @param city 城市代码
     //     * @param cityName 城市名称
     //     * @param town 区县代码
     //     * @param townName 区县名称
     * @param address 详细地址
    //     * @param name 患者姓名
    //     * @param doctor 医生标识
     * @param idcard 患者身份证号
     * @param ssc 患者社保卡号
     * @param mobile 患者手机号
     * @param emerMobile 患者紧急联系人
     * @param teamCode 团队唯一码
     * @param teamName 团队名称
     */
    @RequestMapping(value = "sendApplication", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "发送签约申请", produces = "application/json", notes = "发送签约申请")
    public String sign(
//            @RequestParam(required = false) String province,
//            @RequestParam(required = false) String provinceName,
//            @RequestParam(required = false) String city,
//            @RequestParam(required = false) String cityName,
//            @RequestParam(required = false) String town,
//            @RequestParam(required = false) String townName,
//            String name,
//            String doctor,
//            String doctorName,
            @ApiParam(name = "orgCode", value = "机构Code", required = true)
            @RequestParam(required = true) String orgCode,
            @ApiParam(name = "teamCode", value = "团队Code", required = true)
            @RequestParam(required = true) String teamCode,
            @ApiParam(name = "address", value = "详细地址", required = false)
            @RequestParam(required = false) String address,
            @ApiParam(name = "patientCode", value = "患者Code", required = true)
            @RequestParam(required = false) String patientCode,
            @ApiParam(name = "idcard", value = "患者身份证号", required = true)
            @RequestParam(required = false)String idcard,
            @ApiParam(name = "orgName", value = "机构名称", required = true)
            @RequestParam(required = false)String orgName,
            @ApiParam(name = "teamName", value = "团队名称", required = true)
            @RequestParam(required = false)String teamName,
            @ApiParam(name = "ssc", value = "医保卡号", required = true)
            String ssc,
            @ApiParam(name = "mobile", value = "手机号", required = true)
            @RequestParam(required = false) String mobile,
            @ApiParam(name = "emerMobile", value = "患者紧急联系人", required = true)
            @RequestParam(required = false) String emerMobile) {
        try {

            return write(200, "签约申请已发送！", "data", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "签约申请发送失败！");
        }
    }

    /**
     * 得到患者的签约信息
     *   homePageDoctor,//主页医生code
     invitePatientCode //邀请患者
     * @return
     */
    @RequestMapping(value = "getPatientSign", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "得到患者的签约信息", produces = "application/json", notes = "得到患者的签约信息")
    public String getPatientSign(
            @ApiParam(name = "invitePatientCode", value = "邀请患者Code", required = true)
            @RequestParam(value = "invitePatientCode") String invitePatientCode,
            @ApiParam(name = "homePageDoctorCode", value = "主页医生code", required = true)
            @RequestParam(value = "homePageDoctorCode") String homePageDoctorCode
    ) {
        try {

            return write(200, "数据加载成功！", "data", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "数据加载失败！");
        }
    }
}
