package com.yihu.wlyy.controllers.doctor.sign;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.services.hospital.HospitalService;
import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import com.yihu.wlyy.util.DateUtil;
import com.yihu.wlyy.util.StringUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.servlet.http.HttpServletRequest;
import java.io.Console;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 医生端：家庭签约控制类
 *
 * @author George
 */
@Controller
@RequestMapping(value = "/upload/family_contract")
public class dfUploadController extends BaseController {

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
            String signTeam = " ";
            String signTeamName = " ";
            try {
                //文件保存路径
                String filePath = req.getSession().getServletContext().getRealPath("/") + "upload" + File.separator + UUID.randomUUID()+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")) ;
                System.out.println(filePath);
                //转存文件到本地的指定目录下
                file.transferTo(new File(filePath));
                DataHandler dataHandler = new DataHandler(new FileDataSource(new File(filePath).getAbsoluteFile().getCanonicalPath()));

                Map<String,Object> teamInfo =  hospitalService.getTeamByDoctorCode(orgCode, userId);
                signTeam = teamInfo.get("TEAMID").toString();
                signTeamName = teamInfo.get("TEAMNAME").toString();

                String signPreiodFrom = DateUtil.dateToStrShort(new Date());
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
}
