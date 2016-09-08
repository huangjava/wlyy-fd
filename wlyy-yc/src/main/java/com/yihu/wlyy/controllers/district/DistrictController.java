package com.yihu.wlyy.controllers.district;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.district.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lingfeng on 2016/9/8.
 */
@Controller
@RequestMapping(value = "common")
public class DistrictController extends BaseController{
    @Autowired
    private DistrictService districtService;

    /**
     * 省市一二三级查询接口
     * @param type 1一级目录，2二级目录，3三级目录，4街道目录
     * @param code 省或市标识
     * @return
     */
    @RequestMapping(value = "district")
    @ResponseBody
    public String district(int type, String code) {
        try {
            List<?> list = districtService.findByType(type, code);
            return write(200, "查询成功！", "list", list);
        } catch (Exception e) {
            error(e);
            return error(-1, "查询失败！");
        }
    }
}
