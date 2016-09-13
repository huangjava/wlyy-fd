package com.yihu.wlyy.services.doctor;

import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Airhead on 2016/9/11.
 */
@Service
public class DoctorService {
    //  getMyTeam
    //    <?xml version="1.0" encoding="UTF-8"?>
    //    <MsgForm>
    //    <XmlData>
    //    <teamid>420504202001</teamid>
    //    <teamname>高血压管理团队</teamname>
    //    </XmlData>
    //    <XmlData>
    //    <teamid>420503003001</teamid>
    //    <teamname>签约团队</teamname>
    //    </XmlData>
    //    </MsgForm>

    //  getGPTeamInfo
    //    <?xml version="1.0" encoding="UTF-8"?>
    //    <MSGFORM>
    //    <XMLDATA>
    //    <TEAMID>74529931-1445-468d-8873-abfa63734e7c</TEAMID>
    //    <TEAMNAME>高血压团队</TEAMNAME>
    //    <ORGCODE>123456</ORGCODE>
    //    <CREATEDUNITNAME>宜昌市惠民医院</CREATEDUNITNAME>
    //    <CREATEDUNITCODE>420503003000</CREATEDUNITCODE>
    //    <CREATEDTIME>2016-08-10</CREATEDTIME>
    //    <TEAMDESC>团队简介</TEAMDESC>
    //    <USERID>42589bc0-e069-4a92-a70c-c8a19be9d7d2</USERID>
    //    <USER_FULLNAME>宋文</USER_FULLNAME>
    //    <DEPT_NAME>儿科</DEPT_NAME>
    //    </XMLDATA>
    //    <XMLDATA>
    //    <TEAMID>74529931-1445-468d-8873-abfa63734e7c</TEAMID>
    //    <TEAMNAME>高血压团队</TEAMNAME>
    //    <ORGCODE>123456</ORGCODE>
    //    <CREATEDUNITNAME>宜昌市惠民医院</CREATEDUNITNAME>
    //    <CREATEDUNITCODE>420503003000</CREATEDUNITCODE>
    //    <CREATEDTIME>2016-08-10</CREATEDTIME>
    //    <TEAMDESC>团队简介</TEAMDESC>
    //    <USERID>42589bc0-e069-4a92-a70c-c8a19be9d333</USERID>
    //    <USER_FULLNAME>王明</USER_FULLNAME>
    //    <DEPT_NAME>儿科</DEPT_NAME>
    //    </XMLDATA>
    //    </MSGFORM>
    public JSONArray getMyTeam(String orgCode, String doctorId) {
        try {
            String myTeam = NeuSoftWebService.getMyTeam(orgCode, doctorId);
            Document document = DocumentHelper.parseText(myTeam);
            List<Element> elements = document.getRootElement().elements("XMLDATA");
            JSONArray myTeamArray = new JSONArray();
            for (Element teamElement : elements) {
                String teamId = teamElement.elementText("TEAMID");
                String teamName = teamElement.elementText("TEAMNAME");
                JSONObject teamNode = new JSONObject();
                teamNode.put("teamName", teamName);
                teamNode.put("teamId", teamId);

                String gpTeamInfo = NeuSoftWebService.getGPTeamInfo(teamId, "1", "100");
                Document docGpTeam = DocumentHelper.parseText(gpTeamInfo);
                List<Element> doctorList = docGpTeam.getRootElement().elements("XMLDATA");

                JSONArray doctorArray = new JSONArray();
                for (Element doctorElement : doctorList) {
                    String id = doctorElement.elementText("USERID");
                    String name = doctorElement.elementText("USER_FULLNAME");
                    String deptName = doctorElement.elementText("DEPT_NAME");

                    JSONObject doctorNode = new JSONObject();
                    doctorNode.put("id", id);
                    doctorNode.put("name", name);
                    doctorNode.put("dept", deptName);
                    doctorNode.put("jobName", "");
                    doctorArray.put(doctorNode);

                }
                teamNode.put("list", doctorArray);

                myTeamArray.put(teamNode);
            }

            return myTeamArray;
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    //    <?xml version="1.0" encoding="UTF-8"?>
    //    <MSGFORM>
    //    <XMLDATA>
    //    <USER_FULLNAME>张三</USER_FULLNAME>
    //    <GENDER>男</GENDER>
    //    <PROFESSION>职业经历</PROFESSION>
    //    <EDUCATION>硕士</EDUCATION>
    //    <SPECIALTY>检查检验</SPECIALTY>
    //    <UNIT_NAME>**社区</UNIT_NAME>
    //    <DEPT_NAME>检验科</DEPT_NAME>
    //    <PHOTO></PHOTO>
    //    </XMLDATA>
    //    </MSGFORM>
    public JSONObject getInfo(String doctorId) {
        try {
            String info = NeuSoftWebService.getGPInfo(doctorId);

            Document document = DocumentHelper.parseText(info);
            Element xmldata = document.getRootElement().element("XMLDATA");

            String name = xmldata.elementText("USER_FULLNAME");
            String gender = xmldata.elementText("GENDER");
            String profession = xmldata.elementText("PROFESSION");
            String education = xmldata.elementText("EDUCATION");
            String specialty = xmldata.elementText("SPECIALTY");
            String orgName = xmldata.elementText("UNIT_NAME");
            String deptName = xmldata.elementText("DEPT_NAME");
            String photo = xmldata.elementText("PHOTO");

            JSONObject json = new JSONObject();
            json.put("photo", photo);                   // 照片 8    原有使用的是src
            json.put("name", name);                 // 医生姓名1
            json.put("sex", gender.equals("男") ? "1" : "2");                    // 医生性别2
            // 职业经历3 ****
            // 教育背景4 ****
            json.put("mobile", "");     // 医生联系方式（未提供）
            json.put("expertise", profession);        // 专业特长5
            json.put("hospitalName", orgName);//所属机构6
            json.put("deptName", deptName);             // 所属科室7
            json.put("jobName", "");       // （无）
            json.put("introduce", specialty);      // 简介（无）
            json.put("provinceName", "");    // 省份（无）--湖北
            json.put("cityName", "");        // 城市（无）--宜昌

            return json;
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }


}
