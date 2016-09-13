package com.yihu.wlyy.services.doctor;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

/**
 * @created Airhead 2016/9/12.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = Application.class)
public class DoctorServiceTest {

    //    @Autowired
//    private DoctorService doctorService;
    private static DoctorService doctorService = new DoctorService();

    @Test
    public void getMyTeam() throws Exception {
        JSONArray info = doctorService.getMyTeam("2c9660e34f4fbb9d014f5d5453b8001b", "f93afa56-417c-4901-aeab-002ded330d86");
        System.out.println(info.toString());
    }

    @Test
    public void getInfo() throws Exception {
        JSONObject info = doctorService.getInfo("f93afa56-417c-4901-aeab-002ded330d86");
        System.out.println(info.toString());
    }
}