package com.yihu.wlyy.web.patient.hospital;

import com.yihu.wlyy.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016.08.19.
 */
@Controller
@RequestMapping(value = "/patient/hosptail")
public class HosptailController extends BaseController {
    /**
     * 根据市得到区
     * @return
     */
    @RequestMapping(value = "getTownByCityCode")
    @ResponseBody
    public String getTownByCityCode(String city) {
        try {
            return write(200, "查询成功", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "查询失败");
        }
    }

    /**
     *
     * @param town
     * @return
     */
    @RequestMapping(value = "getHositalByTownCode")
    @ResponseBody
    public String getHositalByTownCode(String town) {
        try {
            return write(200, "查询成功", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "查询失败");
        }
    }

    /**
     * 根据类别获取医院列表
     * @param type
     * @param query 查询条件 医院名称
     * @param id
     * @param pageSize 页数
     * @return
     */
    @RequestMapping(value = "/hospital_list")
    @ResponseBody
    public String getHospitalList(
            @RequestParam(required = true) Integer type,
            @RequestParam(required = false) String query,
            @RequestParam(required = true) long id,
            @RequestParam(required = true) Integer pageSize) {
        try {

            return write(200, "查询成功！", "list", "");
        } catch (Exception ex) {
            error(ex);
            return error(-1, "查询失败！");
        }
    }

    /**
     * 根据医院标识获取医生信息
     * @param hospital 医院标识
     * @param query 查询条件 ：医生名称
     * @param id
     * @param pageSize 页数
     * @return
     */
    @RequestMapping(value = "/doctor_list")
    @ResponseBody
    public String getDoctorByHospital(
            @RequestParam(required = false) String hospital,
            @RequestParam(required = false) String query,
            @RequestParam(required = true) long id,
            @RequestParam(required = true) Integer pageSize) {

        try {
            return write(200, "查询成功！", "list", "");
        } catch (Exception ex) {
            error(ex);
            return error(-1, "查询失败！");
        }

    }
}
