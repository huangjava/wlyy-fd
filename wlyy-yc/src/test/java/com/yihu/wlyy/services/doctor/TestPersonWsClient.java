package com.yihu.wlyy.services.doctor;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import javax.activation.DataHandler;
import javax.xml.rpc.ServiceException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;

public class TestPersonWsClient {

    public static void main(String[] args) throws ServiceException, MalformedURLException, RemoteException {

        String endpoint = "http://localhost:8080/appws/ws/personWsService?wsdl";

        // 创建一个服务（service）调用（call）
        Service service = new Service();
        Call call = (Call) service.createCall();

        // 设置service所在的url
        call.setTargetEndpointAddress(new URL(endpoint));

        String ret = callgetSignState(call);
        System.out.println(ret);
//        try {
//            callGetGPPhotoInfo(call);
//        }
//        catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        String ret = callGetGPTeamInfoService(call);
//       String ret = calldoSignApply(call);//1.4
//       String ret = callgetSignState(call);//1.5
//       String ret = callgetFollowUpDiabetesInfo(call);//2.1
///       String ret = callgetFollowUpBloodInfo(call);//2.2
//
//        System.out.println(ret);
    }

    // 1.1
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
    
    // 1.2
    public static String callGetGPTeamInfoService(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getGPTeamInfo");

        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <TEAMID>74529931-1445-468d-8873-abfa63734e7c</TEAMID>\n" 
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }
    
    // 1.3
    public static String callgetGPInfoService(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getGPInfo");

        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <USERID>a569522f-49d9-46ea-8209-1406e04787ea</USERID>\n" 
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }
    
    // 1.4
    public static String calldoSignApply(Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("doSignApply");

        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
                + "<QUERY_FORM>\n" 
                + " <SELFNAME>小黄人儿</SELFNAME>\n" 
                + " <CONTACTPHONE>13889457565</CONTACTPHONE>\n" 
                + " <APPOINTMENTSIGNDATE>2016-09-08</APPOINTMENTSIGNDATE>\n" 
                + " <SIGNNO>123</SIGNNO>\n" 
                + " <TEMPLETEID>Dummy007</TEMPLETEID>\n" 
                + " <SIGNTEAM>09549d72-0511-48ac-b0af-b8453cc2681a</SIGNTEAM>\n" 
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }
    
    // 1.5
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
    
    // 1.7
    public static String callgetOrgListByOpenid (Call call) throws ServiceException, MalformedURLException, RemoteException {

        // 方法名
        call.setOperation("getOrgListByOpenid ");
        // 420505200103297323
        String inputXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<QUERY_FORM>\n" + " <OPENID>OCEF9T2HW1GBY0KINQK0NEL_ZOSK</OPENID>\n" 
                +  " </QUERY_FORM>\n";
        // 方法调用
        String ret = (String) call.invoke(new Object[] { inputXml });

        return ret;
    }
    
    // 1.8
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
    
    // 2.1
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
    
    // 2.2
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
