package com.yihu.wlyy.services.doctor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

import javax.activation.DataHandler;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class TestPersonWsClient {

    public static void main(String[] args) throws ServiceException, MalformedURLException, RemoteException {

        String endpoint = "http://localhost:8080/appws/ws/personWsService?wsdl";

        // 创建一个服务（service）调用（call）
        Service service = new Service();
        Call call = (Call) service.createCall();

        // 设置service所在的url
        //call.setTargetEndpointAddress(new URL(endpoint));
//        String ret = callGetGPTeamListtService(call);//1.1
//        String ret = callGetGPTeamInfoService(call);//1.2
//        String ret = callgetGPInfoService(call);//1.3
        String ret = calldoSignApply(call);//1.4
//        String ret = callgetSignState(call);//1.5
//        String ret = getOrgList(call);//1.6
//        String ret = callgetOrgListByOpenid(call);//1.7
//        String ret = callgetFollowUpDiabetesInfo(call);//2.1
          //String ret = callgetFollowUpBloodInfo(call);//2.2
//
        System.out.println(ret);

//      1.8    获取家庭医生照片
//      try {
//          callGetGPPhotoInfo(call);
//      }
//      catch (IOException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }
    }

    // 1.1  获取家庭医生团队列表
    public static String callGetGPTeamListtService(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getGPTeamList");

        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <ORGCODE>2c9660e34f4fbb9d014f5d50be6c0016</ORGCODE>\n"
                + " <PAGE>1</PAGE>\n"
                + " <PAGESIZE>2</PAGESIZE>\n"
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }

    // 1.2  获取家庭医生团信息
    public static String callGetGPTeamInfoService(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getGPTeamInfo");

        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <TEAMID>74529931-1445-468d-8873-abfa63734e7c</TEAMID>\n"
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }

    // 1.3  获取家庭医生信息
    public static String callgetGPInfoService(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getGPInfo");

        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <USERID>a569522f-49d9-46ea-8209-1406e04787ea</USERID>\n"
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }

    // 1.4  签约申请
    public static String calldoSignApply(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("doSignApply");

        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<QUERY_FORM>\n"
                + " <SELFNAME>小黄人儿22</SELFNAME>\n"
                + " <CONTACTPHONE>13889457565</CONTACTPHONE>\n"
                + " <IDNUMBER>13889457565</IDNUMBER>\n"
                + " <APPOINTMENTSIGNDATE>2016-09-08</APPOINTMENTSIGNDATE>\n"
                + " <TEMPLETEID>Dummy007</TEMPLETEID>\n"
                + " <SIGNTEAM>09549d72-0511-48ac-b0af-b8453cc2681a</SIGNTEAM>\n"
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }

    // 1.5  获取签约状态
    public static String callgetSignState(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getSignState");
        // 420505200103297323
        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <OPENID>OCEF9T2HW1GBY0KINQK0NEL_ZOSK</OPENID>\n"
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }

    // 1.6  获取所有机构列表（根据居民地址）
    public static String getOrgList(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getOrgList");
        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <COMMUNITY>23e78f86-d38c-41e8-a75a-add79a4b2723</COMMUNITY>\n"
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }

    // 1.7  获取所有机构列表（根据居民地址）
    public static String callgetOrgListByOpenid (Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getOrgListByOpenid");
        // 420505200103297323
        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <OPENID>OCEF9T2HW1GBY0KINQK0NEL_ZOSK</OPENID>\n"
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }

    // 1.8  获取家庭医生照片
    public static void callGetGPPhotoInfo(Call call) throws ServiceException, IOException {

        // 方法名
        call.setOperation("getGPPhotoInfo");

        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <USERID>a569522f-49d9-46ea-8209-1406e04787ea</USERID>\n"
                +  " </QUERY_FORM>\n";
        DataHandler[] ret = (DataHandler[])call.invoke(new Object[] { inputXml });

        byte[] b = new byte[ret[0].getInputStream().available()];
        ret[0].getInputStream().read(b);
        FileOutputStream out = new FileOutputStream("c:/pic.jpg");
        out.write(b);
        out.flush();
        out.close();
    }

    // 2.1  获取居民糖尿病随访记录
    public static String callgetFollowUpDiabetesInfo(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getFollowUpDiabetesInfo");

        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<QUERY_FORM>\n"
                + " <OPENID>120109194905016532</OPENID>\n"
                + " <PAGE>1</PAGE>\n"
                + " <PAGESIZE>2</PAGESIZE>\n"
                + " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }

    // 2.2  获取居民高血压随访记录
    public static String callgetFollowUpBloodInfo(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getFollowUpBloodInfo");

        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<QUERY_FORM>\n"
                + " <OPENID>110105193506258313</OPENID>\n"
                + " <PAGE />\n"
                + " <PAGESIZE>2</PAGESIZE>\n"
                + " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }
}
