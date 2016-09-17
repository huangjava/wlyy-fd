package com.yihu.wlyy.controllers.doctor.sign;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.hospital.HospitalService;
import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import com.yihu.wlyy.util.DateUtil;
import com.yihu.wlyy.util.HttpClientUtil;
import com.yihu.wlyy.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医生端：家庭签约控制类
 *
 * @author George
 */
@Controller
@RequestMapping(value = "/upload/family_contract")
public class DoctorFamilyContractController extends BaseController {

    @Value("${service-gateway.username}")
    private String username;
    @Value("${service-gateway.password}")
    private String password;
    @Value("${service-gateway.url}")
    private String comUrl;

    NeuSoftWebService neuSoftWebService = new NeuSoftWebService();
    HospitalService hospitalService = new HospitalService();

    @ApiOperation("更新签约处理信息")
    @RequestMapping(value = "/sign" , method = RequestMethod.POST)
    @ResponseBody
    public String sign(
            HttpServletRequest req,
            @ApiParam(name = "chid", value = "居民主索引", defaultValue = " ")
            @RequestParam(value = "chid", required = false) String chid,
            @ApiParam(name = "orgCode", value = "医生所属机构编码", defaultValue = " ")
            @RequestParam(value = "orgCode", required = false) String orgCode,
            @ApiParam(name = "userId", value = "医生主索引", defaultValue = " ")
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam MultipartFile file) throws Exception{

        if (!file.isEmpty()) {
            String signTeam = "";
            String signTeamName = "";
            try {
                // 文件保存路径
                String filePath = req.getSession().getServletContext().getRealPath("/") + file.getOriginalFilename();
                //转存文件到本地的指定目录下
                file.transferTo(new File(filePath));
                DataHandler dataHandler = new DataHandler(new FileDataSource(new File(filePath).getAbsoluteFile().getCanonicalPath()));

                List<Map<String,Object>> teamList =  hospitalService.getTeamByDoctorCode(orgCode, userId);
                if(teamList.size()> 0){
                    Map<String,Object> teamInfo = teamList.get(0);
                    signTeam = teamInfo.get("TEAMID").toString();
                    signTeamName = teamInfo.get("TEAMNAME").toString();
                }
                String signPreiodFrom = DateUtil.getNow().toString();
                String signPeriod = "1";
                String agreementName = file.getOriginalFilename();
                String res = neuSoftWebService.upConfirmSignedInfo(dataHandler,signTeam,signTeamName,signPeriod,signPreiodFrom,chid,agreementName,orgCode,userId);
                return write(200, "更新成功", "data", res);
            } catch (Exception e) {
                e.printStackTrace();
                return error(-1, "操作失败！");
            }
        }
        else{
            return error(-1, "签约协议文件不存在，请确认！");
        }
    }

    @ApiOperation("医生签约患者列表查询接口（复用接口）")
    @RequestMapping(value = "/sign_info", method = RequestMethod.GET)
    public String getSignInfoByDoctor(
            @ApiParam(name = "status", value = "签约状态 0 :全部 1:待签约 2, 待解约 3 已签约,4已经解约", defaultValue = "3")
            @RequestParam(value = "status",required = false, defaultValue = "3") int status,
            @ApiParam(name = "doctorType", value = "医生类别 健管 3  全科 2", defaultValue = "3")
            @RequestParam(value = "doctorType",required = false) int doctorType,
            @ApiParam(name = "page", value = "页号", defaultValue = " ")
            @RequestParam(value = "page",required = false) int page,
            @ApiParam(name = "pageSize", value = "每页数量", defaultValue = "3")
            @RequestParam(value = "pageSize",required = false) int pageSize) {

        String resultStr = "";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("status", status);
        params.put("doctorType", doctorType);
        params.put("page", page);
        params.put("pageSize", pageSize);

        try{
            String url =" ";
            resultStr = HttpClientUtil.doGet(comUrl + url, params, username, password);

            return write(200, "查询成功！", resultStr, "");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return error(-1,"系统错误,请联系管理员!");
        }
    }

    //TODO 图片上传接口
    @ApiOperation("图片上传接口（新增接口）")
    @RequestMapping(value = "/pic/upload", method = RequestMethod.POST)
    public String uploadImg(
            @ApiParam(name = "patient", value = "患者唯一标识", defaultValue = " ")
            @RequestParam(value = "patient",required = false) int patient,
            @ApiParam(name = "group", value = "医生团队ID", defaultValue = " ")
            @RequestParam(value = "group",required = false) int group) {

        String resultStr = "";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("patient", patient);
        params.put("group", group);

        try{
            String url =" ";
            resultStr = HttpClientUtil.doPost(comUrl + url, params, username, password);

            return write(200, "图片上传成功！", resultStr, "");
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return error(-1,"图片上传失败,请联系管理员!");
        }
    }



}
