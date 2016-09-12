package transForm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.services.hospital.HospitalService;
import com.yihu.wlyy.util.XMLUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author HZY
 * @vsrsion 1.0
 * Created at 2016/7/28.
 */
public class WsTransFormTest {

    @Test
    public void getTeamInfoByTeamCode() throws Exception{
        String responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\t<QUERY_FORM>\n" +
                "\t  <SELFNAME>林霖</SELFNAME>\n" +
                "\t  <CONTACTPHONE>17112345678</CONTACTPHONE>\n" +
                "\t  <APPOINTMENTSIGNDATE>2016-09-08</APPOINTMENTSIGNDATE>\n" +
                "\t  <SIGNNO>123</SIGNNO>\n" +
                "\t  <TEMPLETEID>Dummy001</TEMPLETEID>\n" +
                "\t  <SIGNTEAM>09549d72-0511-48ac-b0af-b8453cc2681a</SIGNTEAM>\n" +
                "\t</QUERY_FORM>\n";
//                List<Map<String,Object> > list = XMLUtil.xmltoList(responseXml);
        Map<String ,Object> map = XMLUtil.xmltoMap(responseXml);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
//            String json = objectMapper.writeValueAsString(list);
//            System.out.println(json);

            String json = objectMapper.writeValueAsString(map);
            System.out.println(json);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetOrgsByOpenId(){
        HospitalService service = new HospitalService();
        List<Map<String,Object> > list = service.getOrgsByOpenId("");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(list);
            System.out.println(json);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testgetDoctorInfo(){
        HospitalService service = new HospitalService();
        Map<String,Object>  list = service.getDoctorInfo("");
        System.out.println(list);
    }

    @Test
    public void testgetTeamInfoByTeamCode(){
        HospitalService service = new HospitalService();
        Map<String,Object>  list = service.getTeamInfoByTeamCode("","","");
        System.out.println(list);
    }

    @Test
    public void testgetTeamsByorgCode(){
        HospitalService service = new HospitalService();
        List<Map<String,Object> >  list = service.getTeamsByorgCode("","","");
        System.out.println(list);
    }


}
