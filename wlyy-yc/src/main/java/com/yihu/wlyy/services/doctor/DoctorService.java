package com.yihu.wlyy.services.doctor;

import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import org.dom4j.Document;
import org.dom4j.dom.DOMDocument;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Airhead on 2016/9/11.
 */
@Service
public class DoctorService {
    @Autowired
    private NeuSoftWebService neuSoftWebService;

    public String getGPTeamInfo(String teamId, String page, String pageSize) {
        String gpTeamInfo = neuSoftWebService.getGPTeamInfo(teamId, page, pageSize);

        return gpTeamInfo;
    }

    public String getInfo(String doctrorId) {

        //String resultStr = HttpClientUtil.doGet(comUrl+url,params);
//        JSONObject json = new JSONObject();
//        json.put("photo", "");                   // 照片 8    原有使用的是src
//        json.put("name", "张三" + userId);                 // 医生姓名1
//        json.put("sex", "1");                    // 医生性别2
//        // 职业经历3 ****
//        // 教育背景4 ****
//        json.put("mobile", "15805926666no");     // 医生联系方式（未提供）
//        json.put("expertise", "中医内科疾病，糖尿病慢性并发症；肿瘤手术后及放、化疗后中医药调理；脾胃虚弱及睡眠障碍、多汗、亚健康调理等");        // 专业特长5
//        json.put("hospitalName", "第一医院");//所属机构6
//        json.put("deptName", "骨科");             // 所属科室7
//        json.put("jobName", "主治医师");       // （无）
//        json.put("introduce", "全部都是文字，最多多少个文字？怎么展示？这只是个示例，多少个字，这边就展示相应的多少行，不会多余，也不会减少");      // 简介（无）
//        json.put("provinceName", "福建省");    // 省份（无）--湖北
//        json.put("cityName", "厦门市");        // 城市（无）--宜昌
        String info = neuSoftWebService.getGPInfo(doctrorId);


        return info;
    }

}
