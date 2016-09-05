package com.yihu.wlyy.controllers.patient.hospital;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.controllers.BaseController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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
    @RequestMapping(value = "/getTownByCityCode", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据市得到区", produces = "application/json", notes = "根据市得到区")
    public String getTownByCityCode(
            @ApiParam(name = "city", value = "城市Code", required = true)
            @RequestParam(required = true) String city) {
        try {
            return write(200, "查询成功", "list", "");
        } catch (Exception e) {
            error(e);
            return error(-1, "查询失败");
        }
    }


    @RequestMapping(value = "/getHositalByTownCode", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据区得到机构", produces = "application/json", notes = "根据区得到机构")
    public String getOrgByTownCode(
            @ApiParam(name = "town", value = "区", required = false)
            @RequestParam(required = false) Integer town,HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List list = objectMapper.readValue("[{\"code\":\"3502050100\",\"name\":\"海沧区嵩屿街道社区卫生服务中心\"},{\"code\":\"3502050101\",\"name\":\"海沧社区卫生服务站\"},{\"code\":\"3502050200\",\"name\":\"石塘社区卫生服务中心\"},{\"code\":\"3502050300\",\"name\":\"东孚卫生院\"},{\"code\":\"3502050301\",\"name\":\"天竺社区卫生服务站\"},{\"code\":\"3502050302\",\"name\":\"国营厦门第一农场社区卫生服务站\"},{\"code\":\"3502050400\",\"name\":\"新阳社区卫生服务中心\"},{\"code\":\"0a11148d-5b04-11e6-8344-fa163e8aee56\",\"name\":\"厦门市海沧医院\",\"photo\":\"\"}]",List.class);
            return write(200, "查询成功", "list",list);
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
    @RequestMapping(value = "/hospital_list", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据类别获取医院列表", produces = "application/json", notes = "根据类别获取医院列表")
    public String getHospitalList(
            @ApiParam(name = "type", value = "医院类型", required = false)
            @RequestParam(required = false) Integer type,
            @ApiParam(name = "query", value = "医院名称", required = true)
            @RequestParam(required = true) String query,
            @ApiParam(name = "id", value = "主键", required = false)
            @RequestParam(required = false) long id,
            @ApiParam(name = "pageSize", value = "每页大小", required = false)
            @RequestParam(required = false) Integer pageSize) {
        try {

            return write(200, "查询成功！", "list", "");
        } catch (Exception ex) {
            error(ex);
            return error(-1, "查询失败！");
        }
    }

    @RequestMapping(value = "/getTeamsByOrg", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据机构编码获取团队列表", produces = "application/json", notes = "根据机构编码获取团队列表")
    public String getTeamsByOrg(
            @ApiParam(name = "orgCode", value = "机构编码", required = false)
            @RequestParam(required = false) String orgCode) {
        try {

            return write(200, "查询成功！", "list", "");
        } catch (Exception ex) {
            error(ex);
            return error(-1, "查询失败！");
        }
    }

    @RequestMapping(value = "/getTeamInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "获取医生团队详细信息", produces = "application/json", notes = "获取医生团队详细信息")
    public String getTeamInfo(
            @ApiParam(name = "teamCode", value = "团队编码", required = true)
            @RequestParam(required = true) String teamCode) {
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
    @RequestMapping(value = "/doctor_list", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据医院标识获取医生信息", produces = "application/json", notes = "根据医院标识获取医生信息")
    public String getDoctorByHospital(
            @ApiParam(name = "hospital", value = "医院标识", required = false)
            @RequestParam(required = false) String hospital,
            @ApiParam(name = "query", value = "查询条件 ：医生名称", required = false)
            @RequestParam(required = false) String query,
            @ApiParam(name = "id", value = "主键", required = true)
            @RequestParam(required = true) long id,
            @ApiParam(name = "pageSize", value = "页数", required = true)
            @RequestParam(required = true) Integer pageSize) {

        try {
            return write(200, "查询成功！", "list", "");
        } catch (Exception ex) {
            error(ex);
            return error(-1, "查询失败！");
        }

    }
}
