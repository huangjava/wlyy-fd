package com.yihu.wlyy.web.patient.account;

import com.yihu.wlyy.web.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 患者帳戶信息的Controller.
 *
 * @author George
 */
@Controller
@RequestMapping(value = "/patient")
public class PatientController extends BaseController {
    /**
     * 查询患者是否有签约信息
     * @return
     */
    @RequestMapping(value = "is_sign", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询患者是否有签约信息", produces = "application/json", notes = "查询患者是否有签约信息")
    public String isSign(
            @ApiParam(name = "patient", value = "患者Code", required = true)
            @RequestParam(value = "patient") String patient) {
        try {

            return write(200, "获取签约状态成功！", "data",-1);
        } catch (Exception e) {
            error(e);
            return error(-1, "签约状态查询失败！");
        }
    }


    @RequestMapping(value = "teachers")
    @ResponseBody
    @ApiOperation(value = "登录患者获取三师信息", produces = "application/json", notes = "登录患者获取三师信息")
    public String teachers(
            @ApiParam(name = "patient", value = "患者Code", required = true)
            @RequestParam(value = "patient") String patient ) {
        try {

            return write(200, "查询成功！", "data", "");
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "查询失败！");
        }
    }

    /**
     * 患者基本信息查询接口
     * @return
     */
    @RequestMapping(value = "baseinfo")
    @ResponseBody
    @ApiOperation(value = "获取患者基本信息", produces = "application/json", notes = "获取患者基本信息")
    public String baseinfo(
            @ApiParam(name = "patient", value = "患者Code", required = true)
            @RequestParam(value = "patient") String patient ) {
        try {
                return write(200, "患者信息查询成功！", "data", "");
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "患者信息查询失败！");
        }
    }

    /**
     * 患者基本信息保存
     * @param photo 頭像
     * @param name 姓名
     * @param sex 性別
     * @param birthday 生日
     * @param ssc 社保卡號
     * @param province 省標識
     * @param city 市標識
     * @param town 區縣標識
     * @param address 詳細地址
     * @param street 街道
     * @return
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public String save(@RequestParam(required = false) String photo,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) Integer sex,
                       @RequestParam(required = false) String birthday,
                       @RequestParam(required = false) String ssc,
                       @RequestParam(required = false) String province,
                       @RequestParam(required = false) String city,
                       @RequestParam(required = false) String town,
                       @RequestParam(required = false) String address,
                       @RequestParam(required = false) String street) {
        try {
            return error(-1, "保存失败！");
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "保存失败！");
        }
    }
}
