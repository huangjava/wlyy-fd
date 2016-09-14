package com.yihu.wlyy.services.person;

import com.yihu.wlyy.services.neusoft.NeuSoftWebService;
import com.yihu.wlyy.util.CollectionUtil;
import com.yihu.wlyy.util.DateUtil;
import com.yihu.wlyy.util.StringUtil;
import com.yihu.wlyy.util.XMLUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @created Airhead 2016/9/12.
 */
@Service
public class PersonService {
    public String getInfoByOpenId(String openId) {
        try {

            JSONObject json = new JSONObject();
            // 有提供
            json.put("chId", openId); //chId  居民主索引
            json.put("name", "李六" + openId);              //姓名
            json.put("sex", "1");                    // 性别
            json.put("age", 65);                     //年龄（出生日期处理得到）
            json.put("sortType", "糖尿病,高血压,老年人");       //分拣标签
            json.put("address", "湖北省宜昌市");       //常住地址
            json.put("birthday", "1990-01-01");

            //TODO 未提供
            json.put("mobile", "15805926666");           //联系电话（未提供）
            json.put("idCard", "350581199002052852");
            json.put("tel", "0592-7651545");
            json.put("linkerTel", "0592-7651545");

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getInfo(String chId) {
        try {

            JSONObject json = new JSONObject();
            // 有提供
            json.put("chId", chId); //chId  居民主索引
            json.put("name", "李六" + chId);              //姓名
            json.put("sex", "1");                    // 性别
            json.put("age", 65);                     //年龄（出生日期处理得到）
            json.put("sortType", "糖尿病,高血压,老年人");       //分拣标签
            json.put("address", "湖北省宜昌市");       //常住地址
            json.put("birthday", "1990-01-01");

            //TODO 未提供
            json.put("mobile", "15805926666");           //联系电话（未提供）
            json.put("idCard", "350581199002052852");
            json.put("tel", "0592-7651545");
            json.put("linkerTel", "0592-7651545");

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     *  未给出返回格式
     * 我的资料
     * @param openId 微信主索引
     * @return
     */
    public static Map<String,Object> getBaseInfByOpenId(String openId){
        Map<String,Object> result = new HashMap<>();
        //TODO 调用东软接口返回数据
        openId = "OCEF9T2HW1GBY0KINQK0NEL_ZOSK";
        String userInfoXml = NeuSoftWebService.getSignDetailInfo(openId);
        String stateXml = NeuSoftWebService.getSignState(openId);
        try {
            result.put("sign", "0");
            List<Map<String,Object>> stateInfo = XMLUtil.xmltoList(stateXml);
            if (CollectionUtil.isNotEmpty(stateInfo)){
                Map<String,Object> obj = stateInfo.get(0);
                result.put("sign", obj.get("STATUS"));
                result.put("teamCode", obj.get("TEAMID"));
                result.put("teamName", obj.get("TEAMNAME"));
                result.put("orgCode", obj.get("ORGCODE"));
                result.put("orgName", obj.get("UNIT_NAME"));
            }

            Map<String, String> sortMap = new HashMap<>();
            sortMap.put("1", "高血压");
            sortMap.put("2", "糖尿病");
            sortMap.put("3", "结核病");
            sortMap.put("4", "精神病");
            sortMap.put("5", "老年人");
            sortMap.put("6", "孕产妇");
            sortMap.put("7", "儿童");
            List<Map<String,Object>> patientInfo = XMLUtil.xmltoList(userInfoXml);
            if (CollectionUtil.isNotEmpty(patientInfo)){
                Map<String,Object> obj = patientInfo.get(0);
                result.put("chId", "");
                result.put("name",obj.get("SELFNAME"));
                result.put("sex",obj.get("GENDER"));
                String birthday = StringUtil.toString(obj.get("BIRTHDAY"));
                result.put("age",DateUtil.getAgeByBirthday(DateUtil.strToDate(birthday, DateUtil.YYYY_MM_DD)));//toset
                String sortType = "";
                for (String type : StringUtil.toString(obj.get("SORT_TYPE")).split(",")) {
                    sortType = sortType + sortMap.get(type) + ",";
                }
                if (StringUtil.isNotEmpty(sortType)) {
                    sortType = sortType.substring(0, sortType.length() -1);
                }
                result.put("sortType", sortType);
                result.put("address", obj.get("ADDRESS"));       //常住地址
                result.put("birthday", obj.get("BIRTHDAY"));
                result.put("mobile", "");           //联系电话（未提供）
                result.put("idCard", "");
                result.put("tel", "");

                result.put("linkerTel", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
