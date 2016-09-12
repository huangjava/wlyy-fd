package com.yihu.wlyy.services.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.daos.UserDao;
import com.yihu.wlyy.daos.UserSessionDao;
import com.yihu.wlyy.models.user.UserAgent;
import com.yihu.wlyy.models.user.UserModel;
import com.yihu.wlyy.models.user.UserSessionModel;
import com.yihu.wlyy.util.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @created Airhead 2016/9/4.
 */
@Service
public class UserSessionService {
    private static final ResponseKit responseKit = new ResponseKit();
    private static Logger logger = LoggerFactory.getLogger(UserSessionService.class);
    @Autowired
    private UserSessionDao userSessionDao;
    @Autowired
    private UserDao userDao;

    /**
     * 每次调用会更新过期时间，用于避免调用第三方登录
     *
     * @param userName 微信OpenId
     * @return
     */
    public String loginInfo(String userName) {
        UserModel user = userDao.findOne(userName);
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
        map.put("userName", userSessionModel.getUserCode());
        return responseKit.write(200, "用户登录成功。", "data", map);
    }

    public UserSessionModel loginWeChat(String code, String message, String openId, String sig) {
        String signature = openId + SystemConf.getInstance().getValue("eHomeSecret");
        String md5Crypt = DigestUtils.md5Hex(signature);
        if (!md5Crypt.equalsIgnoreCase(sig)) {
            return null;
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

        String nextDay = DateUtil.getNextDay(new Date(), 1);
        Date expireTime = DateUtil.strToDateLong(nextDay);
        userSession.setExpireTime(expireTime);

        userSession = userSessionDao.save(userSession);

        String xiaoWeiComunity = SystemConf.getInstance().getValue("xiaoWeiComunity");
        Map<String, Object> param = new HashMap<>();
        param.put("action", "add");
        param.put("openid", "ooLDPt9H0cH3aMOOFknVxWsk5Bhk");
        String response = null;
        try {
            response = HttpClientUtil.doPost(xiaoWeiComunity, param, null, null);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }
        logger.debug(response);

        return userSession;
    }

    public UserSessionModel loginApp(HttpServletRequest request, HttpServletResponse response) {
//        String userId = request.getParameter("userId");
//        String appType = request.getParameter("appType");
//        String validTime = request.getParameter("validTime");
//        String orgId = request.getParameter("orgId");
//        String appUid = request.getParameter("appUid");
//        String ticket = request.getParameter("ticket");
        return null;
    }

    public boolean isLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userAgent = request.getHeader("userAgent");
//        if (userAgent == null) {
//            //从健康之中APP或者小薇社区进入
//            //小薇社区进入时openId非空
//            String openId = request.getParameter("openId");
//            return !StringUtil.isEmpty(openId) || isLoginApp(request, response);
//        }

        if (StringUtil.isEmpty(userAgent)) {
            //从健康之中APP或者小薇社区进入
            //从健康之中APP进入时ticket非空
            String ticket = request.getParameter("ticket");
            return !StringUtil.isEmpty(ticket) || isLoginWeChat(request, response);
        }
        //以上空的逻辑是否可合并还需要验证

        ObjectMapper objectMapper = new ObjectMapper();
        UserAgent user = objectMapper.readValue(userAgent, UserAgent.class);
        if (!StringUtil.isEmpty(user.getOpenid())) {
            return isLoginWeChat(request, response);
        }

        return isLoginApp(request, response);
    }

    public boolean isLoginWeChat(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userAgent = request.getHeader("userAgent");
        ObjectMapper objectMapper = new ObjectMapper();
        if (!StringUtil.isEmpty(userAgent)) {

            UserAgent user = objectMapper.readValue(userAgent, UserAgent.class);
            UserSessionModel userSession = userSessionDao.findOne(user.getUid());
            if (userSession != null) {
                return true;
            }
        } else {
            response.sendRedirect(genEHomeUrl("OCEF9T2HW1GBY0KINQK0NEL_ZOSK"));
        }
        return false;
    }

    public boolean isLoginApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String appType = request.getParameter("appType");
        String validTime = request.getParameter("validTime");
        String orgId = request.getParameter("orgId");
        String appUid = request.getParameter("appUid");
        String ticket = request.getParameter("ticket");

        String sso = SystemConf.getInstance().getValue("sso.yihu");
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("orgId", orgId);
        param.put("appUid", appUid);
        param.put("ticket", ticket);
        String result = null;
        try {
            result = HttpClientUtil.doPost(sso, param, null, null);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readValue(result, JsonNode.class);
            String code = jsonNode.findPath("Code").asText();
            if (!code.equals("10000")) {
                //TODO:WriteResponse,Not Login Jkzl App
                response.getOutputStream().print(result);
                logger.debug(result);
                return false;
            }

            String userCode = jsonNode.findPath("Result").findPath("UserId").asText();
            String userName = jsonNode.findPath("Result").findPath("LoginId").asText();
            UserSessionModel userSession = userSessionDao.findOne(userCode);
            if (userSession != null) {
                return true;
            }

            UserModel user = userDao.findOne(userName);
            if (user == null) {
                user = new UserModel(userName, userCode);
                user = userDao.save(user);
            }

            userSession = new UserSessionModel();
            userSession.setUserCode(user.getCode());
            userSession.setToken(ticket);
            String nextDay = DateUtil.getNextDay(new Date(), 1);
            Date expireTime = DateUtil.strToDateLong(nextDay);
            userSession.setExpireTime(expireTime);

            userSessionDao.save(userSession);
            return true;

        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

        response.getOutputStream().print(result);
        return false;
    }

    public String genEHomeUrl(String openId) throws UnsupportedEncodingException {
        String weiWeChat = SystemConf.getInstance().getValue("xiaoWeiWeChat");
        String eHomeUrl = SystemConf.getInstance().getValue("eHome");
        String eHomeScrect = SystemConf.getInstance().getValue("eHomeSecret");
        String eHomeCallback = SystemConf.getInstance().getValue("eHomeCallback");
        String signature = DigestUtils.md5Hex(openId + weiWeChat + eHomeCallback + eHomeScrect).toUpperCase();
        return eHomeUrl
                + "?openid=" + openId
                + "&weixin=" + weiWeChat
                + "&returnurl=" + eHomeCallback
                + "&sig=" + signature;
    }

}
