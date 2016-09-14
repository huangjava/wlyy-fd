package com.yihu.wlyy.controllers.doctor.sign;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import com.yihu.wlyy.util.DateUtil;
import com.yihu.wlyy.util.HttpClientUtil;
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
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 医生端：家庭签约控制类
 *
 * @author George
 */
@Controller
@RequestMapping(value = "/doctor/family_contract")
public class DoctorFamilyContractController extends BaseController {

    @Value("${service-gateway.username}")
    private String username;
    @Value("${service-gateway.password}")
    private String password;
    @Value("${service-gateway.url}")
    private String comUrl;

    NeuSoftWebService neuSoftWebService = new NeuSoftWebService();

    @ApiOperation("更新签约处理信息（复用接口）")
    @RequestMapping(value = "/sign" , method = RequestMethod.POST)
    @ResponseBody
    public String sign(HttpServletRequest request,
            @ApiParam(name = "signTeam", value = "签约团队代码", defaultValue = "1")
            @RequestParam(value = "signTeam",required = true, defaultValue = "1") String signTeam,
            @ApiParam(name = "signTeamName", value = "签约团队名称", defaultValue = " ")
            @RequestParam(value = "signTeamName", required = false) String signTeamName,
            @ApiParam(name = "signPeriod", value = "签约周期\t单位：年", defaultValue = " ")
            @RequestParam(value = "signPeriod", required = false) String signPeriod,
            @ApiParam(name = "signPreiodFrom", value = "签约日期", defaultValue = " ")
            @RequestParam(value = "signPreiodFrom", required = false, defaultValue = "1") String signPreiodFrom,
            @ApiParam(name = "chid", value = "居民主索引", defaultValue = " ")
            @RequestParam(value = "chid", required = false) String chid,
            @ApiParam(name = "agreementName", value = "上传协议名称", defaultValue = " ")
            @RequestParam(value = "agreementName", required = false) String agreementName,
            @ApiParam(name = "orgCode", value = "医生所属机构编码", defaultValue = " ")
            @RequestParam(value = "orgCode", required = false) String orgCode,
            @ApiParam(name = "userId", value = "医生主索引", defaultValue = " ")
            @RequestParam(value = "userId", required = false) String userId) {
        try {
            //图像信息
            DataHandler dataHandler = null;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                MultipartFile mf = entity.getValue();
                InputStream inputStream = mf.getInputStream();
                int temp = 0;
                byte[] tempBuffer = new byte[1024];
                byte[] fileBuffer = new byte[0];
                while ((temp = inputStream.read(tempBuffer)) != -1) {
                    fileBuffer = ArrayUtils.addAll(fileBuffer, ArrayUtils.subarray(tempBuffer, 0, temp));
                }
                inputStream.close();
                dataHandler = new DataHandler(fileBuffer, "application/octet-stream");
                break; // 取完第一张照片即中断
            }

            //其他信息处理。
            signPeriod = "1";  //默认为1年
            signPreiodFrom = DateUtil.getNow().toString(); //默认当天
            agreementName = chid + "agreement";   //居民唯一标识 + agreement
            String res = neuSoftWebService.upConfirmSignedInfo(dataHandler,signTeam,signTeamName,signPeriod,signPreiodFrom,chid,agreementName,orgCode,userId);

            return write(200, "更新成功", "data", res);
        } catch (Exception e) {
            error(e);
            return error(-1, "操作失败！");
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
