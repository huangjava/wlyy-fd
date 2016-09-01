package com.yihu.wlyy.web.doctor.patient;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yihu.wlyy.web.BaseController;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医生端：患者分组管理
 *
 * @author George
 */
@Controller
@RequestMapping(value = "/doctor/patient_group")
public class DoctorPatientGroupController extends BaseController {


    private final int DOCTOR_PATIENT_OBJECT_LENGTH = 13;

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
}
