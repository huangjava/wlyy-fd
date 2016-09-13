package com.yihu.wlyy.services.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.daos.UserDao;
import com.yihu.wlyy.daos.UserSessionDao;
import com.yihu.wlyy.models.user.UserAgent;
import com.yihu.wlyy.models.user.UserModel;
import com.yihu.wlyy.models.user.UserSessionModel;
import com.yihu.wlyy.services.doctor.DoctorService;
import com.yihu.wlyy.services.gateway.GateWayApi;
import com.yihu.wlyy.services.gateway.GateWayService;
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
    @Autowired
    private GateWayService gateWayService;
    @Autowired
    private DoctorService doctorService;

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
        userSession.setExpireTime(DateUtil.addDateTime(1, new Date()));

        userSession = userSessionDao.save(userSession);

        String xiaoWeiComunity = SystemConf.getInstance().getValue("xiaoWeiComunity");
        Map<String, Object> param = new HashMap<>();
        param.put("action", "add");
        param.put("openid", openId);
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

    public boolean isLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userAgent = request.getHeader("userAgent");
        if (StringUtil.isEmpty(userAgent)) {
            //从健康之中APP或者小薇社区进入
            //从健康之中APP进入时ticket非空
            String ticket = request.getParameter("ticket");
            if (StringUtil.isEmpty(ticket)) {
                return isLoginWeChat(request, response);
            } else {
                return isLoginApp(request, response);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        UserAgent user = objectMapper.readValue(userAgent, UserAgent.class);
        if (!StringUtil.isEmpty(user.getOpenid())) {
            return isLoginWeChat(user);
        }

        return isLoginApp(user);
    }

    public boolean isLoginWeChat(UserAgent userAgent) {
        if (userAgent == null) {
            return false;
        }

        UserSessionModel userSession = userSessionDao.findOne(userAgent.getUid());
        return userSession != null;

    }

    public boolean isLoginWeChat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = request.getParameter("openId");
        String random = request.getParameter("random");
        openId = AESUtil.decryptByRandom(openId, random);
        response.getOutputStream().write(responseKit.write(200, "reLogin", "loginUrl", genEHomeUrl(openId)).getBytes());
        return false;
    }

    public boolean isLoginApp(UserAgent userAgent) {
        if (userAgent == null) {
            return false;
        }

        UserSessionModel userSession = userSessionDao.findOne(userAgent.getUid());
        return userSession != null;

    }

    public boolean isLoginApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        String appType = request.getParameter("appType");
        String validTime = request.getParameter("validTime");
        String orgId = request.getParameter("orgId");
        String appUid = request.getParameter("appUid");
        String ticket = request.getParameter("ticket");
        if (StringUtil.isEmpty(appUid)) {
            appUid = "0";
        }
        String sso = SystemConf.getInstance().getValue("sso.yihu");
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("orgId", orgId);
        param.put("appUid", appUid);
        param.put("ticket", ticket);
        String result = null;
        try {
            result = HttpClientUtil.doPost(sso, param, null, null);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readValue(result, JsonNode.class);
            String code = jsonNode.findPath("Code").asText();
            if (!code.equals("10000")) {
                logger.debug(result);
                return false;
            }

            String userCode = jsonNode.findPath("Result").findPath("UserID").asText();
            String userName = jsonNode.findPath("Result").findPath("LoginID").asText();
            UserSessionModel userSession = userSessionDao.findOne(userCode);
            if (userSession != null) {
                return true;
            }

            UserModel user = userDao.findOne(userName);
            if (user == null) {
                user = new UserModel(userName, userCode);
                String idCard = getIdCard(userCode);
                user.setIdCard(idCard);
                user.setIdCard(getExternalIdentity(idCard));
                user = userDao.save(user);
            }

            if (StringUtil.isEmpty(user.getIdCard()) || StringUtil.isEmpty(user.getExternalIdentity())) {
                String idCard = getIdCard(userCode);
                user.setIdCard(idCard);
                user.setIdCard(getExternalIdentity(idCard));
                user = userDao.save(user);
            }

            userSession = new UserSessionModel();
            userSession.setUserCode(user.getCode());
            userSession.setToken(ticket);
            userSession.setExpireTime(DateUtil.addDateTime(1, new Date()));

            userSessionDao.save(userSession);
            return true;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            e.printStackTrace();
        }

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

    private String getIdCard(String userCode) {
        Map<String, String> apiParam = new HashMap<>();
        apiParam.put("returnMsg", "IdNumber");
        apiParam.put("doctorUid", userCode);
        String result = gateWayService.post(GateWayApi.queryUserInfoByID, apiParam);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readValue(result, JsonNode.class);
            String code = jsonNode.findPath("Code").asText();
            if (!code.equals("10000")) {
                logger.error("调用网关接口失败");
                return "";
            }

            return jsonNode.findPath("IdNumber").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String getExternalIdentity(String idCard) {
        String identity = doctorService.loginByID(idCard, SystemConf.getInstance().getValue("neusoft.ws.key"));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readValue(identity, JsonNode.class);
            return jsonNode.findPath("idCard").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
