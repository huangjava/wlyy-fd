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


    @RequestMapping(value = "teachers", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
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


    @RequestMapping(value = "baseinfo", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
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


    @RequestMapping(value = "save", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "患者基本信息保存", produces = "application/json", notes = "患者基本信息保存")
    public String save(
            @ApiParam(name = "photo", value = "頭像", required = true)
            @RequestParam(required = false) String photo,
            @ApiParam(name = "name", value = "姓名", required = true)
            @RequestParam(required = false) String name,
            @ApiParam(name = "sex", value = "性別", required = true)
            @RequestParam(required = false) Integer sex,
            @ApiParam(name = "birthday", value = "生日", required = true)
            @RequestParam(required = false) String birthday,
            @ApiParam(name = "ssc", value = "社保卡號", required = true)
            @RequestParam(required = false) String ssc,
            @ApiParam(name = "province", value = "省標識", required = true)
            @RequestParam(required = false) String province,
            @ApiParam(name = "city", value = "市標識", required = true)
            @RequestParam(required = false) String city,
            @ApiParam(name = "town", value = "區縣標識", required = true)
            @RequestParam(required = false) String town,
            @ApiParam(name = "address", value = "詳細地址", required = true)
            @RequestParam(required = false) String address,
            @ApiParam(name = "street", value = "街道", required = true)
            @RequestParam(required = false) String street
    ) {
        try {
            return error(-1, "保存失败！");
        } catch (Exception e) {
            error(e);
            return invalidUserException(e, -1, "保存失败！");
        }
    }
}
