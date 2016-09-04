package com.yihu.wlyy.controllers.doctor.sign;

import com.yihu.wlyy.controllers.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 医生端：家庭签约控制类
 *
 * @author George
 */
@Controller
@RequestMapping(value = "/doctor/family_contract")
public class DoctorFamilyContractController extends BaseController {

    /**
     * 患者签约申请处理接口  -- 加个图片上传的接口及数据的存储
     *
     * @param signType         是否有三师签约 1 否 2是    默认“否”
     * @param healthDoctor     健康管理师标识
     * @param healthDoctorName 健康管理师姓名
     * @param majorDoctor      专科医生标识
     * @param disease          疾病code
     * @param majorDoctorName 专科医生姓名
     * @param majorDoctor 专科医生code
     * @param msgid            消息id
     * @param patientIDcard    患者身份证
     * @param type             处理类型：1同意，2拒绝
     * @param group            医生团队
     * @return
     */
    @RequestMapping(value = "sign")
    @ResponseBody
    public String sign(
            @RequestParam(required = true, defaultValue = "1") String signType,
            String healthDoctor,
            @RequestParam(required = false, defaultValue = "1") String disease,
            String healthDoctorName,
            @RequestParam(required = false) String majorDoctor,
            @RequestParam(required = false) String majorDoctorName,
            long msgid,
            String patientIDcard,
            int type,
            String group,
            @RequestParam(required = false,defaultValue = "0") String expenses) {
        try {
            return "";
        } catch (Exception e) {
            error(e);
            return error(-1, "操作失败！");
        }
    }

    /**
     * 患者解约申请处理接口
     *
     * @param msgid   消息id
     * @param patient 患者标识
     * @param type    处理类型：1同意，2拒绝
     * @param reason  解约原因
     * @return
     */
    /*@RequestMapping(value = "surrender")
    @ResponseBody
    public String surrender(
            long msgid,
            String patient,
            int type,
            @RequestParam(required = false) String reason) {
        try {

            return error(-1, "操作失败！");
        } catch (Exception e) {
            error(e);
            return error(-1, "操作失败！");
        }
    }*/
    /**
     * 获取签约患者信息
     * @param status 签约状态 1:待签约 2, 待解约 3 已签约,4已经解约
     * @param doctorType 医生类别 健管 3  全科 2    默认 “0”
     * @return
     */
    @RequestMapping("/sign_info")
    public String getSignInfoByDoctor(
            int status,
            int doctorType,
            int page,
            int pageSize) {
        try{
            return write(200, "查询成功！", "data", "");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return error(-1,"系统错误,请联系管理员!");
        }
    }


}
