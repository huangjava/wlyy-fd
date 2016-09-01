package com.yihu.wlyy.web.patient.family;

import com.yihu.wlyy.web.BaseController;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
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
    @RequestMapping(value = "getPatientSign")
    @ResponseBody
    public String getPatientSign(
            String homePageDoctorCode,//主页医生code
            String invitePatientCode //邀请患者
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
    @RequestMapping("/findPatientInviteLog")
    public String findPatientInviteLog(String invitePatientCode) {
        try {

            return write(200, "查询成功!", "data", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "更新已读失败！");
        }
    }
}
