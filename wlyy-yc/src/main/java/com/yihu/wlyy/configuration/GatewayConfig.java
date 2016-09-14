package com.yihu.wlyy.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Airhead on 2016/9/13.
 */
@Configuration
//@ConfigurationProperties(prefix = "yihu.gateway")
public class GatewayConfig {
    @Value("${yihu.gateway.client.id}")
    private String clientId;
    @Value("${yihu.gateway.client.version}")
    private String clientVersion;
    @Value("${yihu.gateway.url}")
    private String url;


    public String getClientId() {
        return clientId;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public String getUrl() {
        return url;
    }

}
