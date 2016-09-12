package com.yihu.wlyy.services.neusoft;

import com.yihu.wlyy.util.SystemConf;
import com.yihu.wlyy.util.XMLUtil;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//import org.springframework.stereotype.Service;

/**
 * @created Airhead 2016/9/4.
 */
@org.springframework.stereotype.Service
public class NeuSoftWebService {
    private static String doctorUrl = SystemConf.getInstance().getValue("neusoft.ws.doctor");
    private static String patientUrl = SystemConf.getInstance().getValue("neusoft.ws.person");
    private XMLUtil xmlUtil = new XMLUtil();

    //1.1 getGPTeamList  获取家庭医生团队列表
    //参数示例
    /*String paramXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <ORGCODE>2c9660e34f4fbb9d014f5d50be6c0016</ORGCODE>\n"
                    + " <PAGE>1</PAGE>\n"
                    + " <PAGESIZE>2</PAGESIZE>\n"
                    +  " </QUERY_FORM>\n";
    */
    public String getGPTeamList(String orgCode, String page, String pageSize) throws Exception {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("ORGCODE", orgCode);
            param.put("PAGE", page);
            param.put("PAGESIZE", pageSize);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getGPTeamList");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //1.2 getGPTeamInfo  1.2获取家庭医生团信息
    //参数示例
    /*
    String paramXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <TEAMID>74529931-1445-468d-8873-abfa63734e7c</TEAMID>\n"
                    + " <PAGE>1</PAGE>\n"
                    + " <PAGESIZE>2</PAGESIZE>\n"
                    +  " </QUERY_FORM>\n";
    */
    public String getGPTeamInfo(String teamId, String page, String pageSize) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("TEAMID", teamId);
            param.put("PAGE", page);
            param.put("PAGESIZE", pageSize);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getGPTeamInfo");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getGPInfo(String doctorId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("USERID", doctorId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getGPInfo");

            String res = (String) call.invoke(new Object[]{paramXml});
//            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String doSignApply(String selfName, String contactPhone, String appointmentSignDate, String signNo, String templeteId, String signTeam) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("SELFNAME", selfName);
            param.put("CONTACTPHONE", contactPhone);
            param.put("APPOINTMENTSIGNDATE", appointmentSignDate);
            param.put("SIGNNO", signNo);  //这个值是传递什么呢，界面上没有？
            param.put("TEMPLETED", templeteId); //这个值是什么呢？界面上没有。
            param.put("SIGNTEAM", signTeam);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("doSignApply");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSignState(String openId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("OPENID", openId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getSignState");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getOrgList(String district, String street, String community, String address) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("District", district);
            param.put("Street", street);
            param.put("Community", community);
            param.put("ADDRESS", address);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getOrgList");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getOrgListByOpenid(String openId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("OPENID", openId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getOrgListByOpenid");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO:有段代码说明需要处理
    public String getGPPhotoInfo(String userID) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("USERID", userID);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getGPPhotoInfo");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSignedInfoList(String orgCode, String userId, String page, String pageSize) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("ORGCODE", orgCode);
            param.put("USERID", userId);
            param.put("PAGE", page);
            param.put("PAGESIZE", pageSize);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getSignedInfoList");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getNotSignInfoList(String orgCode, String userId, String page, String pageSize) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("ORGCODE", orgCode);
            param.put("USERID", userId);
            param.put("PAGE", page);
            param.put("PAGESIZE", pageSize);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("getNotSignInfoList");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getToSignInfoList(String orgCode, String userId, String page, String pageSize) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("ORGCODE", orgCode);
            param.put("USERID", userId);
            param.put("PAGE", page);
            param.put("PAGESIZE", pageSize);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("getToSignInfoList");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String upConfirmSignedInfo(String signTeam, String signTeamName, String signPeriod, String signPreiodFrom, String chid, String agreementName, String orgCode, String userId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("SIGNTEAM", signTeam);
            param.put("SIGNTEAMNAME", signTeamName);
            param.put("SIGNPERIOD", signPeriod);
            param.put("SIGNPERIODFROM", signPreiodFrom);
            param.put("CHID", chid);
            param.put("AGREEMENTNAME", agreementName);
            param.put("ORGCODE", orgCode);
            param.put("USERID", userId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("upConfirmSignedInfo");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMyTeam(String orgCode, String userId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("SIGNTEAM", orgCode);
            param.put("SIGNTEAMNAME", userId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("getMyTeam");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String login(String userName, String password) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("USER_NAME", userName);
            param.put("PASS_WORD", password);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("login");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String loginByID(String idNumber, String longinKey) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("IDNUMBER", idNumber);
            param.put("LOGINKEY", longinKey);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("loginByID");

            String res = (String) call.invoke(new Object[]{paramXml});
            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
