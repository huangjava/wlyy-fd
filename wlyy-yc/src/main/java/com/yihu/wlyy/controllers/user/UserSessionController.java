package com.yihu.wlyy.controllers.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.models.user.UserAgent;
import com.yihu.wlyy.models.user.UserSessionModel;
import com.yihu.wlyy.services.user.UserSessionService;
import com.yihu.wlyy.util.SystemConf;
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
    public void loginWeChat(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String openId = request.getParameter("openId");

        if (openId == null) {
            String familyDoctorUrl = SystemConf.getInstance().getValue("familyDoctor");
            response.sendRedirect(familyDoctorUrl);
            return;
        }
        response.sendRedirect(userSessionService.genEHomeUrl("OCEF9T2HW1GBY0KINQK0NEL_ZOSK"));
    }

    @RequestMapping(value = "/wechat/callback", method = RequestMethod.GET)
    public void weChatCallback(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String message = request.getParameter("message");
        String openid = request.getParameter("openid");
        String sig = request.getParameter("sig");

        UserSessionModel userSessionModel = userSessionService.loginWeChat(code, message, openid, sig);
        try {
            String familyDoctorUrl = SystemConf.getInstance().getValue("familyDoctor");
            if (userSessionModel == null) {
                response.sendRedirect(familyDoctorUrl);
                return;
            }

            UserAgent userAgent = new UserAgent();
            userAgent.setToken(userSessionModel.getToken());
            userAgent.setOpenid(userSessionModel.getTokenRef());
            userAgent.setUid(userSessionModel.getUserCode());
            ObjectMapper objectMapper = new ObjectMapper();
            String user = objectMapper.writeValueAsString(userAgent);
            response.sendRedirect(familyDoctorUrl+"?userAgent="+user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/app", method = RequestMethod.GET)
    public void loginApp(HttpServletRequest request, HttpServletResponse response) {
        userSessionService.loginApp(request, response);
    }
}
