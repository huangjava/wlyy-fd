package com.yihu.wlyy.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

/**
 * @created Airhead 2016/9/4.
 */
public class ResponseKit {
    private static Logger logger = LoggerFactory.getLogger(ResponseKit.class);

    public void error(Exception e) {
        logger.error(getClass().getName() + ":", e.getMessage());
        e.printStackTrace();
    }

    public void warn(Exception e) {
        logger.warn(getClass().getName() + ":", e.getMessage());
        e.printStackTrace();
    }

    /**
     * 返回接口处理结果
     *
     * @param code 结果码，成功为400~，500~
     * @param msg  结果提示信息
     * @return
     */
    public String error(int code, String msg) {
        return this.write(code, msg);
    }

    /**
     * 接口处理成功
     *
     * @param msg
     * @return
     */
    public String success(String msg) {
        return this.write(200, msg);
    }

    public String write(int code, String msg) {
        try {
            Response response = new Response(code, msg).invoke();
            return response.getJsonObject().toString();
        } catch (Exception e) {
            error(e);
            return null;
        }
    }

    /**
     * 返回接口处理结果
     *
     * @param code 结果码，成功为200
     * @param msg  结果提示信息
     * @return
     */
//    public String write(int code, String msg, String key, List<?> list) {
//        try {
//            Response response = new Response(code, msg).invoke();
//            Map<Object, Object> map = response.getMap();
//            ObjectMapper mapper = response.getJsonObject();
//            map.put(key, list);
//            return mapper.writeValueAsString(map);
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "服务器异常，请稍候再试！");
//        }
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
        try {
            JSONObject json = new JSONObject();
            json.put("status", code);
            json.put("msg", msg);
            json.put(key, value);
            return json.toString();
        } catch (Exception e) {
            error(e);
            return error(-1, "服务器异常，请稍候再试！");
        }
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
        try {
            JSONObject json = new JSONObject();
            json.put("status", code);
            json.put("msg", msg);
            json.put(key, value);
            return json.toString();
        } catch (Exception e) {
            error(e);
            return error(-1, "服务器异常，请稍候再试！");
        }
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
        try {
            JSONObject json = new JSONObject();
            json.put("status", code);
            json.put("msg", msg);
            json.put("total", total);
            json.put(key, value);
            return json.toString();
        } catch (Exception e) {
            error(e);
            return error(-1, "服务器异常，请稍候再试！");
        }
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
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", code);
            jsonObject.put("msg", msg);
            jsonObject.put(key, value);
            return jsonObject.toString();
        } catch (Exception e) {
            error(e);
            return error(-1, "服务器异常，请稍候再试！");
        }
    }

    public String write(int code, String msg, String key, Page<?> list) {
        try {
            Map<Object, Object> map = new HashMap<Object, Object>();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", code);
            jsonObject.put("msg", msg);
            // 是否为第一页
            jsonObject.put("isFirst", list.isFirst());
            // 是否为最后一页
            jsonObject.put("isLast", list.isLast());
            // 总条数
            jsonObject.put("total", list.getTotalElements());
            // 总页数
            jsonObject.put("totalPages", list.getTotalPages());
            jsonObject.put(key, list.getContent());
            return jsonObject.toString();
        } catch (Exception e) {
            error(e);
            return error(-1, "服务器异常，请稍候再试！");
        }
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
//        try {
//            Map<Object, Object> map = new HashMap<Object, Object>();
//            ObjectMapper mapper = new ObjectMapper();
//            map.put("status", code);
//            map.put("msg", msg);
//            map.put(key, value);
//            return mapper.writeValueAsString(map);
//        } catch (Exception e) {
//            error(e);
//            return error(-1, "服务器异常，请稍候再试！");
//        }
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
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", code);
            jsonObject.put("msg", msg);
            jsonObject.put(key, value);
            return jsonObject.toString();
        } catch (Exception e) {
            error(e);
            return error(-1, "服务器异常，请稍候再试！");
        }
    }

    private class Response {
        private int code;
        private String msg;
        private Map<Object, Object> map;
        private JSONObject jsonObject;

        public Response(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public Map<Object, Object> getMap() {
            return map;
        }

        public JSONObject getJsonObject() {
            return jsonObject;
        }

        public Response invoke() {
            map = new HashMap<>();
            jsonObject = new JSONObject();
            jsonObject.put("status", code);
            jsonObject.put("msg", msg);
            return this;
        }
    }
}

