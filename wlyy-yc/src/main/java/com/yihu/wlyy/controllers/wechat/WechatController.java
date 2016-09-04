package com.yihu.wlyy.controllers.wechat;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @created Airhead 2016/9/4.
 */
@RestController
@RequestMapping(value = "/wechat")
public class WechatController extends WeixinBaseController {
    @RequestMapping(method = RequestMethod.GET)
    public boolean checkSignature(String signature, String timestamp, String nonce) {
        String TOKEN = "29e33fda"; //ApiConfigKit.getApiConfig().getToken();
        String array[] = {TOKEN, timestamp, nonce};
        Arrays.sort(array);
        String tempStr = (array[0] + array[1] + array[2]);
        tempStr = DigestUtils.sha1Hex(tempStr);
        return tempStr.equalsIgnoreCase(signature);
    }
}
