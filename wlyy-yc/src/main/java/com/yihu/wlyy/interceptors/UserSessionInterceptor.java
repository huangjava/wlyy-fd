package com.yihu.wlyy.interceptors;

import com.yihu.wlyy.services.user.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserSessionInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private UserSessionService userSessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return userSessionService.isLogin(request, response);
//        return super.preHandle(request, response, handler);
    }
}