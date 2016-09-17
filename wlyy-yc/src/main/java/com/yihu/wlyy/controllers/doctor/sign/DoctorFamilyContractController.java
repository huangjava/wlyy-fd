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
}
