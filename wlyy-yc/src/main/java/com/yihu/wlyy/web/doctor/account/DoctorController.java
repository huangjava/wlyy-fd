package com.yihu.wlyy.web.doctor.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yihu.wlyy.web.BaseController;

/**
 * 用户注册的Controller.
 *
 * @author calvin
 */
@Controller
@RequestMapping(value = "/doctor")
public class DoctorController extends BaseController {


    /**
     * 社区医院下医生列表查询接口 有分页
     *
     * @param hospital 医院标识
     * @return
     */
    @RequestMapping(value = "getDoctorsByhospital")
    @ResponseBody
    public String getDoctorsByhospital(
            @RequestParam(required = false) String hospital,
            @RequestParam(required = false) Integer type,
            long id,
            int pagesize) {
        try {
            return write(200, "获取医院医生列表成功！", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取医院医生列表失败！");
        }
    }


    /**
     * 医生基本信息查询接口
     *
     * @return
     */
    @RequestMapping(value = "baseinfo")
    @ResponseBody
    public String baseinfo(String doctor) {
        try {
            return "";
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "医生信息查询失败！");
        }
    }


}
