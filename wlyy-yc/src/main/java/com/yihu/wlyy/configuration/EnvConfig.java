package com.yihu.wlyy.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yihu.wlyy.util.SystemConf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author HZY
 * @vsrsion 1.0
 * Created at 2016/8/5.
 */
@Configuration
public class EnvConfig {

    @Value("${system.profile}")
    private String profile;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
