package com.yihu.wlyy.controllers.doctor.account;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.doctor.DoctorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 医生信息相关的的Controller.
 * 医生信息、医生团队信息、团队成员信息
 *
 * @author calvin
 */
@RestController
@RequestMapping(value = "/doctor")
public class DoctorController extends BaseController {

    @Value("${service-gateway.username}")
    private String username;
    @Value("${service-gateway.password}")
    private String password;
    @Value("${service-gateway.url}")
    private String comUrl;
//    @Value("${service-gateway.doctorUrl}")
//    private String doctorUrl;

    //    XMLUtil xmlUtil = new XMLUtil();
//    NeuSoftAdapter neuSoftAdapter =  new NeuSoftAdapter();
    @Autowired
    private DoctorService doctorService;

    //获取医生的 团队信息
    @RequestMapping(value = "teamInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取医生团队信息")
    public String getDoctorTeamInfo(
            @ApiParam(name = "orgId", value = "医生所在机构id", defaultValue = "orgId")
            @RequestParam(value = "orgId", required = false) String orgId,
            @ApiParam(name = "userId", value = "医生唯一标识", defaultValue = "userId")
            @RequestParam(value = "userId", required = false) String userId,
            @ApiParam(name = "ticket", value = "ticket", defaultValue = "12121")
            @RequestParam(value = "ticket", required = false) String ticket) {
        try {
            java.lang.String myTeam = doctorService.getMyTeam(orgId, userId);
            return write(200, "获取团队信息成功！", "data", myTeam);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取团队信息失败！");
        }
    }

    // 获取医生信息
    @RequestMapping(value = "baseinfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取医生本人信息，原有")
    public String getDoctorInfo(
            @ApiParam(name = "userId", value = "医生唯一标识", defaultValue = "f93afa56-417c-4901-aeab-002ded330d86")
            @RequestParam(value = "userId", required = false) String userId,
            @ApiParam(name = "ticket", value = "ticket", defaultValue = "12121")
            @RequestParam(value = "ticket", required = false) String ticket) {
        try {
            String info = doctorService.getInfo(userId);
            return write(200, "success！", "data", info);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取医生信息失败！");
        }
    }

    // 获取医生信息
    @RequestMapping(value = "memberInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "获取团队某个医生信息")
    public String getMemberDoctorInfo(
            @ApiParam(name = "doctorId", value = "团队成员的userId", defaultValue = "f93afa56-417c-4901-aeab-002ded330d86")
            @RequestParam(value = "doctorId", required = false) String doctorId,
            @ApiParam(name = "ticket", value = "ticket", defaultValue = "12121")
            @RequestParam(value = "ticket", required = false) String ticket) {

        try {
            String info = doctorService.getInfo(doctorId);
            return write(200, "success！", "data", info);
        } catch (Exception e) {
            error(e);
            return error(-1, "获取医生信息失败！");
        }
    }

}
