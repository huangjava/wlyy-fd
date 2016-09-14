package com.yihu.wlyy.services.contract;

import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
    public JSONArray getSignedPatients(String orgCode, String doctorId, String page, String pageSize) {
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
                String age = "";
                if(!StringUtils.isEmpty(birthday)){
                    age= getAge(new Date(birthday.replace("-","/")))+"";
                }
                JSONObject patient = new JSONObject();
                patient.put("chId", chId);                //唯一id
                patient.put("name", selfName);              //姓名
                patient.put("sex", gender);                    // 性别
                patient.put("age", age);                     //年龄（出生日期处理得到）//TODO:Age
                patient.put("sortType", sortType);          //分拣标签      //多个分拣的格式是怎么样的？
                patient.put("address", address);      //地址
                patient.put("birthday", birthday);     //出生日期

                String[] sortList = sortType.split(",");
                for (String sort : sortList) {
                    if (StringUtils.isEmpty(sort)) {
                        sort = "0";
                    }
                    int index = Integer.parseInt(sort);
                    patient.put("sortType", groupName[index]);          //分拣标签
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
                gp.put("code", i);
                gp.put("name", groupName[i]);
                gp.put("total", patients.size());
                jsonArray.add(gp);
            }

            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int getAge(Date birthDay) throws Exception {
        //获取当前系统时间
        Calendar cal = Calendar.getInstance();
        //如果出生日期大于当前时间，则抛出异常
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        //取出系统当前时间的年、月、日部分
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        //将日期设置为出生日期
        cal.setTime(birthDay);
        //取出出生日期的年、月、日部分
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        //当前年份与出生年份相减，初步计算年龄
        int age = yearNow - yearBirth;
        //当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
        if (monthNow <= monthBirth) {
            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
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

    public JSONArray getToSignInfoList(String orgCode, String doctorId, String page, String pageSize) {
        try {
            String[] groupName = {"未分拣", "高血压", "糖尿病", "结核病", "精神病", "老年人", "孕产妇", "儿童"};
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
                String age = "";
                if(!StringUtils.isEmpty(birthday)){
                    age= getAge(new Date(birthday.replace("-","/")))+"";
                }

                JSONObject json = new JSONObject();
                json.put("chId", chId); //chId  居民主索引
                json.put("name", selfName);              //姓名
                json.put("sex", gender);                    // 性别
                json.put("age", age);                     //年龄（出生日期处理得到）

                String sortTypeName = "";
                for(String str :sortType.split(",")){
                    if(StringUtils.isEmpty(sortType)){
                        str = "0";
                    }
                    sortTypeName = groupName[Integer.parseInt(str)]+",";
                }
                json.put("sortType", sortTypeName.substring(0,sortTypeName.length()-1));          //分拣标签
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

            return array;
        } catch (Exception e) {
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
    public JSONArray getNotSignInfoList(String orgCode, String doctorId, String page, String pageSize) {
        try {
            String[] groupName = {"未分拣", "高血压", "糖尿病", "结核病", "精神病", "老年人", "孕产妇", "儿童"};
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
                String age = "";
                if(!StringUtils.isEmpty(birthday)){
                    age= getAge(new Date(birthday.replace("-","/")))+"";
                }
                JSONObject json = new JSONObject();
                json.put("chId", chId); //chId  居民主索引
                json.put("name", selfName);              //姓名
                json.put("sex", gender);                    // 性别
                json.put("age", age);                     //年龄（出生日期处理得到）
                String sortTypeName = "";
                for(String str :sortType.split(",")){
                    if(StringUtils.isEmpty(sortType)){
                        str = "0";
                    }
                    sortTypeName = groupName[Integer.parseInt(str)]+",";
                }
                json.put("sortType", sortTypeName.substring(0,sortTypeName.length()-1));
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

            return array;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


//    <?xml version="1.0" encoding="UTF-8"?>
//    <MSGFORM>
//      <XMLDATA>
//          <SERIALVERSIONUID>1</SERIALVERSIONUID>
//          <SELFNAME>谢继珍</SELFNAME>
//          <GENDERCODE>2</GENDERCODE>
//          <GENDER>女</GENDER>
//          <BIRTHDAY>1954-08-14</BIRTHDAY>
//          <ADDRESS>古老背街道七里新村社区居委会湖北省宜昌市猇亭区云池街办云池居委会六组22</ADDRESS>
//          <SORT_TYPE>1,2</SORT_TYPE>
//      </XMLDATA>
//    </MSGFORM>

    public JSONObject getPatientInfo(String patientId){
        try {
            String[] groupName = {"未分拣", "高血压", "糖尿病", "结核病", "精神病", "老年人", "孕产妇", "儿童"};
            String info = NeuSoftWebService.getSignDetailInfoByChid(patientId);
            Document document = DocumentHelper.parseText(info);
            Element element = document.getRootElement().element("XMLDATA");
//            String chId = element.elementText("CHID");
            String serialversionuid = element.elementText("SERIALVERSIONUID");
            String selfName = element.elementText("SELFNAME");
            String gender = element.elementText("GENDER");
            String genderCode = element.elementText("GENDERCODE");
            String birthday = element.elementText("BIRTHDAY");
            String address = element.elementText("ADDRESS");
            String sortType = element.elementText("SORT_TYPE");
            String age = "";
            if(!StringUtils.isEmpty(birthday)){
                age= getAge(new Date(birthday.replace("-","/")))+"";
            }
            JSONObject json = new JSONObject();
            json.put("chId", patientId); //chId  居民主索引
            json.put("serialVersionUid",serialversionuid);
            json.put("name", selfName);              //姓名
            json.put("sex", genderCode);                    // 性别
            json.put("age", age);                     //年龄（出生日期处理得到）
            String sortTypeName = "";
            for(String str :sortType.split(",")){
                if(StringUtils.isEmpty(sortType)){
                    str = "0";
                }
                sortTypeName = groupName[Integer.parseInt(str)]+",";
            }
            json.put("sortType", sortTypeName.substring(0,sortTypeName.length()-1));
            json.put("address", address);      //常住地址
            json.put("birthday", birthday);     //出生日期
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
