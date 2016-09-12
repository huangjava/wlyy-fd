package com.yihu.wlyy.services.person;

import com.yihu.wlyy.util.XMLUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @created Airhead 2016/9/12.
 */
@Service
public class PersonService {
    public String getInfoByOpenId(String openId) {
        try {

            JSONObject json = new JSONObject();
            // 有提供
            json.put("chId", openId); //chId  居民主索引
            json.put("name", "李六" + openId);              //姓名
            json.put("sex", "1");                    // 性别
            json.put("age", 65);                     //年龄（出生日期处理得到）
            json.put("sortType", "糖尿病,高血压,老年人");       //分拣标签
            json.put("address", "湖北省宜昌市");       //常住地址
            json.put("birthday", "1990-01-01");

            //TODO 未提供
            json.put("mobile", "15805926666");           //联系电话（未提供）
            json.put("idCard", "350581199002052852");
            json.put("tel", "0592-7651545");
            json.put("linkerTel", "0592-7651545");

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getInfo(String chId) {
        try {

            JSONObject json = new JSONObject();
            // 有提供
            json.put("chId", chId); //chId  居民主索引
            json.put("name", "李六" + chId);              //姓名
            json.put("sex", "1");                    // 性别
            json.put("age", 65);                     //年龄（出生日期处理得到）
            json.put("sortType", "糖尿病,高血压,老年人");       //分拣标签
            json.put("address", "湖北省宜昌市");       //常住地址
            json.put("birthday", "1990-01-01");

            //TODO 未提供
            json.put("mobile", "15805926666");           //联系电话（未提供）
            json.put("idCard", "350581199002052852");
            json.put("tel", "0592-7651545");
            json.put("linkerTel", "0592-7651545");

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     *  未给出返回格式
     * 我的资料
     * @param openId 微信主索引
     * @return
     */
    public static Map<String,Object> getBaseInfByOpenId(String openId){
        Map<String,Object> result = new HashMap<>();
        //TODO 调用东软接口返回数据
        String responseXml = null;
        responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MSGFORM>\n" +
                "  <XMLDATA>\n" +
                "    <TEAMID>74529931-1445-468d-8873-abfa63734e7c</TEAMID>\n" +
                "    <TEAMNAME>高血压团队</TEAMNAME>\n" +
                "    <ORGCODE>123456</ORGCODE>\n" +
                "    <CREATEDUNITNAME>宜昌市惠民医院</CREATEDUNITNAME>\n" +
                "    <CREATEDUNITCODE>420503003000</CREATEDUNITCODE>\n" +
                "    <CREATEDTIME>2016-08-10</CREATEDTIME>\n" +
                "    <TEAMDESC>团队简介</TEAMDESC>\n" +
                "    <USERID>42589bc0-e069-4a92-a70c-c8a19be9d7d2</USERID>\n" +
                "    <USER_FULLNAME>宋文</USER_FULLNAME>\n" +
                "    <DEPT_NAME>儿科</DEPT_NAME>\n" +
                "  </XMLDATA>\n" +
                "  <XMLDATA>\n" +
                "    <TEAMID>74529931-1445-468d-8873-abfa63734e7c</TEAMID>\n" +
                "    <TEAMNAME>高血压团队</TEAMNAME>\n" +
                "    <ORGCODE>123456</ORGCODE>\n" +
                "    <CREATEDUNITNAME>宜昌市惠民医院</CREATEDUNITNAME>\n" +
                "    <CREATEDUNITCODE>420503003000</CREATEDUNITCODE>\n" +
                "    <CREATEDTIME>2016-08-10</CREATEDTIME>\n" +
                "    <TEAMDESC>团队简介</TEAMDESC>\n" +
                "    <USERID>42589bc0-e069-4a92-a70c-c8a19be9d333</USERID>\n" +
                "    <USER_FULLNAME>王明</USER_FULLNAME>\n" +
                "    <DEPT_NAME>儿科</DEPT_NAME>\n" +
                "  </XMLDATA>\n" +
                "</MSGFORM>\n";

        try {
            List<Map<String,Object>> teams = XMLUtil.xmltoList(responseXml);
            if (teams!=null && teams.size()>0){
                Map<String,Object> obj = teams.get(0);
                result.put("teamCode",obj.get("TEAMID"));
                result.put("teamName",obj.get("TEAMNAME"));
                result.put("orgCode",obj.get("ORGCODE"));
                result.put("orgName",obj.get("TEAMNAME"));//toset
                result.put("introduce",obj.get("TEAMDESC"));
                result.put("photo",obj.get("TEAMNAME"));//toset

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
