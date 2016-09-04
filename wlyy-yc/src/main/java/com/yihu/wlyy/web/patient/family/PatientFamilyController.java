package com.yihu.wlyy.web.patient.family;

import com.yihu.wlyy.web.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 患者家庭签约
 * Created by Administrator on 2016.08.20.
 */
@Controller
@RequestMapping("/patient/family_contract")
public class PatientFamilyController extends BaseController {
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

    /**
     * 查找患者的邀请记录
     * @param invitePatientCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "findPatientInviteLog", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ApiOperation(value = "查找患者的邀请记录", produces = "application/json", notes = "查找患者的邀请记录")
    public String findPatientInviteLog(
            @ApiParam(name = "invitePatientCode", value = "邀请患者Code", required = true)
            @RequestParam(value = "invitePatientCode") String invitePatientCode ) {
        try {

            return write(200, "查询成功!", "data", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "更新已读失败！");
        }
    }
}
