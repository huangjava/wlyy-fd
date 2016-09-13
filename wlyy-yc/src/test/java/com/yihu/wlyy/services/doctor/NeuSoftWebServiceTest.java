package com.yihu.wlyy.services.doctor;

import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import org.junit.Test;

public class NeuSoftWebServiceTest {

    private static NeuSoftWebService neuSoftWebService = new NeuSoftWebService();

    //1.1 getGPTeamList  获取家庭医生团队列表
    //@Test
    public void getGPTeamList() throws Exception {
        String info = neuSoftWebService.getGPTeamList("2c9660e34f4fbb9d014f5d50be6c0016","1","10");
        System.out.println(info);
    }

    //1.2 getGPTeamInfo  1.2获取家庭医生团信息
    //@Test
    public void getGPTeamInfo() throws Exception {
        String info = neuSoftWebService.getGPTeamInfo("74529931-1445-468d-8873-abfa63734e7c", "1", "10");
        System.out.println(info);
    }

    //1.3获取家庭医生信息  getGPInfo
    //@Test
    public void getGPInfo() throws Exception {
        String info = neuSoftWebService.getGPInfo("a569522f-49d9-46ea-8209-1406e04787ea");
        System.out.println(info);
    }

    //1.4签约申请 doSignApply  -- 未通过
    @Test
    public void doSignApply() throws Exception {
        String info = neuSoftWebService.doSignApply("林霖","13889457565","2016-09-08","125","Dummy007","09549d72-0511-48ac-b0af-b8453cc2681a");
        System.out.println(info);
    }

    //1.5获取签约状态 getSignState -- 未通过
    @Test
    public void getSignState() throws Exception {
        String info = neuSoftWebService.getSignState("OCEF9T2HW1GBY0KINQK0NEL_ZOSK");
        System.out.println(info);
    }

    //1.6获取所有机构列表（根据居民地址） getOrgList -- 未开发
    //@Test
    public void getOrgList() throws Exception {
        String info = neuSoftWebService.getOrgList("420502","420502001","420502001001","");
        //西陵区  420502  西陵街道  420502001  土街头社区居委会   420502001001
        System.out.println(info);
    }

    //1.7获取所有机构列表（根据居民微信主索引） getOrgListByOpenid - 未通过
    @Test
    public void getOrgListByOpenid() throws Exception {
        String info = neuSoftWebService.getOrgListByOpenid("OCEF9T2HW1GBY0KINQK0NEL_ZOSK");
        System.out.println(info);
    }

    //1.8根据医生信息获取医生照片 getGPPhotoInfo  -- 未通过
    //TODO
    @Test
    public void getGPPhotoInfo() throws Exception {
        neuSoftWebService.getGPPhotoInfo("a569522f-49d9-46ea-8209-1406e04787ea");

    }

    //3.1获取已签约的记录列表 getSignedInfoList  -- 通过
    //@Test
    public void getSignedInfoList() throws Exception {
        String info = neuSoftWebService.getSignedInfoList("420503003000","9bf5afea-3200-4489-b93a-b5261351479e","1","10");
        System.out.println(info);
    }

    //3.2获取未签约的记录列表  getNotSignInfoList  -- 通过
    //@Test
    public void getNotSignInfoList() throws Exception {
        String info = neuSoftWebService.getNotSignInfoList("420503003000","9bf5afea-3200-4489-b93a-b5261351479e","1","10");
        System.out.println(info);
    }

    //3.3获取待签约的记录列表 getToSignInfoList -- 通过
    //@Test
    public void getToSignInfoList() throws Exception {
        String info = neuSoftWebService.getToSignInfoList("420503003000","9bf5afea-3200-4489-b93a-b5261351479e","1","10");
        System.out.println(info);
    }

    //3.4提交确认签约信息 upConfirmSignedInfo -- 未通过
    @Test
    public void upConfirmSignedInfo() throws Exception {
        String info = neuSoftWebService.getToSignInfoList("1234","420505198104127042","1","10");
        System.out.println(info);
    }

    //4.1获取当前医生参与的团队列表  getMyTeam
    @Test
    public void getMyTeam() throws Exception {
        String info = neuSoftWebService.getMyTeam("2c9660e34f4fbb9d014f5d5453b8001b", "f93afa56-417c-4901-aeab-002ded330d86");
        System.out.println(info);
    }

    //5.2登陆验证(根据医生身份证)  loginByID -- 未通过
    @Test
    public void loginByID() throws Exception {
        String info = neuSoftWebService.loginByID("521236197407075566", "JKZL");
        System.out.println(info);
    }

    //7.1获取已签约的人员详细信息
    /*@Test
    public void getSignDetailInfo() throws Exception {
        String info = neuSoftWebService.getSignDetailInfo("OCEF9T2HW1GBY0KINQK0NEL_ZOSK");
        System.out.println(info);
    }*/
}