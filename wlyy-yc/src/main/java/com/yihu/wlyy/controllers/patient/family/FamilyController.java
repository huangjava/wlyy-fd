package com.yihu.wlyy.controllers.patient.family;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.hospital.HospitalService;
import com.yihu.wlyy.services.person.PersonService;
import com.yihu.wlyy.services.person.SignlTransFormService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016.08.26.
 */
@Controller
@RequestMapping(value = "/patient/family")
public class FamilyController extends BaseController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private SignlTransFormService signlTransFormService;
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "isAssign", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询居民是否已经分拣", produces = "application/json", notes = "查询居民是否已经分拣")
    public String isAssign(
            @ApiParam(name = "openId", value = "居民微信主索引", required = true)
            @RequestParam(value = "openId") String openId) {
        try {
            int sign = 0;
            List<Map<String,Object>> list = hospitalService.getOrgsByOpenId(getOpenid());
            Map<String,Object> info = signlTransFormService. getSignState(getOpenid());

            if (list!=null && list.size()>0){
                if (info!=null && "0".equals(info.get("signStatus")))
                {
                    sign = 0;//已分拣+未签约
                }else {
                    sign = 1;//已分拣+已签约
                }
            }else {
                sign = -1;//未分拣
            }
            return write(200, "获取分拣状态成功！", "data",sign);
        } catch (Exception e) {
            error(e);
            return error(-1, "分拣状态查询失败！");
        }
    }

    @RequestMapping(value = "saveGridInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存居民网格信息", produces = "application/json", notes = "保存居民网格信息")
    public String saveAddress(
            @ApiParam(name = "district", value = "区县ID", required = true)
            @RequestParam(value = "district") String district,
            @ApiParam(name = "street", value = "街道ID", required = true)
            @RequestParam(value = "street") String street,
            @ApiParam(name = "community", value = "居委会ID", required = true)
            @RequestParam(value = "community") String community,
            @ApiParam(name = "address", value = "详细地址", required = true)
            @RequestParam(value = "address") String address ) {
        try {
            List<Map<String,Object>> list =  hospitalService.getOrgsByUserAddr(district, street, community, address);
            int sign = 0;
            if (list!=null && list.size()>0){
                sign = 1;
            }else {
                sign = -1;
            }
            return write(200, "获取分拣状态成功！", "data",sign);
        } catch (Exception e) {
            error(e);
            return error(-1, "分拣状态查询失败！");
        }
    }

    @RequestMapping(value = "baseinfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取患者基本信息", produces = "application/json", notes = "获取患者基本信息")
    public String baseinfo() {
        try {
            Map<String,Object> info = personService.getBaseInfByOpenId(getOpenid());
            return write(200, "患者信息查询成功！", "data", info);
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

    @RequestMapping(value = "district", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存网格信息", produces = "application/json", notes = "保存网格信息")
    public String district(
            @ApiParam(name = "city", value = "城市", required = true)
            @RequestParam(value = "city") String city,
            @ApiParam(name = "town", value = "城镇", required = true)
            @RequestParam(value = "town") String town,
            @ApiParam(name = "street", value = "街道", required = true)
            @RequestParam(value = "street") String street,
            @ApiParam(name = "committee", value = "居委会", required = true)
            @RequestParam(value = "committee") String committee,
            @ApiParam(name = "address", value = "患者Code", required = true)
            @RequestParam(value = "address") String address) {
        try {
            //TODO 调用接口获取数据
            List<Map<String,Object>> list =  hospitalService.getOrgsByUserAddr(town, street, committee, address);
            int sign = 0;
            if (list!=null && list.size()>0){
                sign = 1;
            }else {
                sign = -1;
            }
            return write(200, "保存成功！", "data",sign);
        } catch (Exception e) {
            error(e);
            return error(-1, "保存失败！");
        }
    }
}
