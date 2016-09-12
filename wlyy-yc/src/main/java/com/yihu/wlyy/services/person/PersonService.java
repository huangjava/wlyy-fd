package com.yihu.wlyy.services.person;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

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

}
