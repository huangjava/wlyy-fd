package com.yihu.wlyy.controllers.patient.family;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.models.patient.PatientModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016.08.26.
 */
@Controller
@RequestMapping(value = "/patient/family")
public class FamilyController extends BaseController {

    @RequestMapping(value = "isAssign", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询居民是否已经分拣", produces = "application/json", notes = "查询居民是否已经分拣")
    public String isAssign(
            @ApiParam(name = "patient", value = "患者Code", required = true)
            @RequestParam(value = "patient") String patient) {
        try {

            return write(200, "获取分拣状态成功！", "data",-1);
        } catch (Exception e) {
            error(e);
            return error(-1, "分拣状态查询失败！");
        }
    }

    @RequestMapping(value = "baseinfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取患者基本信息", produces = "application/json", notes = "获取患者基本信息")
    public String baseinfo(
            @ApiParam(name = "patient", value = "患者Code", required = false)
            @RequestParam(required = false) String patient ) {
        try {
            String demo = "{\"id\":1,\"code\":\"CS20160830001\",\"idCard\":\"350204194*******40\",\"birthday\":\"1950-10-17\",\"gender\":1,\"mobile\":\"15210000077\",\"phone\":\"\",\"socialSecurityCard\":\"\",\"photo\":\"\",\"province\":\"350000\",\"city\":\"350200\",\"town\":\"350206\",\"street\":\"\",\"address\":\"高崎南五路\",\"provinceCode\":\"福建省\",\"cityCode\":\"厦门市\",\"townCode\":\"湖里区\",\"streetCode\":\"\",\"status\":1,\"createTime\":\"2016-08-17\"}";
            PatientModel patientModel = objectMapper.readValue(demo,PatientModel.class);

            return write(200, "患者信息查询成功！", "data", patientModel);
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
