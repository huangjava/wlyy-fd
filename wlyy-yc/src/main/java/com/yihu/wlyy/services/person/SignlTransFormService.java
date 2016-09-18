package com.yihu.wlyy.services.person;

import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import com.yihu.wlyy.util.XMLUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 签约相关业务类
 *
 * @author HZY
 * @vsrsion 1.0
 * Created at 2016/9/10.
 */
@Component
public class SignlTransFormService {

    /**
     * 获取签约状态
     *
     * @param openId 微信主索引
     * @return
     */
    //        responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                " <MSGFORM>\n" +
//                "   <XMLDATA>\n" +
//                "       <STATUS>1</STATUS>\n" +
//                "       <TEAMID>420505001002</TEAMID>\n" +
//                "       <TEAMNAME>高血压团队</TEAMNAME>\n" +
//                "       <ORGCODE>420505001000</ORGCODE>\n" +
//                "       <CREATEDUNITNAME>古老背社区卫生服务中心</CREATEDUNITNAME>\n" +
//                "       < CREATEDUNITCODE> 420505001000</CREATEDUNITCODE>\n" +
//                "       < CREATEDTIME>2016-01-01</ CREATEDTIME>\n" +
//                "       < TEAMDESC>高血压团队简介</ TEAMDESC>\n" +
//                "       < UNIT_NAME>古老背社区卫生服务中心</ UNIT_NAME>\n" +
//                "    </XMLDATA>\n" +
//                "  </MSGFORM>\n";
    public static Map<String, Object> getSignState(String openId) {
        Map<String, Object> result = new HashMap<>();
        try {
            //TODO 调用东软接口返回数据
            String responseXml = NeuSoftWebService.getSignState(openId);
            List<Map<String, Object>> teams = XMLUtil.xmltoList(responseXml);
            if (teams != null && teams.size() > 0) {
                Map<String, Object> obj = teams.get(0);
                result.put("signStatus", obj.get("STATUS"));//签约状态
                result.put("teamCode", obj.get("TEAMID"));
                result.put("teamName", obj.get("TEAMNAME"));
                result.put("orgCode", obj.get("ORGCODE"));
                result.put("orgName", obj.get("UNIT_NAME"));
                result.put("desc", obj.get("TEAMDESC"));
                //创建单位
                result.put("createDate", obj.get("CREATEDTIME"));
                result.put("createDunitCode", obj.get("CREATEDUNITCODE"));
                result.put("createDunitName", obj.get("CREATEDUNITNAME"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 根据团队主键获取团队详情信息
     *
     * @param selfName            居民名称
     * @param contactPhone        本人电话
     * @param appointmentSignDate 签约日期
     * @param signTeam
     * @return
     */
    public static Map<String, Object> doSignApply(String selfName, String contactPhone, String appointmentSignDate, String idnumber, String signTeam) {
        Map<String, Object> result = new HashMap<>();
        String responseXml = NeuSoftWebService.doSignApply(selfName, null, null, null, null, signTeam);

        //TODO 调用东软接口返回数据
//        responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
//                "<MSGFORM>\n" +
//                "   <XMLDATA>\n" +
//                "     <RESULTFLAG>1</RESULTFLAG>\n" +
//                "   </XMLDATA>\n" +
//                "</MSGFORM>\n";

        try {
            List<Map<String, Object>> teams = XMLUtil.xmltoList(responseXml);
            if (teams != null && teams.size() > 0) {
                Map<String, Object> obj = teams.get(0);
                result.put("result", obj.get("RESULTFLAG"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
