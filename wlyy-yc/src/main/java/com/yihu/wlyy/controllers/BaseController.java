package com.yihu.wlyy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.util.ResponseKit;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class BaseController {

    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    private final ResponseKit responseKit = new ResponseKit();

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected ObjectMapper objectMapper;

    /**
     * 獲取髮送請求用戶的uid
     *
     * @return
     */
    public String getUID() {
        try {
            String userAgent = request.getHeader("userAgent");
            if (StringUtils.isEmpty(userAgent)) {
                userAgent = request.getHeader("User-Agent");
            }
            JSONObject json = JSONObject.fromObject(userAgent);
            return json.getString("uid");

        } catch (Exception e) {
            return null;
        }
    }

    public String getOpenid() {
        try {
            String userAgent = request.getHeader("userAgent");
            if (StringUtils.isEmpty(userAgent)) {
                userAgent = request.getHeader("User-Agent");
            }
            JSONObject json = JSONObject.fromObject(userAgent);
            return json.getString("openid");

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public long getId() {
        try {
            String userAgent = request.getHeader("userAgent");
            if (StringUtils.isEmpty(userAgent)) {
                userAgent = request.getHeader("User-Agent");
            }
            JSONObject json = JSONObject.fromObject(userAgent);
            return json.getLong("id");
        } catch (Exception e) {
            return 0;
        }
    }

    public String getIMEI() {
        try {
            String userAgent = request.getHeader("userAgent");
            if (StringUtils.isEmpty(userAgent)) {
                userAgent = request.getHeader("User-Agent");
            }
            JSONObject json = JSONObject.fromObject(userAgent);
            return json.getString("imei");
        } catch (Exception e) {
            return null;
        }
    }

    public String getToken() {
        try {
            String userAgent = request.getHeader("userAgent");
            if (StringUtils.isEmpty(userAgent)) {
                userAgent = request.getHeader("User-Agent");
            }
            JSONObject json = JSONObject.fromObject(userAgent);
            return json.getString("token");
        } catch (Exception e) {
            return null;
        }
    }

    public void error(Exception e) {
        responseKit.error(e);
    }

    public void warn(Exception e) {
        responseKit.warn(e);
    }

    /**
     * 返回接口处理结果
     *
     * @param code 结果码，成功为200
     * @param msg  结果提示信息
     * @return
     */
    public String error(int code, String msg) {
        return responseKit.error(code, msg);
    }

    /**
     * 接口处理成功
     *
     * @param msg
     * @return
     */
    public String success(String msg) {
        return responseKit.success(msg);
    }

    public String write(int code, String msg) {
        return responseKit.write(code, msg);
    }

    /**
     * 返回接口处理结果
     *
     * @param code 结果码，成功为200
     * @param msg  结果提示信息
     * @return
     */
//    public String write(int code, String msg, String key, List<?> list) {
//        return responseKit.write(code, msg, key, list);
//    }

    /**
     * 返回接口处理结果
     *
     * @param code  结果码，成功为200
     * @param msg   结果提示信息
     * @param value 结果数据
     * @return
     */
    public String write(int code, String msg, String key, JSONObject value) {
        return responseKit.write(code, msg, key, value);
    }

    /**
     * 返回接口处理结果
     *
     * @param code  结果码，成功为200
     * @param msg   结果提示信息
     * @param value 结果数据
     * @return
     */
    public String write(int code, String msg, String key, JSONArray value) {
        return responseKit.write(code, msg, key, value);
    }

    /**
     * 返回接口处理结果
     *
     * @param code  结果码，成功为200
     * @param msg   结果提示信息
     * @param total 总数
     * @param value 结果数据
     * @return
     */
    public String write(int code, String msg, int total, String key, JSONArray value) {
        return responseKit.write(code, msg, total, key, value);
    }

    /**
     * 返回接口处理结果
     *
     * @param code  结果码，成功为200
     * @param msg   结果提示信息
     * @param value 结果数据
     * @return
     */
    public String write(int code, String msg, String key, Object value) {
        return responseKit.write(code, msg, key, value);
    }

    /**
     * 返回分页接口处理结果
     *
     * @param code  结果码，成功为200
     * @param msg   结果提示信息
     * @param value 结果数据
     * @return
     */
    public String write(int code, String msg, String key, Page<?>  value) {
        return responseKit.write(code, msg, key, value);
    }


    /**
     * 返回接口处理结果
     *
     * @param code  结果码，成功为200
     * @param msg   结果提示信息
     * @param value 结果数据
     * @return
     */
//    public String write(int code, String msg, String key, Map<?, ?> value) {
//        return responseKit.write(code, msg, key, value);
//    }

    /**
     * 返回接口处理结果
     *
     * @param code  结果码，成功为200
     * @param msg   结果提示信息
     * @param value 结果数据
     * @return
     */
    public String write(int code, String msg, String key, String value) {
        return responseKit.write(code, msg, key, value);
    }

    public String trimEnd(String param, String trimChars) {
        if (param.endsWith(trimChars)) {
            param = param.substring(0, param.length() - trimChars.length());
        }
        return param;
    }

    /**
     * 无效用户消息返回
     *
     * @param e
     * @param defaultCode
     * @param defaultMsg
     * @return
     */
    public String invalidUserException(Exception e, int defaultCode, String defaultMsg) {
        try {
            // if (e instanceof UndeclaredThrowableException) {
            // UndeclaredThrowableException ute = (UndeclaredThrowableException) e;
            // InvalidUserException iue = (InvalidUserException) ute.getUndeclaredThrowable();
            // if (iue != null) {
            // return error(iue.getCode(), iue.getMsg());
            // }
            // }
            return error(defaultCode, defaultMsg);
        } catch (Exception e2) {
            return null;
        }
    }

}
