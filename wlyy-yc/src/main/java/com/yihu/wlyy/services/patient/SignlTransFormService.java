package com.yihu.wlyy.services.patient;

import com.yihu.wlyy.util.XMLUtil;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  签约相关业务类
 * @author HZY
 * @vsrsion 1.0
 * Created at 2016/9/10.
 */
@Component
public class SignlTransFormService {

    /**
     * 获取签约状态
     * @param openId  微信主索引
     * @return
     */
    public static List<Map<String,Object>> getSignStatus(String openId){
        List<Map<String,Object>> result = new ArrayList<>();
        //TODO 调用东软接口返回数据
        String responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MSGFORM>\n" +
                "   <XMLDATA>\n" +
                "     <RESULTFLAG>1</RESULTFLAG>\n" +
                "   </XMLDATA>\n" +
                "</MSGFORM>\n";

        try {
            List<Map<String,Object>> teams =  XMLUtil.xmltoList(responseXml);
            if (teams!=null && teams.size()>0){
                for (Map<String,Object> team:teams){
                    Map<String,Object> obj = new HashMap<String,Object>();
                    obj.put("name", team.get("TEAMNAME"));
                    obj.put("code", team.get("TEAMID"));
                    result.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 根据团队主键获取团队详情信息
     * @param teamCode 团队主键
     * @return
     */
    public static Map<String,Object> getTeamInfoByTeamCode(String teamCode){
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
            Map<String,Object> map  = XMLUtil.xml2map(responseXml);
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
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return result;
    }

}
