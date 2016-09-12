package com.yihu.wlyy.services.contract;

import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONObject;

import java.util.List;

/**
 * @created Airhead 2016/9/12.
 */
public class SignContractService {
    //getSignedInfoList
    //    <?xml version="1.0" encoding="UTF-8"?>
    //    <MSGFORM>
    //    <XMLDATA>
    //    <SIGNTEAMNAME>高血压团队</SIGNTEAMNAME>
    //    <SIGNPERIOD>1</SIGNPERIOD>
    //    <SIGNPERIODFROM>2016-08-31</SIGNPERIODFROM>
    //    <CHID>1234567890</CHID>
    //    <SELFNAME>张三</SELFNAME>
    //    <GENDER>女</GENDER>
    //    <BIRTHDAY>1972-08-31</BIRTHDAY>
    //    <ADDRESS>宜昌市**********</ADDRESS>
    //    <SORT_TYPE>1</SORT_TYPE>
    //    </XMLDATA>
    //    <XMLDATA>
    //    <SIGNTEAMNAME>高血压团队</SIGNTEAMNAME>
    //    <SIGNPERIOD>1</SIGNPERIOD>
    //    <SIGNPERIODFROM>2016-08-31</SIGNPERIODFROM>
    //    <CHID>1234567890</CHID>
    //    <SELFNAME>李四</SELFNAME>
    //    <GENDER>男</GENDER>
    //    <BIRTHDAY>1972-08-31</BIRTHDAY>
    //    <ADDRESS>宜昌市**********</ADDRESS>
    //    <SORT_TYPE>1</SORT_TYPE>
    //    </XMLDATA>
    //    </MSGFORM>
    public static String getSignedPatients(String orgCode, String doctorId, String page, String pageSize) {
        try {
            String signedInfoList = NeuSoftWebService.getSignedInfoList(orgCode, doctorId, page, pageSize);
            Document document = DocumentHelper.parseText(signedInfoList);
            List<Element> elements = document.getRootElement().elements("XMLDATA");
            net.sf.json.JSONArray jsonArray = new net.sf.json.JSONArray();
            for (Element element : elements) {
                String signTeamName = element.elementText("SIGNTEAMNAME");
                String signPeriod = element.elementText("SIGNPERIOD");
                String signPeriodFrom = element.elementText("SIGNPERIODFROM");
                String chid = element.elementText("CHID");
                String selfName = element.elementText("SELFNAME");
                String gender = element.elementText("GENDER");
                String birthday = element.elementText("BIRTHDAY");
                String address = element.elementText("ADDRESS");
                String sortType = element.elementText("SORT_TYPE");


                JSONObject patientJson = new JSONObject();
                //唯一id
                patientJson.put("chId","35082211111111");
                patientJson.put("name","李四");              //姓名
                patientJson.put("sex","1");                    // 性别
                patientJson.put("age",65);                     //年龄（出生日期处理得到）
                patientJson.put("sortType","糖尿病、高血压、老年人");          //分拣标签
                patientJson.put("address","湖北省宜昌市已签约");      //地址
                patientJson.put("birthday","1990-01-01");     //出生日期

            }

            return jsonArray.toString();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }
}
