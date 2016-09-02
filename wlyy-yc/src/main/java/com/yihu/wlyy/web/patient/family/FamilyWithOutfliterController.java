package com.yihu.wlyy.web.patient.family;

import com.yihu.wlyy.web.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2016.08.26.
 */
@Controller
@RequestMapping(value = "/family_contract/homepage")
public class FamilyWithOutfliterController extends BaseController {

    /**
     * 医生主页信息查询接口
     * @param doctor 医生标识
     * @return
     */
    @RequestMapping(value = "homepage")
    @ResponseBody
    @ApiOperation(value = "医生主页信息查询接口", produces = "application/json", notes = "医生主页信息查询接口")
    public String homepage(
            @ApiParam(name = "doctor", value = "医生标识", required = true)
            @RequestParam(value = "doctor") String doctor) {
        try {

            return write(200, "医生主页查询成功！", "data", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "医生主页查询失败！");
        }
    }
}
