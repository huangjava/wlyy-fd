package com.yihu.wlyy.services.doctor;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.xml.rpc.ServiceException;

import com.yihu.wlyy.util.SystemConf;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class TestDoctorWsClient {

    public static void main(String[] args) throws ServiceException, MalformedURLException, RemoteException {
        String endpoint = SystemConf.getInstance().getValue("neusoft.ws.doctor");

        // 创建一个服务（service）调用（call）
        Service service = new Service();
        Call call = (Call) service.createCall();
        // 设置service所在的url
        call.setTargetEndpointAddress(new URL(endpoint));

        String ret = getSignedInfoList(call);
        //String ret = getMyTeam(call);
        //String ret = login(call);
        //String ret = loginByID(call);
        //String ret = upDoctorInfo(call);
        //String ret = getSignDetailInfo(call);
        //String ret = getSignDetailInfoByChid(call);

        System.out.println(ret);
    }

    public static String getSignedInfoList(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getSignedInfoList");

        String inputXml4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <ORGCODE>420503003000</ORGCODE>\n" 
                + "  <USERID>9bf5afea-3200-4489-b93a-b5261351479e</USERID>\n"
//                + "  <PAGE>1</PAGE>\n"
                + "  <PAGESIZE></PAGESIZE>\n"
                + " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml4 });

//        String inputXml5 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <SIGNTEAM>1</SIGNTEAM>\n" 
//        + "  <SIGNTEAMNAME>2</SIGNTEAMNAME>\n" 
//        + "  <SIGNPERIOD>3</SIGNPERIOD>\n"
//        + "  <SIGNPERIODFROM>2016-09-09</SIGNPERIODFROM>\n"
//        + "  <CHID>e8d6c41b-bd8a-4701-8a4e-08e45efef939</CHID>\n"
//        + "  <AGREEMENTNAME>5</AGREEMENTNAME>\n"
//        + "  <ORGCODE>420503003000</ORGCODE>\n"
//        + "  <USERID>25e6cdd4-3b9f-4cd9-a072-a8dd351fb7ef</USERID>\n"
//        + " </QUERY_FORM>\n";
//        
//        DataHandler dh1 = null;
//        try {
//            dh1 = new DataHandler(new FileDataSource(new File("c:/1.jpg").getAbsoluteFile().getCanonicalPath()));
//        }
//        catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    
//        // 方法调用
//        String ret = (String) call.invoke(new Object[] {dh1, inputXml5 });
        return ret;
    }
    
    // 测试：4.1   获取当前医生参与的团队列表
    public static String getMyTeam(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getMyTeam");

        String inputXml4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <USER_NAME>qy</USER_NAME>\n" 
                + "  <ORGCODE>2c9660e34f4fbb9d014f5d50be6c0016</ORGCODE>\n"
                + "  <USERID>42589bc0-e069-4a92-a70c-c8a19be9d7d2</USERID>\n"
                + "  <PAGE>1</PAGE>\n"
                + "  <PAGESIZE>10</PAGESIZE>\n"
                + " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml4 });

        return ret;
    }
    
    // 测试：5.1   登陆验证
    public static String login(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("login");

        String inputXml4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <USER_NAME>qy</USER_NAME>\n" 
                + "  <PASS_WORD>1234</PASS_WORD>\n"
//                + "  <PAGE>1</PAGE>\n"
//                + "  <PAGESIZE>10</PAGESIZE>\n"
                + " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml4 });

        return ret;
    }
    
    // 测试：5.2  登陆验证(根据医生身份证)
    public static String loginByID(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("loginByID");

        String inputXml4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
                + "<QUERY_FORM>\n" 
                + "  <IDNUMBER>521236197407075566</IDNUMBER>\n" //521236197407075566
                + "  <LOGINKEY>JKZL</LOGINKEY>\n"//JKZL
                + " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml4 });

        return ret;
    }
    
    // 测试：6.1  提交医生个人信息
    public static String upDoctorInfo(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("upDoctorInfo");

        String inputXml4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
                + " <QUERY_FORM>\n" 
                + "  <USERID>422723195408143124</USERID>\n"
                + "  <USER_FULLNAME>422723195408143124</USER_FULLNAME>\n"
                + "  <GENDER>422723195408143124</GENDER>\n"
                + "  <PROFESSION>422723195408143124</PROFESSION>\n"
                + "  <EDUCATION>422723195408143124</EDUCATION>\n"
                + "  <SPECIALTY>422723195408143124</SPECIALTY>\n"
                + "  <UNIT_NAME>422723195408143124</UNIT_NAME>\n"
                + "  <DEPT_NAME>422723195408143124</DEPT_NAME>\n"
                + " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml4 });
        return ret;
    }
    
    // 测试：7.1 获取已签约的人员详细信息
    public static String getSignDetailInfo(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getSignDetailInfo");

        String inputXml4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
                + " <QUERY_FORM>\n" 
                + "  <OPENID>OCEF9T2HW1GBY0KINQK0NEL_ZOSK</OPENID>\n"//1,2
                + " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml4 });
        return ret;
    }
    
    // 测试：7.1 获取已签约的人员详细信息（根据居民主索引）
    public static String getSignDetailInfoByChid(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getSignDetailInfoByChid");

        String inputXml4 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
                + " <QUERY_FORM>\n" 
                + "  <CHID>bcc9e576-517f-4ed2-93d6-386b09f99184</CHID>\n"//1,2
//                + "  <CHID>2b78eb32-93ff-461c-a155-fd7889dd88e4</CHID>\n" //1
//                + "  <CHID>8143b81f-9e09-48af-b21e-fb14db568dc4</CHID>\n" //2
//                + "  <CHID>df405367-1b0e-4e13-99ba-d7fd4d4e721c</CHID>\n" //null
                + " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml4 });

        return ret;
    }
}
