package com.yihu.wlyy.controllers.doctor.patient;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.util.XMLUtil;
import org.apache.commons.lang.ArrayUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
@Controller
@RequestMapping(value = "/upload")
public class FileUploadController extends BaseController {
//    @Value("${service-gateway.doctorUrl}")
//    private String doctorUrl;

    XMLUtil xmlUtil = new XMLUtil();

    /**
     * 图片上传
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    @RequestMapping(value = "image", method = RequestMethod.POST/* , headers = "Accept=image/png" */)
    @ResponseBody
    public String image(HttpServletRequest request, HttpServletResponse response) {
        //TODO 单张图片
        try {
//            JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
//            Client client = factory.createClient("");
//            String resultJson = "";
//            Map params = new HashMap<>();
//            params.put("patientId","");
//            String docGroupParamStr = xmlUtil.map2xml(params);

            DataHandler dataHandler = null;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            String patientId = multipartRequest.getParameter("patientId");
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
                //invoke("方法名","参数1","参数2"，"".....)
//                Object[] resultGp = client.invoke("getGPInfo", docGroupParamStr);
                //获取返回信息
                break;
            }
            JSONObject json = new JSONObject();
            json.put("status", 200);
            json.put("msg", "上传成功");
            return json.toString();
        } catch (Exception e) {
            error(e);
            return error(-1, "上传失败");
        }

//                DataHandler?dh1?=?null;
//                try?{
//                    ?? dh1?=?new?DataHandler(new?FileDataSource(new? File("c:/1.jpg").getAbsoluteFile().getCanonicalPath()));
//                    ???}
//                ????catch?(IOException?e)?{
//                    ?????//?TODO?Auto-generated?catch?block
//                    ?????e.printStackTrace();
//                    ???}
//                ????
////?方法调用
//                String?ret?=?(String)?call.invoke(new?Object[]?{dh1,?inputXml?});

    }
}
