package com.yihu.wlyy.services.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.daos.UserDao;
import com.yihu.wlyy.daos.UserSessionDao;
import com.yihu.wlyy.models.user.UserAgent;
import com.yihu.wlyy.models.user.UserModel;
import com.yihu.wlyy.models.user.UserSessionModel;
import com.yihu.wlyy.services.doctor.DoctorService;
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
    private UserService userService;
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

    public void loginApp(UserAgent userAgent){

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
                return isLoginApp(request, response, null);
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        UserAgent user = objectMapper.readValue(userAgent, UserAgent.class);
        if (!StringUtil.isEmpty(user.getOpenid())) {
            return isLoginWeChat(request, response, user);
        }

        return isLoginApp(request, response, user);
    }

    public boolean isLoginWeChat(HttpServletRequest request, HttpServletResponse response, UserAgent userAgent) throws Exception {
        if (userAgent == null) {
            return false;
        }

        UserSessionModel userSession = userSessionDao.findOne(userAgent.getUid());
        if(userSession != null){
            if (DateUtil.compareDate(userSession.getExpireTime(), DateUtil.getNow()) > 0) {
                return true;
            }
        }

        response.getOutputStream().write(responseKit.write(200, "reLogin", "loginUrl", genEHomeUrl(userAgent.getOpenid())).getBytes());
        return false;

    }

    public boolean isLoginWeChat(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String openId = request.getParameter("openId");
        String random = request.getParameter("random");
        openId = AESUtil.decryptByRandom(openId, random);
        response.getOutputStream().write(responseKit.write(200, "reLogin", "loginUrl", genEHomeUrl(openId)).getBytes());
        return false;
    }

    public boolean isLoginApp(HttpServletRequest request, HttpServletResponse response, UserAgent userAgent) throws IOException {
        try {
            if (userAgent == null) {
                return false;
            }

            String userId = request.getParameter("userId");
            String appType = request.getParameter("appType");
            String validTime = request.getParameter("validTime");
            String orgId = request.getParameter("orgId");
            String appUid = request.getParameter("appUid");
            String ticket = request.getParameter("ticket");
            String userCode = userAgent.getUid();
            String userName = "";

            //有ticket说明是从APP第一次进入，由main.html发起
            if (!StringUtil.isEmpty(ticket)) {
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

                result = HttpClientUtil.doPost(sso, param, null, null);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readValue(result, JsonNode.class);
                String code = jsonNode.findPath("Code").asText();
                if (!code.equals("10000")) {
                    logger.debug(result);
                    return false;
                }

                userCode = jsonNode.findPath("Result").findPath("UserID").asText();
                userName = jsonNode.findPath("Result").findPath("LoginID").asText();
            }


            UserSessionModel userSession = userSessionDao.findOne(userCode);
            if (userSession != null) {
                if (DateUtil.compareDate(userSession.getExpireTime(), DateUtil.getNow()) > 0) {
                    if (!StringUtil.isEmpty(ticket)){
                        userSession.setToken(ticket);   //APP产生ticket可能已经更新了
                        userSessionDao.save(userSession);
                    }

                    return true;
                }
            }
            UserModel user = userDao.findOneByCode(userCode);
            if (user == null) {
                user = new UserModel(userName, userCode);
                String idCard = getIdCard(userCode);
                user.setIdCard(idCard);
                user.setExternalIdentity(userService.getExternal(idCard).get("userId"));
                user = userDao.save(user);
            } else {
                if (StringUtil.isEmpty(user.getIdCard()) || StringUtil.isEmpty(user.getExternalIdentity())) {
                    String idCard = getIdCard(userCode);
                    user.setIdCard(idCard);
                    user.setExternalIdentity(userService.getExternal(idCard).get("userId"));
                    user = userDao.save(user);
                }
            }
            if (userSession == null) {
                userSession = new UserSessionModel();
            }
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
        return "420500196911241845";//TODO:  FOR TEST

//        Map<String, String> apiParam = new HashMap<>();
//        apiParam.put("returnMsg", "IdNumber");
//        apiParam.put("doctorUid", userCode);
//        String result = gateWayService.post(GateWayApi.queryUserInfoByID, apiParam);
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = null;
//        try {
//            jsonNode = objectMapper.readValue(result, JsonNode.class);
//            String code = jsonNode.findPath("Code").asText();
//            if (!code.equals("10000")) {
//                logger.error("调用网关接口失败");
//                return "";
//            }
//
//            return jsonNode.findPath("IdNumber").asText();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "";
    }


}
