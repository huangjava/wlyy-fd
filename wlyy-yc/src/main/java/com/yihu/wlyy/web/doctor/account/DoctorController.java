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
     * 社区医院下医生列表查询接口 没分页
     *
     * @param hospital 医院标识
     * @return
     */
    @RequestMapping(value = "getDoctorsByhospitalNoPage")
    @ResponseBody
    public String getDoctorsByhospitalNoPage(
            @RequestParam(required = false) String hospital,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer type) {
        try {

            return write(200, "获取医院医生列表成功！", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取医院医生列表失败！");
        }
    }

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
     * 社区医院列表查询接口
     *
     * @param type     医院类型：0全部，1大医院，2社区医院
     * @param province 患标识
     * @param city     城市标识
     * @param key      搜索关键字，用于搜索医院名称
     * @return
     */
    @RequestMapping(value = "hospitals")
    @ResponseBody
    public String hospitals(
            int type,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String town,
            @RequestParam(required = false) String key,
            long id,
            int pagesize) {
        try {
            return write(200, "获取医院列表成功！", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "获取医院列表失败！");
        }
    }


    /**
     * 修改密码
     *
     * @param oldpwd 旧密码
     * @param newpwd 新密码
     * @return
     */
    @RequestMapping(value = "updatepwd")
    @ResponseBody
    public String updatepwd(String oldpwd, String newpwd) {
        try {
            return "";
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "修改失败！");
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "loginout")
    @ResponseBody
    public String loginout() {
        try {

            return success("已成功退出！");
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "操作失败！");
        }
    }

    /**
     * 医生基本信息查询接口
     *
     * @return
     */
    @RequestMapping(value = "baseinfo")
    @ResponseBody
    public String baseinfo() {
        try {
            return "";
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "医生信息查询失败！");
        }
    }

    /**
     * 医生基本信息查询接口
     *
     * @return
     */
    @RequestMapping(value = "baseinfoCount")
    @ResponseBody
    public String baseinfoCount(
            String doctorType) {
        try {
            return "";
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "医生信息查询失败！");
        }
    }

    /**
     * 患者基本信息保存
     *
     * @param photo     頭像
     * @param sex       性別
     * @param expertise 专长
     * @param introduce 简介
     * @param province  省标识
     * @param city      市标识
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public String save(
            @RequestParam(required = false) String photo,
            @RequestParam(required = false) Integer sex,
            @RequestParam(required = false) String expertise,
            @RequestParam(required = false) String introduce,
            @RequestParam(required = false) String province,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String mobile) {
        try {

            return null;
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "保存失败！");
        }
    }

    /**
     * 红黄绿标患者数查询接口
     *
     * @return
     */
    @RequestMapping(value = "patients")
    @ResponseBody
    public String patients() {
        try {
            return write(200, "查询成功！", "data", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "查询失败！");
        }
    }

    /**
     * 邀请医生咨询
     *
     * @param patientCode
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/hospital_list_type")
    @ResponseBody
    public String getHospitalList(
            @RequestParam(required = true) int doctorType,
            @RequestParam(required = true) int consultType,
            @RequestParam(required = true) String patientCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer pageSize) {
        try {
            return write(200, "查询成功！", "list", "");

        } catch (Exception ex) {
            ex.printStackTrace();
            error(ex);
            return error(-1, "查询失败！");
        }
    }


}
