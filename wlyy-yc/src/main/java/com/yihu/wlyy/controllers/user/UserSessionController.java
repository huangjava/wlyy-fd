package com.yihu.wlyy.controllers.user;

import com.yihu.wlyy.services.user.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @created Airhead 2016/9/4.
 */
@Controller
@RequestMapping(value = "/login")
public class UserSessionController {
    @Autowired
    private UserSessionService userSessionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginInfo(String userCode) {
        return userSessionService.loginInfo(userCode);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String login(String userName, String passWord) {
        return userSessionService.login(userName, passWord);
    }

    @RequestMapping(value = "/loginCallback", method = RequestMethod.GET)
    public String loginCallback(HttpServletRequest request) {
        return userSessionService.loginCallback();
    }
}
