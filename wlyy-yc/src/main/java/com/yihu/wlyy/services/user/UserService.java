package com.yihu.wlyy.services.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.daos.UserDao;
import com.yihu.wlyy.models.user.UserModel;
import com.yihu.wlyy.services.doctor.DoctorService;
import com.yihu.wlyy.util.SystemConf;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @created Airhead 2016/9/4.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private DoctorService doctorService;

    public UserModel get(String userCode) {
        return userDao.findOneByCode(userCode);
    }

    public Map<String, String> getExternal(String idCard) {
        Map<String, String> mapExternal = new HashMap<>();
        JSONObject identity = doctorService.loginByID(idCard, SystemConf.getInstance().getValue("neusoft.ws.key"));
        try {
            mapExternal.put("userId", identity.get("userId").toString());
            mapExternal.put("orgCode", identity.get("orgCode").toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapExternal;
    }
}
