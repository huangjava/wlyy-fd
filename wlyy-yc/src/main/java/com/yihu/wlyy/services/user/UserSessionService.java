package com.yihu.wlyy.services.user;

import com.yihu.wlyy.daos.UserDao;
import com.yihu.wlyy.daos.UserSessionDao;
import com.yihu.wlyy.models.user.UserModel;
import com.yihu.wlyy.models.user.UserSessionModel;
import com.yihu.wlyy.util.DateUtil;
import com.yihu.wlyy.util.ResponseKit;
import com.yihu.wlyy.util.SystemConf;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @created Airhead 2016/9/4.
 */
@Service
public class UserSessionService {
    private static final ResponseKit responseKit = new ResponseKit();
    @Autowired
    private UserSessionDao userSessionDao;
    @Autowired
    private UserDao userDao;

    /**
     * 每次调用会更新过期时间，用于避免调用第三方登录
     *
     * @param openId
     * @return
     */
    public String loginInfo(String openId) {
        UserModel user = userDao.findOneByOpenId(openId);
        if (user == null) {
            return responseKit.error(SystemConf.NOT_LOGIN, "用户未登录或未注册。");
        }

        UserSessionModel userSessionModel = userSessionDao.findOne(user.getCode());
        if (userSessionModel == null) {
            return responseKit.error(SystemConf.NOT_LOGIN, "用户未登录。");
        }

        String nextDay = DateUtil.getNextDay(new Date(), 1);
        Date expireTime = DateUtil.strToDateLong(nextDay);
        userSessionModel.setExpireTime(expireTime);
        userSessionDao.save(userSessionModel);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("token", userSessionModel.getToken());
        map.put("userCode", userSessionModel.getUserCode());
        return responseKit.write(200, "用户登录成功。", "data", map);
    }

    public String login(String userName, String password) {
        return null;
    }

    public boolean callback(String code, String message, String openId, String sig) {
        String signature = openId + "yyweixin@jkzl";
        String md5Crypt = Md5Crypt.md5Crypt(signature.getBytes());
        if (!md5Crypt.equalsIgnoreCase(sig)) {
            return false;
        }

        UserModel user = userDao.findOneByOpenId(openId);
        if (user == null) {
            user = new UserModel(openId);
            user = userDao.save(user);
        }

        UserSessionModel userSession = userSessionDao.findOne(user.getCode());
        if (userSession == null) {
            userSession = new UserSessionModel();
            userSession.setUserCode(user.getCode());
            userSession.setToken(UUID.randomUUID().toString());
        }

        userSessionDao.save(userSession);

        return true;
    }
}
