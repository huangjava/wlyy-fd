package com.yihu.wlyy.web.patient.sign;

import com.yihu.wlyy.web.BaseController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.util.List;

/**
 * 患者端：家庭签约控制类
 *
 * @author George
 */
@Controller
@RequestMapping(value = "/patient/family_contract")
public class FamilyContractController extends BaseController {

    /**
     * 取消签约申请
     * @param doctor
     * @return
     */
    @RequestMapping(value = "unsign")
    @ResponseBody
    public String unsign(
            String doctor,
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
    @RequestMapping(value = "surrender")
    @ResponseBody
    public String surrender(String doctor, String doctorName, String reason,
                            @RequestParam(required = false) String patientCode) {
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
    @RequestMapping(value = "getSignMessage")
    @ResponseBody
    public String getSignDoctorMessage(String patientCode){
        try {



            return write(200, "获取列表成功！", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取列表失败！");
        }
    }

    /**
     * 发送签约申请
     * @param province 省代码
     * @param provinceName 省名称
     * @param city 城市代码
     * @param cityName 城市名称
     * @param town 区县代码
     * @param townName 区县名称
     * @param address 详细地址
     * @param name 患者姓名
     * @param doctor 医生标识
     * @param idcard 患者身份证号
     * @param ssc 患者社保卡号
     * @param mobile 患者手机号
     * @param emerMobile 患者紧急联系人
     */
    @RequestMapping(value = "sign")
    @ResponseBody
    public String sign(
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String provinceName,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String cityName,
            @RequestParam(required = false) String town,
            @RequestParam(required = false) String townName,
            @RequestParam(required = false) String address,
            String name,
            String doctor,
            String doctorName,
            @RequestParam(required = false) String patientCode,
            @RequestParam(required = false)String idcard,
            String ssc,
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String emerMobile) {
        try {

            return write(200, "签约申请已发送！", "data", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "签约申请发送失败！");
        }
    }

}
