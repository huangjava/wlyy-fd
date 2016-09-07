package com.yihu.wlyy.controllers.user;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.models.user.UserSessionModel;
import com.yihu.wlyy.services.user.UserSessionService;
import com.yihu.wlyy.util.SystemConf;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @created Airhead 2016/9/4.
 */
@Controller
@RequestMapping(value = "/login")
public class UserSessionController extends BaseController {
    @Autowired
    private UserSessionService userSessionService;

    @RequestMapping(value = "/wechat", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId = request.getParameter("openId");
        String publicName = request.getParameter("publicName");
        if (openId == null || publicName == null) {
            response.sendError(400);
            return;
        }

        String eHomeUrl = SystemConf.getInstance().getValue("eHome");
        String eHomrScrect = SystemConf.getInstance().getValue("eHomeSecret");
        String eHomeCallback = SystemConf.getInstance().getValue("eHomeCallback");
        String signature = DigestUtils.md5Hex(openId + publicName + eHomeCallback + eHomrScrect).toUpperCase();
        String url = eHomeUrl
                + "?openid=" + openId
                + "&weixin=" + publicName
                + "&returnurl" + eHomeCallback
                + "&sig=" + signature;

        response.sendRedirect(url);
    }


    @RequestMapping(value = "/wechat/info", method = RequestMethod.GET)
    public String loginInfo(String userCode) {
        return userSessionService.loginInfo(userCode);
    }

    @RequestMapping(value = "/wechat/callback", method = RequestMethod.GET)
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String message = request.getParameter("message");
        String openid = request.getParameter("openid");
        String sig = request.getParameter("sig");

        UserSessionModel userSessionModel = userSessionService.callback(code, message, openid, sig);
        try {
            String familyDoctorUrl = SystemConf.getInstance().getValue("familyDoctor");
            if (userSessionModel == null) {
                response.sendRedirect(familyDoctorUrl);
                return;
            }

            response.sendRedirect(familyDoctorUrl + "?token=" + userSessionModel.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
