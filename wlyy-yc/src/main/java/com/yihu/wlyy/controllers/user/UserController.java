package com.yihu.wlyy.controllers.user;

import com.yihu.wlyy.controllers.BaseController;
import com.yihu.wlyy.models.user.UserModel;
import com.yihu.wlyy.services.user.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @created Airhead 2016/9/4.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(HttpServletRequest request) {
        try {
            String userAgent = request.getParameter("userAgent");
            JSONObject jsonObject = JSONObject.fromObject(userAgent);
            String userCode = jsonObject.getString("uid");
            String clientType = jsonObject.getString("clientType");
            UserModel userModel = userService.get(userCode);
            if (userModel != null) {
                if (clientType.equals("weChat")) {
                    jsonObject.put("openid", userModel.getExternalIdentity());
                } else {    //app
                    jsonObject.put("doctorId", userModel.getExternalIdentity());
                }

                return write(200, "获取用户信息成功！", "data", jsonObject);
            }

            return write(-1, "获取用户信息失败！", "data", jsonObject);

        } catch (Exception e) {
            error(e);
            return error(-1, "获取用户信息失败！");
        }
    }
}
