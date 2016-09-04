package com.yihu.wlyy.controllers.doctor.patient;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yihu.wlyy.controllers.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 医生端：患者分组管理
 *
 * @author George
 */
@Controller
@RequestMapping(value = "/doctor/patient_group")
public class DoctorPatientGroupController extends BaseController {

    private final int DOCTOR_PATIENT_OBJECT_LENGTH = 13;

    //TODO 获取已签约的记录列表
    //TODO 获取待签约的记录列表
    //TODO 获取未签约的记录列表
    //TODO 患者详情信息
    //TODO 患者标签
    //TODO 根据医生的唯一ID，获取不同状态的患者列表
    //TODO 根据患者的唯一ID ，确认该患者是否签约

    //   1. 根据医生唯一ID获取相应的团队列表
    //   2. 根据团队ID 获取不同状态的患者列表

    /**
     * 我的患者查询接口
     *
     * @return
     */
    @RequestMapping(value = "patients")
    @ResponseBody
    public String patients() {
        try {

            return write(200, "患者列表查询成功！", "data", "");
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "患者列表查询失败！");
        }
    }


    /**
     * 校验是否有签约信息
     *
     * @param idcard
     * @return
     */
    @RequestMapping(value = "check")
    @ResponseBody
    public String checkSign(String idcard) {
        try {

            return write(200, "签约验证成功！", "data", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "签约验证异常！");
        }
    }
}
