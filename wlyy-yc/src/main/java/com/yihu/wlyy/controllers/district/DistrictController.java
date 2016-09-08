package com.yihu.wlyy.controllers.district;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.district.DistrictService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    @ApiOperation("获取地区信息")
    @RequestMapping(value = "district", produces = "application/json;charset=UTF-8",method = RequestMethod.POST)
    @ResponseBody
    public String district(@ApiParam(name="type",value="地区等级")
                           @RequestParam(value="type",required = true) int type,
                           @ApiParam(name="code",value="地区编码")
                           @RequestParam(value="code",required = false) String code) {
        try {
            List<?> list = districtService.findByType(type, code);
            return write(200, "查询成功！", "list", list);
        } catch (Exception e) {
            error(e);
            return error(-1, "查询失败！");
        }
    }
}
