package com.yihu.wlyy.web.patient.family;

import com.yihu.wlyy.web.BaseController;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public String homepage(String doctor) {
        try {

            return write(200, "医生主页查询成功！", "data", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "医生主页查询失败！");
        }
    }
}
