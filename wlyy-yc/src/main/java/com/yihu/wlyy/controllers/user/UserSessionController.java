package com.yihu.wlyy.controllers.user;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.models.user.UserSessionModel;
import com.yihu.wlyy.services.user.UserSessionService;
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

    @RequestMapping(method = RequestMethod.GET)
    public String loginInfo(String userCode) {
        return userSessionService.loginInfo(userCode);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String login(String userName, String passWord) {
        return userSessionService.login(userName, passWord);
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String message = request.getParameter("message");
        String openid = request.getParameter("openid");
        String sig = request.getParameter("sig");

        UserSessionModel userSessionModel = userSessionService.callback(code, message, openid, sig);
        try {
            if (userSessionModel == null) {
                response.sendRedirect("http://localhost:8080/ycwx/health_service/404.html");
                return;
            }

            response.sendRedirect("http://localhost:8080/ycwx/html/ssgg/html/start-sign3.html?token=" + userSessionModel.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
