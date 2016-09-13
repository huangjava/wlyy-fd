package com.yihu.wlyy.services.hospital;

import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
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
public class HospitalService {

    /**
     * 获取团队列表
     * @param orgCode  机构代码
     * @return
     */
    public static List<Map<String,Object>> getTeamsByorgCode(String orgCode, String page, String pageSize){
        List<Map<String,Object>> result = new ArrayList<>();
        try {
        String responseXml = NeuSoftWebService.getGPTeamList(orgCode,page,pageSize);

        //TODO 调用东软接口返回数据
//        responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<MSGFORM>\n" +
//                "  <XMLDATA>\n" +
//                "    <TEAMID>2705143a-f84c-4a9d-9cf8-7fd0df5a759c</TEAMID>\n" +
//                "    <TEAMNAME>糖尿病团队</TEAMNAME>\n" +
//                "  </XMLDATA>\n" +
//                "    <XMLDATA>\n" +
//                "    <TEAMID>2705143a-f84c-4a9d-9cf8-7fd0df5a759c</TEAMID>\n" +
//                "    <TEAMNAME>高血压团队</TEAMNAME>\n" +
//                "  </XMLDATA>\n" +
//                "</MSGFORM>\n";


            Map<String,Object> map  = XMLUtil.xml2map(responseXml);
            List<Map<String,Object>> teams =  XMLUtil.xmltoList(responseXml);
            if (teams!=null && teams.size()>0){
                for (Map<String,Object> team:teams){
                    Map<String,Object> obj = new HashMap<String,Object>();
                    obj.put("name", team.get("TEAMNAME"));
                    obj.put("code", team.get("TEAMID"));

                    //未提供
                    obj.put("hospital_name", ""); //机构名
//                    obj.put("expertise", "");
//                    obj.put("introduce","");
//                    obj.put("job_name", "");
                    obj.put("photo", "");   //团队头像
//                    obj.put("dept_name","");//科室名

                    result.add(obj);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
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
    public static Map<String,Object> getTeamInfoByTeamCode(String teamCode , String page, String pageSize){
        Map<String,Object> result = new HashMap<>();
        String responseXml = NeuSoftWebService.getGPTeamInfo(teamCode, page, pageSize);

        //TODO 调用东软接口返回数据
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
                result.put("introduce",obj.get("TEAMDESC"));
                //未给出
                result.put("orgName","");//toset
                result.put("photo","");//toset

            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * TODO 返回字段再设置
     * 获取家庭医生详细信息
     * @param doctorCode
     * @return
     */
    public Map<String,Object> getDoctorInfo(String doctorCode){
        Map<String,Object> result = new HashMap<>();
        //TODO 调用东软接口返回数据
        String responseXml = NeuSoftWebService.getGPInfo(doctorCode);
        responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MSGFORM>\n" +
                "  <XMLDATA>\n" +
                "    <USER_FULLNAME>张三</USER_FULLNAME>\n" +
                "    <GENDER>男</GENDER>\n" +
                "    <PROFESSION>职业经历</PROFESSION>\n" +
                "    <EDUCATION>硕士</EDUCATION>\n" +
                "    <SPECIALTY>检查检验</SPECIALTY>\n" +
                "    <UNIT_NAME>**社区</UNIT_NAME>\n" +
                "    <DEPT_NAME>检验科</DEPT_NAME>\n" +
                "    <PHOTO></PHOTO>\n" +
                "  </XMLDATA>\n" +
                "</MSGFORM>\n";

        try {
            List<Map<String,Object>> teams = XMLUtil.xmltoList(responseXml);
            if (teams!=null){
                Map<String,Object> obj = teams.get(0);
                result.put("doctorName",obj.get("USER_FULLNAME"));
                result.put("orgName",obj.get("UNIT_NAME"));
                result.put("introduce",obj.get("EDUCATION"));

                //未提供
                result.put("jobName","");//技术职称
                result.put("doctorCode","");
                result.put("teamCode","");
                result.put("teamName","");
                result.put("expertise","");//专业技术
                result.put("photo",obj.get("PHOTO"));//toset


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     *  TODO 待定，最新xml格式未给出
     * 获取所有机构列表（根据居民微信主索引）
     * @param openId 微信主索引
     * @return
     */
    public List<Map<String,Object>>  getOrgsByOpenId(String openId){
        List<Map<String,Object>> result = new ArrayList<>();
        //TODO 调用东软接口返回数据
        String responseXml = NeuSoftWebService.getOrgListByOpenid(openId);
        responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MSGFORM>\n" +
                "  <XMLDATA>\n" +
                "    <ORGCODE>2705143a-f84c-4a9d-9cf8-7fd0df5a759c</ORGCODE>\n" +
                "    <ORGNAME>**社区</ORGNAME>\n" +
                "  </XMLDATA>\n" +
                "</MSGFORM>\n";

        try {
            List<Map<String,Object>> teams =  XMLUtil.xmltoList(responseXml);
            if (teams!=null && teams.size()>0){
                for (Map<String,Object> team:teams){
                    Map<String,Object> obj = new HashMap<String,Object>();
                    obj.put("code", team.get("ORGCODE"));
                    obj.put("name", team.get("ORGNAME"));
                    result.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 获取所有机构列表（根据居民地址）
     * @param district 区县ID
     * @param street    街道ID
     * @param community 居委会ID
     * @param address   详细地址
     * @return
     */
    public List<Map<String,Object>> getOrgsByUserAddr(String district,String street,String community,String address){
        List<Map<String,Object>> result = new ArrayList<>();
        //TODO 调用东软接口返回数据
        String responseXml = NeuSoftWebService.getOrgList(community);
        responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<MSGFORM>\n" +
                "  <XMLDATA>\n" +
                "    <ORGCODE>2705143a-f84c-4a9d-9cf8-7fd0df5a759c</ORGCODE>\n" +
                "    <ORGNAME>**社区</ORGNAME>\n" +
                "  </XMLDATA>\n" +
                "</MSGFORM>\n";
        try {
            Map<String,Object> map  = XMLUtil.xml2map(responseXml);
            List<Map<String,Object>> teams = (List<Map<String, Object>>) map.get("XMLDATA");
            if (teams!=null && teams.size()>0){
                for (Map<String,Object> team:teams){
                    Map<String,Object> obj = new HashMap<String,Object>();
                    obj.put("code", team.get("ORGCODE"));
                    obj.put("name", team.get("ORGNAME"));
                    result.add(obj);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     *  TODO 见 DoctorService
     *  获取当前医生参与的团队列表（待定）
     * @param orgCode 机构编码
     * @param doctorCode  医生主索引
     * @return
     */
    public List<Map<String,Object>> getTeamByDoctorCode(String orgCode,String doctorCode){
        List<Map<String,Object>> result = new ArrayList<>();
        //TODO 调用东软接口返回数据
        String responseXml =  NeuSoftWebService.getMyTeam(orgCode, doctorCode);
        responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "  <MSGFORM>\n" +
                "    <XMLDATA>\n" +
                "       <TEAMID>420504202001</TEAMID>\n" +
                "       <TEAMNAME>高血压管理团队</TEAMNAME>\n" +
                "</XMLDATA>\n" +
                "    <XMLDATA>\n" +
                "       <TEAMID>420503003001</TEAMID>\n" +
                "       <TEAMNAME>签约团队</TEAMNAME>\n" +
                "    </XMLDATA>\n" +
                "  </MSGFORM>";

        try {
            Map<String,Object> map  = XMLUtil.xml2map(responseXml);
            List<Map<String,Object>> teams = (List<Map<String, Object>>) map.get("XMLDATA");
            if (teams!=null && teams.size()>0){
                for (Map<String,Object> team:teams){
                    Map<String,Object> obj = new HashMap<String,Object>();
                    obj.put("code", team.get("TEAMID"));
                    obj.put("name", team.get("TEAMNAME"));
                    result.add(obj);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return result;
    }

}
