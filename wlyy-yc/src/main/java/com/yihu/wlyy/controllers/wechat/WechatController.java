package com.yihu.wlyy.controllers.wechat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @created Airhead 2016/9/4.
 */
@RestController
@RequestMapping(value = "/wechat")
public class WechatController {
    @RequestMapping(method = RequestMethod.GET)
    public String token() {
        return "29e33fda";
    }
}
