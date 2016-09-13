package com.yihu.wlyy.configuration;

import com.yihu.wlyy.interceptors.UserSessionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @created Airhead 2016/9/8.
 */
@EnableWebMvc
@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter {
    @Bean
    UserSessionInterceptor userSessionInterceptor() {
        return new UserSessionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userSessionInterceptor())
                .addPathPatterns("/patient/**")
                .addPathPatterns("/doctor/**")
                .addPathPatterns("/user/**")
                .addPathPatterns("/common/**")
                .addPathPatterns("/loginApp/**");
    }
}
