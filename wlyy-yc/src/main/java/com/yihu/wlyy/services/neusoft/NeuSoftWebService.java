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
    private static String doctorUrl = SystemConf.getInstance().getValue("neusoft.ws.doctor");
    private static String patientUrl = SystemConf.getInstance().getValue("neusoft.ws.person");
    private XMLUtil xmlUtil = new XMLUtil();

    //1.1 getGPTeamList  获取家庭医生团队列表  -- 调通
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


    //1.2 getGPTeamInfo  1.2获取家庭医生团信息  -- 调通
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

    //1.3获取家庭医生信息  getGPInfo  -- 调通
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

    //1.4签约申请 doSignApply --  未调通  ----
    public static String doSignApply(String selfName, String contactPhone,String idnumber, String appointmentSignDate,String templeteId, String signTeam) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("SELFNAME", selfName);
            param.put("CONTACTPHONE", contactPhone);  //本人电话
            param.put("IDNUMBER", idnumber);    //签约团队
            param.put("APPOINTMENTSIGNDATE", appointmentSignDate); //预约签约日
            param.put("SIGNTEAM", signTeam);    //签约团队
            param.put("TEMPLETEID", templeteId);
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

    //1.5获取签约状态 getSignState -- 未调通
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

    //1.6获取所有机构列表（根据居民地址） getOrgList  -- 未开发
    public static String getOrgList(String community) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("COMMUNITY", community);
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

    //1.7获取所有机构列表（根据居民微信主索引） getOrgListByOpenid  -- 未发布
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

    //1.8根据医生信息获取医生照片 getGPPhotoInfo  -- 未调通
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

    //3.1获取已签约的记录列表 getSignedInfoList    -- 调通
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

    //3.2获取未签约的记录列表  getNotSignInfoList    -- 调通
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

    //3.3获取待签约的记录列表 getToSignInfoList  -- 调通
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

    //3.4提交确认签约信息 upConfirmSignedInfo  -- 未调通
    public static String upConfirmSignedInfo(DataHandler dataHandler, String signTeam, String signTeamName, String signPeriod, String signPreiodFrom, String chid, String agreementName, String orgCode, String userId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("SIGNTEAM", signTeam); //签约团队信息
            param.put("SIGNTEAMNAME", signTeamName);  //置空
            param.put("SIGNPERIOD", signPeriod);  //签约周期 默认按年默认1年
            param.put("SIGNPERIODFROM", signPreiodFrom); //签约日期 当天
            param.put("CHID", chid);   //居民主索引
            param.put("AGREEMENTNAME", agreementName);  //上传协议名称
            param.put("ORGCODE", orgCode); //医生所属机构编号
            param.put("USERID", userId); //医生ID
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

    //4.1获取当前医生参与的团队列表  getMyTeam -- 调通
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

    //5.2登陆验证(根据医生身份证)  loginByID  -- 调通
    public static String loginByID(String idNumber, String loginKey) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("IDNUMBER", idNumber);  //医生身份证
            param.put("LOGINKEY", loginKey); //健康之路登陆验证key
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

    //7.1获取人员详细信息by openId  getSignDetailInfo -- 未开发
    public static String getSignDetailInfo(String openId) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("OPENID", openId);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("getSignDetailInfo");

            String res = (String) call.invoke(new Object[]{paramXml});
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //7.2获取人员详细信息by chid  getSignDetailInfoByChid -- 未开发
    public static String getSignDetailInfoByChid(String chid) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("CHID", chid);
            String paramXml = XMLUtil.map2xml(param);

            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(doctorUrl));
            call.setOperation("getSignDetailInfoByChid");

            String res = (String) call.invoke(new Object[]{paramXml});
            //res = XMLUtil.xml2JSON(res);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
