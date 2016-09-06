package com.yihu.wlyy.models.common;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by lingfeng on 2016/9/5.
 */
public class ResultModel {
    private Integer status;
    private String msg;
    private Map<String, Object> resultMap;

    /**
     * 错误消息
     */
    public static ResultModel error(String msg) {
        ResultModel re= new ResultModel();
        re.status = -1;
        re.msg = msg;
        return re;
    }

    /**
     * 成功消息
     */
    public static ResultModel success(String msg) {
        ResultModel re= new ResultModel();
        re.status = 200;
        re.msg = msg;
        return re;
    }

    public String toJson() {
       return new JSONObject(this).toString();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
}
