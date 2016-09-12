package com.yihu.wlyy.services.neusoft;

import com.yihu.wlyy.util.SystemConf;
import com.yihu.wlyy.util.XMLUtil;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import javax.activation.DataHandler;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @created Airhead 2016/9/4.
 */
@org.springframework.stereotype.Service
public class NeuSoftWebService {
    //private static String doctorUrl = "http://172.22.224.117:8089/phis_hubei_ws/ws/doctorWsService?wsdl";
    //private static String patientUrl = "http://172.22.224.117:8089/phis_hubei_ws/ws/personWsService?wsdl";
    private static String doctorUrl = "http://219.139.130.106:8884/phis_hubei_ws/ws/doctorWsService?wsdl";
    private static String patientUrl = "http://219.139.130.106:8884/phis_hubei_ws/ws/personWsService?wsdl";

    private XMLUtil xmlUtil = new XMLUtil();

    //1.1 getGPTeamList  获取家庭医生团队列表
    //参数示例
    /*String paramXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <ORGCODE>2c9660e34f4fbb9d014f5d50be6c0016</ORGCODE>\n"
                    + " <PAGE>1</PAGE>\n"
                    + " <PAGESIZE>2</PAGESIZE>\n"
                    +  " </QUERY_FORM>\n";
    */
    public static String getGPTeamList(String orgCode, String page, String pageSize) throws Exception {
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
            //res = XMLUtil.xml2JSON(res);
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
    public static String getGPTeamInfo(String teamId, String page, String pageSize) {
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
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //1.3获取家庭医生信息  getGPInfo
    public static String getGPInfo(String doctorId) {
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

    //1.4签约申请 doSignApply
    public static String doSignApply(String selfName, String contactPhone, String appointmentSignDate, String signNo, String templeteId, String signTeam) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("SELFNAME", selfName);
            param.put("CONTACTPHONE", contactPhone);  //本人电话
            param.put("APPOINTMENTSIGNDATE", appointmentSignDate); //预约签约日
            param.put("SIGNNO", signNo);  //这个值是传递什么呢，界面上没有？ 签约编号
            param.put("TEMPLETED", templeteId); //这个值是什么呢？界面上没有。  模板类型
            param.put("SIGNTEAM", signTeam);    //签约团队
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("doSignApply");

            String res = (String) call.invoke(new Object[]{paramXml});
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //1.5获取签约状态 getSignState
    public static String getSignState(String openId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("OPENID", openId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getSignState");

            String res = (String) call.invoke(new Object[]{paramXml});
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //1.6获取所有机构列表（根据居民地址） getOrgList
    public static String getOrgList(String district, String street, String community, String address) {
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
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //1.7获取所有机构列表（根据居民微信主索引） getOrgListByOpenid
    public static String getOrgListByOpenid(String openId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("OPENID", openId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getOrgListByOpenid");

            String res = (String) call.invoke(new Object[]{paramXml});
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //1.8根据医生信息获取医生照片 getGPPhotoInfo
    public static void getGPPhotoInfo(String userID) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("USERID", userID);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getGPPhotoInfo");

            DataHandler[] ret = (DataHandler[])call.invoke(new Object[] { paramXml });
            byte[] b = new byte[ret[0].getInputStream().available()];
            ret[0].getInputStream().read(b);
            FileOutputStream out = new FileOutputStream("c:/pic.jpg"); //配置照片存放地址
            out.write(b);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3.1获取已签约的记录列表 getSignedInfoList
    public static String getSignedInfoList(String orgCode, String userId, String page, String pageSize) {
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
            call.setOperation("getSignedInfoList");

            String res = (String) call.invoke(new Object[]{paramXml});
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //3.2获取未签约的记录列表  getNotSignInfoList
    public static String getNotSignInfoList(String orgCode, String userId, String page, String pageSize) {
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
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //3.3获取待签约的记录列表 getToSignInfoList
    public static String getToSignInfoList(String orgCode, String userId, String page, String pageSize) {
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
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //3.4提交确认签约信息 upConfirmSignedInfo
    //TODO DataHandler的生成逻辑要考虑
    public static String upConfirmSignedInfo(DataHandler dataHandler, String signTeam, String signTeamName, String signPeriod, String signPreiodFrom, String chid, String agreementName, String orgCode, String userId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("SIGNTEAM", signTeam);
            param.put("SIGNTEAMNAME", signTeamName);
            param.put("SIGNPERIOD", signPeriod);  //签约周期
            param.put("SIGNPERIODFROM", signPreiodFrom); //签约日期
            param.put("CHID", chid);   //居民主索引
            param.put("AGREEMENTNAME", agreementName);  //上传协议名称
            param.put("ORGCODE", orgCode); //医生所属机构编
            param.put("USERID", userId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("upConfirmSignedInfo");

            String res = (String) call.invoke(new Object[]{dataHandler,paramXml});
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //4.1获取当前医生参与的团队列表  getMyTeam
    public static String getMyTeam(String orgCode, String userId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("ORGCODE", orgCode);
            param.put("USERID", userId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("getMyTeam");

            String res = (String) call.invoke(new Object[]{paramXml});
//            res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //
    public static String login(String userName, String password) {
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
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //5.2登陆验证(根据医生身份证)  loginByID
    public static String loginByID(String idNumber, String longinKey) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("IDNUMBER", idNumber);  //医生身份证
            param.put("LOGINKEY", longinKey); //健康之路登陆验证key
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("loginByID");

            String res = (String) call.invoke(new Object[]{paramXml});
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //7.1获取已签约的人员详细信息  getSignDetailInfo
    public static String getSignDetailInfo(String openId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("OPENID", openId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(patientUrl));
            call.setOperation("getSignDetailInfo");

            String res = (String) call.invoke(new Object[]{paramXml});
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
