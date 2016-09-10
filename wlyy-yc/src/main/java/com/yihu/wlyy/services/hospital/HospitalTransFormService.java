package com.yihu.wlyy.services.hospital;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.util.XMLUtil;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  机构/团队业务类
 * @author HZY
 * @vsrsion 1.0
 * Created at 2016/9/10.
 */
@Service
public class HospitalTransFormService {

    /**
     * 获取团队列表
     * @param orgCode  机构代码
     * @return
     */
    public static List<Map<String,Object>> getTeamsByorgCode(String orgCode){
        List<Map<String,Object>> result = new ArrayList<>();
        //TODO 调用东软接口返回数据
        String responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MSGFORM>\n" +
                "  <XMLDATA>\n" +
                "    <TEAMID>2705143a-f84c-4a9d-9cf8-7fd0df5a759c</TEAMID>\n" +
                "    <TEAMNAME>糖尿病团队</TEAMNAME>\n" +
                "  </XMLDATA>\n" +
                "    <XMLDATA>\n" +
                "    <TEAMID>2705143a-f84c-4a9d-9cf8-7fd0df5a759c</TEAMID>\n" +
                "    <TEAMNAME>高血压团队</TEAMNAME>\n" +
                "  </XMLDATA>\n" +
                "</MSGFORM>\n";

        try {
            Map<String,Object> map  = XMLUtil.xml2map(responseXml);
            List<Map<String,Object>> teams = (List<Map<String, Object>>) map.get("XMLDATA");
            if (teams!=null && teams.size()>0){
                for (Map<String,Object> team:teams){
                    Map<String,Object> obj = new HashMap<String,Object>();
                    obj.put("name",team.get("TEAMNAME").toString());
                    obj.put("code",team.get("TEAMID").toString());
                    result.add(obj);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 根据团队主键获取团队详情信息
     * @param teamCode 团队主键
     * @return
     */
    public Map<String,Object> getTeamInfoByTeamCode(String teamCode){
        Map<String,Object> map = new HashMap<>();


        return map;
    }

    /**
     * 获取家庭医生详细信息
     * @param doctorCode
     * @return
     */
    public Map<String,Object> getDoctorInfo(String doctorCode){
        Map<String,Object> map = new HashMap<>();


        return map;
    }

    /**
     * 获取所有机构列表（根据居民微信主索引）
     * @param openId 微信主索引
     * @return
     */
    public Map<String,Object> getOrgsByOpenId(String openId){
        Map<String,Object> map = new HashMap<>();


        return map;
    }


    /**
     * 获取所有机构列表（根据居民地址）
     * @param district 区县ID
     * @param street    街道ID
     * @param community 居委会ID
     * @param address   详细地址
     * @return
     */
    public Map<String,Object> getOrgsByUserAddr(String district,String street,String community,String address){
        Map<String,Object> map = new HashMap<>();


        return map;
    }

    /**
     *  获取当前医生参与的团队列表（待定）
     * @param orgCode 机构编码
     * @param doctorCode  医生主索引
     * @return
     */
    public Map<String,Object> getTeamByDoctorCode(String orgCode,String doctorCode){
        Map<String,Object> map = new HashMap<>();


        return map;
    }

    public static void main(String[] args) {
        List<Map<String,Object> > map = getTeamsByorgCode("");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(map);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
