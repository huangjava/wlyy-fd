package com.yihu.wlyy.controllers.user;

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
@RequestMapping(value = "/loginApp")
public class LoginAppController {
    @RequestMapping(value = "", method = RequestMethod.GET)
    public void loginApp(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.sendRedirect("http://ehr.yihu.com/yichang/fd/app/html/yichangguanli/html/main.html?userId=9bf5afea-3200-4489-b93a-b5261351479e&appUID=APPUID&orgId=420503003000&appType=1&vaildTime=TIMESTAMP&ticket=sdfgasdgfasd");
    }
}
