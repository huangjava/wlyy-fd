package com.yihu.wlyy.wx.util;

import com.yihu.wlyy.util.SystemConf;
import com.yihu.wlyy.util.HttpUtil;
import com.yihu.wlyy.wx.entity.AccessToken;
import com.yihu.wlyy.wx.service.AccessTokenService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lyr on 2016/08/22.
 */
@Component
public class WeiXinAccessTokenUtils {
    @Autowired
    private AccessTokenService accessTokenService;

    /**
     * 获取微信AccessToken
     *
     * @return
     */
    public String getAccessToken(){
        try {
            Iterable<AccessToken> accessTokens = accessTokenService.findAccessToken();
            if (accessTokens != null) {
                for (AccessToken accessToken : accessTokens) {
                    if ((System.currentTimeMillis() - accessToken.getAdd_timestamp()) < (accessToken.getExpires_in() * 1000)) {
                        return accessToken.getAccess_token();
                    } else {
                        accessTokenService.delAccessToken(accessToken);
                        break;
                    }
                }
            }
            String token_url = "https://api.weixin.qq.com/cgi-bin/token";
            String params = "grant_type=client_credential&appid=" + SystemConf.getInstance().getAppId() + "&secret=" + SystemConf.getInstance().getAppSecret();
            String result = HttpUtil.sendGet(token_url, params);
            JSONObject json = new JSONObject(result);
            if (json.has("access_token")) {
                String token = json.get("access_token").toString();
                String expires_in = json.get("expires_in").toString();
                AccessToken newaccessToken = new AccessToken();
                newaccessToken.setAccess_token(token);
                newaccessToken.setExpires_in(Long.parseLong(expires_in));
                accessTokenService.addAccessToken(newaccessToken);
                return token;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
