package com.yihu.wlyy.wx.util;

import com.yihu.wlyy.util.HttpUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by lyr on 2016/08/22.
 */
@Component
public class WeiXinTempMsgSendUtils {

    @Autowired
    WeiXinAccessTokenUtils weiXinAccessTokenUtils;

    // 模板消息发送接口URL
    private static String TEMP_MSG_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     * 模板消息发送
     *
     * @param templateId 模板消息ID
     * @param toUser     接收者
     * @param url        模板消息跳转URL
     * @param data       模板数据
     * @return
     */
    public boolean sendTemplateMessage(String templateId, String toUser, String url, JSONObject data) throws Exception {
        // 参数
        JSONObject params = new JSONObject();
        String accessToken = weiXinAccessTokenUtils.getAccessToken();

        if (StringUtils.isEmpty(templateId)) {
            throw new Exception("templateId is  null");
        }
        if (StringUtils.isEmpty(toUser)) {
            throw new Exception("openid is null ");
        }
        if (StringUtils.isEmpty(accessToken)) {
            throw new Exception("accessToken is null");
        }

        // 接收者
        params.put("touser", toUser);
        // 模板消息ID
        params.put("template_id", templateId);
        // 模板消息点击跳转URL
        params.put("url", url);
        // 模板数据
        params.put("data", data);

        // 发送模板消息
        String result = HttpUtil.sendPost(TEMP_MSG_SEND_URL + accessToken, params.toString());
        JSONObject jsonResult = new JSONObject(result);
        String returnCode=jsonResult.get("errcode").toString();
        if ("0".equals(returnCode)) {
            return true;
        } else {
            return false;
        }
    }
}
