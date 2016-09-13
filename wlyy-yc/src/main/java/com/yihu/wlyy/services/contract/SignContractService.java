package com.yihu.wlyy.services.contract;

import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import com.yihu.wlyy.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @created Airhead 2016/9/12.
 */
@Service
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
    public String getSignedInfoList(String orgCode, String doctorId, String page, String pageSize) {
        try {
            String signedInfoList = NeuSoftWebService.getSignedInfoList(orgCode, doctorId, page, pageSize);
            Document document = DocumentHelper.parseText(signedInfoList);
            List<Element> elements = document.getRootElement().elements("XMLDATA");
            String[] groupName = {"未分拣", "高血压", "糖尿病", "结核病", "精神病", "老年人", "孕产妇", "儿童"};
            JSONArray jsonArray = new JSONArray();
            JSONObject[] group = {new JSONObject(), new JSONObject(), new JSONObject(), new JSONObject(), new JSONObject(), new JSONObject(), new JSONObject(), new JSONObject()};
            JSONArray[] groupPatient = {new JSONArray(), new JSONArray(), new JSONArray(), new JSONArray(), new JSONArray(), new JSONArray(), new JSONArray(), new JSONArray()};
            for (Element element : elements) {
                String signTeamName = element.elementText("SIGNTEAMNAME");
                String signPeriod = element.elementText("SIGNPERIOD");
                String signPeriodFrom = element.elementText("SIGNPERIODFROM");
                String chId = element.elementText("CHID");
                String selfName = element.elementText("SELFNAME");
                String gender = element.elementText("GENDER");
                String birthday = element.elementText("BIRTHDAY");
                String address = element.elementText("ADDRESS");
                String sortType = element.elementText("SORT_TYPE");


                JSONObject patient = new JSONObject();
                patient.put("chId", chId);                //唯一id
                patient.put("name", selfName);              //姓名
                patient.put("sex", gender);                    // 性别
                patient.put("age", birthday);                     //年龄（出生日期处理得到）//TODO:Age
                patient.put("sortType", sortType);          //分拣标签      //多个分拣的格式是怎么样的？
                patient.put("address", address);      //地址
                patient.put("birthday", birthday);     //出生日期


                String[] sortList = sortType.split(",");
                for (String sort : sortList) {
                    if (StringUtil.isEmpty(sort)) {
                        sort = "0";
                    }

                    patient.put("sortType", sort);          //分拣标签

                    int index = Integer.parseInt(sort);
                    JSONObject gp = group[index];
                    JSONArray gpPatient = groupPatient[index];
                    gpPatient.add(patient);
                }
            }

            for (int i = 0; i < group.length; ++i) {
                JSONObject gp = group[i];
                JSONArray patients = groupPatient[i];
                if (patients.size() == 0) {
                    continue;
                }

                gp.put("patients", patients);
                jsonArray.add(gp);

                gp.put("code", i + 1);
                gp.put("name", groupName[i]);
                gp.put("total", patients.size());
            }

            return jsonArray.toString();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    //  getToSignInfoList
    //    <?xml version="1.0" encoding="UTF-8"?>
    //    <MSGFORM>
    //    <XMLDATA>
    //    <CHID>0af8336618f0aa6f39bffe683221b4f3</CHID>
    //    <SELFNAME>胡欢</SELFNAME>
    //    <GENDER>男</GENDER>
    //    <BIRTHDAY>2006-07-14</BIRTHDAY>
    //    <ADDRESS>杨岔路</ADDRESS>
    //    <SORT_TYPE>1</SORT_TYPE>
    //    </XMLDATA>
    //    <XMLDATA>
    //    <CHID>0af8336618f0aa6f39bffe683221b222</CHID>
    //    <SELFNAME>胡哥</SELFNAME>
    //    <GENDER>男</GENDER>
    //    <BIRTHDAY>1986-07-14</BIRTHDAY>
    //    <ADDRESS>和平路</ADDRESS>
    //    <SORT_TYPE>2</SORT_TYPE>
    //    </XMLDATA>
    //    </MSGFORM>

    public String getToSignInfoList(String orgCode, String doctorId, String page, String pageSize) {
        try {
            String toSignInfoList = NeuSoftWebService.getToSignInfoList(orgCode, doctorId, page, pageSize);
            Document document = DocumentHelper.parseText(toSignInfoList);
            List<Element> elements = document.getRootElement().elements("XMLDATA");

            JSONArray array = new JSONArray();
            for (Element element : elements) {
                String chId = element.elementText("CHID");
                String selfName = element.elementText("SELFNAME");
                String gender = element.elementText("GENDER");
                String birthday = element.elementText("BIRTHDAY");
                String address = element.elementText("ADDRESS");
                String sortType = element.elementText("SORT_TYPE");

                JSONObject json = new JSONObject();
                json.put("chId", chId); //chId  居民主索引
                json.put("name", selfName);              //姓名
                json.put("sex", gender);                    // 性别
                json.put("age", 65);                     //年龄（出生日期处理得到）
                json.put("sortType", sortType);          //分拣标签
                json.put("address", address);      //常住地址
                json.put("birthday", birthday);     //出生日期


//                json.put("qyrq","2016-08-31");
//                json.put("code","noSigningPatients"+i);
//                json.put("disease",0);
//                json.put("idcard","44132219941116368X");
//                json.put("signType","2");
//                json.put("partAmount",0);
                array.add(json);
            }

            return array.toString();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    // getNotSignInfoList
    //    <?xml version="1.0" encoding="UTF-8"?>
    //    <MSGFORM>
    //    <XMLDATA>
    //    <CHID>0af8336618f0aa6f39bffe683221b4f3</CHID>
    //    <SELFNAME>胡欢</SELFNAME>
    //    <GENDER>男</GENDER>
    //    <BIRTHDAY>2006-07-14</BIRTHDAY>
    //    <ADDRESS>杨岔路</ADDRESS>
    //    <SORT_TYPE></SORT_TYPE>
    //    </XMLDATA>
    //    <XMLDATA>
    //    <CHID>0af8336618f0aa6f39bffe683221b222</CHID>
    //    <SELFNAME>胡哥</SELFNAME>
    //    <GENDER>男</GENDER>
    //    <BIRTHDAY>1986-07-14</BIRTHDAY>
    //    <ADDRESS>和平路</ADDRESS>
    //    <SORT_TYPE></SORT_TYPE>
    //    </XMLDATA>
    //    </MSGFORM>
    public String getNotSignInfoList(String orgCode, String doctorId, String page, String pageSize) {
        try {
            String toSignInfoList = NeuSoftWebService.getNotSignInfoList(orgCode, doctorId, page, pageSize);
            Document document = DocumentHelper.parseText(toSignInfoList);
            List<Element> elements = document.getRootElement().elements("XMLDATA");

            JSONArray array = new JSONArray();
            for (Element element : elements) {
                String chId = element.elementText("CHID");
                String selfName = element.elementText("SELFNAME");
                String gender = element.elementText("GENDER");
                String birthday = element.elementText("BIRTHDAY");
                String address = element.elementText("ADDRESS");
                String sortType = element.elementText("SORT_TYPE");

                JSONObject json = new JSONObject();
                json.put("chId", chId); //chId  居民主索引
                json.put("name", selfName);              //姓名
                json.put("sex", gender);                    // 性别
                json.put("age", 65);                     //年龄（出生日期处理得到）
                json.put("sortType", sortType);          //分拣标签
                json.put("address", address);      //常住地址
                json.put("birthday", birthday);     //出生日期


//                json.put("qyrq","2016-08-31");
//                json.put("code","noSigningPatients"+i);
//                json.put("disease",0);
//                json.put("idcard","44132219941116368X");
//                json.put("signType","2");
//                json.put("partAmount",0);
                array.add(json);
            }

            return array.toString();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }
}
