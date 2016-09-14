package com.yihu.wlyy.listener;

import com.yihu.wlyy.configuration.EnvConfig;
import com.yihu.wlyy.util.SystemConf;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 项目启动执行
 * add by hzp at 2016-01-25
 */
public class ApplicationStart implements ApplicationListener<ApplicationContextEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationContextEvent) {
        EnvConfig envConfig = applicationContextEvent.getApplicationContext().getBean(EnvConfig.class);
        SystemConf.getInstance().setProfile(envConfig.getProfile());
    }
}
