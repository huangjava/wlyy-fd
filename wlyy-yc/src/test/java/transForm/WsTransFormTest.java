package transForm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.services.doctor.DoctorService;
import com.yihu.wlyy.services.hospital.HospitalService;
import com.yihu.wlyy.services.person.SignlTransFormService;
import org.json.JSONArray;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author HZY
 * @vsrsion 1.0
 * Created at 2016/7/28.
 */
public class WsTransFormTest {
    private static HospitalService service = new HospitalService();

    @Test
    public void getOrgsByOpenId(){
        List<Map<String,Object> > list = service.getOrgsByOpenId("OCEF9T2HW1GBY0KINQK0NEL_ZOSK");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(list);
            System.out.println(json);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getMyTeam() throws Exception {
        DoctorService doctorService = new DoctorService();
        JSONArray info = doctorService.getMyTeam("2c9660e34f4fbb9d014f5d5453b8001b", "f93afa56-417c-4901-aeab-002ded330d86");
        System.out.println(info.toString());
    }



    @Test
    public void getOrgList() throws Exception {
        List<Map<String,Object>>   info = service.getOrgsByUserAddr("420502", "420502001", "420502001001", "");
        System.out.println(info);
    }


    @Test
    public void doSignApply() throws Exception {
        SignlTransFormService service = new SignlTransFormService();
        Map<String,Object>  info = service.doSignApply("林霖","17112345678","350425198507080016","2016-09-08","b825206f-6af4-4009-b9bc-7a9c71f820d5");
        System.out.println(info);
    }

    @Test
    public void getSignState() throws Exception {
        SignlTransFormService service = new SignlTransFormService();
        Map<String,Object> info = service.getSignState("OCEF9T2HW1GBY0KINQK0NEL_ZOSK");
        System.out.println(info);
    }

    @Test
    public void testgetDoctorInfo(){
        Map<String,Object>  list = service.getDoctorInfo("a569522f-49d9-46ea-8209-1406e04787ea");
        System.out.println(list);
    }

    @Test
    public void testgetTeamInfoByTeamCode(){
        Map<String,Object>  list = service.getTeamInfoByTeamCode("74529931-1445-468d-8873-abfa63734e7c", "1", "10");
        System.out.println(list);
    }

    @Test
    public void testgetTeamsByorgCode(){
        List<Map<String,Object>> list = service.getTeamsByorgCode("2c9660e34f4fbb9d014f5d50be6c0016","1","10");
        System.out.println(list);
    }


}
