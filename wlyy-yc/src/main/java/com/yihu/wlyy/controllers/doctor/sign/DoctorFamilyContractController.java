package com.yihu.wlyy.controllers.doctor.sign;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.util.HttpClientUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 医生端：家庭签约控制类
 *
 * @author George
 */
@Controller
@RequestMapping(value = "/doctor/family_contract")
public class DoctorFamilyContractController extends BaseController {

    @Value("${service-gateway.username}")
    private String username;
    @Value("${service-gateway.password}")
    private String password;
    @Value("${service-gateway.url}")
    private String comUrl;

    @ApiOperation("更新签约处理信息（复用接口）")
    @RequestMapping(value = "/sign" , method = RequestMethod.POST)
    @ResponseBody
    public String sign(
            @ApiParam(name = "signType", value = "是否有三师签约 1 否 2是", defaultValue = "1")
            @RequestParam(value = "signType",required = true, defaultValue = "1") String signType,
            @ApiParam(name = "healthDoctor", value = "健康管理师标识", defaultValue = " ")
            @RequestParam(value = "healthDoctor", required = false) String healthDoctor,
            @ApiParam(name = "healthDoctorName", value = "健康管理师姓名", defaultValue = " ")
            @RequestParam(value = "healthDoctorName", required = false) String healthDoctorName,
            @ApiParam(name = "disease", value = "疾病code", defaultValue = " ")
            @RequestParam(value = "disease", required = false, defaultValue = "1") String disease,
            @ApiParam(name = "majorDoctor", value = "专科医生标识", defaultValue = " ")
            @RequestParam(value = "majorDoctor", required = false) String majorDoctor,
            @ApiParam(name = "majorDoctorName", value = "专科医生姓名", defaultValue = " ")
            @RequestParam(value = "majorDoctorName", required = false) String majorDoctorName,
            @ApiParam(name = "msgid", value = "消息id", defaultValue = " ")
            @RequestParam(value = "msgid", required = false) long msgid,
            @ApiParam(name = "patientIDcard", value = "患者身份证", defaultValue = " ")
            @RequestParam(value = "patientIDcard", required = false) String patientIDcard,
            @ApiParam(name = "type", value = "患者身份证", defaultValue = " ")
            @RequestParam(value = "type", required = false) int type,
            @ApiParam(name = "group", value = "患者所属组", defaultValue = " ")
            @RequestParam(value = "group", required = false) String group,
            @ApiParam(name = "expenses", value = "付费类型", defaultValue = " ")
            @RequestParam(value = "expenses",required = false,defaultValue = "0") String expenses) {

        String resultStr = "";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("signType", signType);
        params.put("healthDoctor", healthDoctor);
        params.put("healthDoctorName", healthDoctorName);
        params.put("disease", disease);
        params.put("majorDoctor", majorDoctor);
        params.put("majorDoctorName", majorDoctorName);
        params.put("msgid", msgid);
        params.put("patientIDcard", patientIDcard);
        params.put("type", type);
        params.put("group", group);
        params.put("expenses", expenses);

        try {
            String url =" ";
            resultStr = HttpClientUtil.doPost(comUrl + url, params, username, password);

            return write(200, "更新成功", resultStr, "");

        } catch (Exception e) {
            error(e);
            return error(-1, "操作失败！");
        }
    }

    @ApiOperation("医生签约患者列表查询接口（复用接口）")
    @RequestMapping(value = "/sign_info", method = RequestMethod.GET)
    public String getSignInfoByDoctor(
            @ApiParam(name = "status", value = "签约状态 0 :全部 1:待签约 2, 待解约 3 已签约,4已经解约", defaultValue = "3")
            @RequestParam(value = "status",required = false, defaultValue = "3") int status,
            @ApiParam(name = "doctorType", value = "医生类别 健管 3  全科 2", defaultValue = "3")
            @RequestParam(value = "doctorType",required = false) int doctorType,
            @ApiParam(name = "page", value = "页号", defaultValue = " ")
            @RequestParam(value = "page",required = false) int page,
            @ApiParam(name = "pageSize", value = "每页数量", defaultValue = "3")
            @RequestParam(value = "pageSize",required = false) int pageSize) {

        String resultStr = "";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        params.put("doctorType", doctorType);
        params.put("page", page);
        params.put("pageSize", pageSize);

        try{
            String url =" ";
            resultStr = HttpClientUtil.doGet(comUrl + url, params, username, password);

            return write(200, "查询成功！", resultStr, "");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return error(-1,"系统错误,请联系管理员!");
        }
    }

    //TODO 图片上传接口
    @ApiOperation("图片上传接口（新增接口）")
    @RequestMapping(value = "/pic/upload", method = RequestMethod.POST)
    public String uploadImg(
            @ApiParam(name = "patient", value = "患者唯一标识", defaultValue = " ")
            @RequestParam(value = "patient",required = false) int patient,
            @ApiParam(name = "group", value = "医生团队ID", defaultValue = " ")
            @RequestParam(value = "group",required = false) int group) {

        String resultStr = "";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("patient", patient);
        params.put("group", group);

        try{
            String url =" ";
            resultStr = HttpClientUtil.doPost(comUrl + url, params, username, password);

            return write(200, "图片上传成功！", resultStr, "");
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return error(-1,"图片上传失败,请联系管理员!");
        }
    }



}
