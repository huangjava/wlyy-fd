package com.yihu.wlyy.interceptors;

import com.yihu.wlyy.services.user.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @created Airhead 2016/9/9.
 */
public class DoctorAppInterceptor  extends HandlerInterceptorAdapter {
    @Autowired
    private UserSessionService userSessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }
}
