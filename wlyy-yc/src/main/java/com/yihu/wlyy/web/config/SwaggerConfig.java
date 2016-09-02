package com.yihu.wlyy.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan("com.yihu.wlyy.web")
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Bean
    public Docket commonApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Common")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(or(
                        regex(".common.*[^(Page)]")))
                .build()
                .apiInfo(commonApiInfo());
    }

    private ApiInfo commonApiInfo() {
        ApiInfo apiInfo = new ApiInfo("Electronic Health Record(EHR) Browser Common API",
                "FamilyDoctor's REST API, all the applications could access the Object model data via JSON.",
                "0.1",
                "NO terms of service",
                "huangzhiyong@jkzl.com",
                "The Apache License, Version 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );

        return apiInfo;
    }

    @Bean
    public Docket doctorApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Doctor")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(or(
                        regex(".doctor.*[^(Page)]")))
                .build()
                .apiInfo(doctorApiInfo());
    }

    private ApiInfo doctorApiInfo() {
        ApiInfo apiInfo = new ApiInfo("Electronic Health Record(EHR) Browser Medic API",
                "FamilyDoctor's REST API, all the applications could access the Object model data via JSON.",
                "0.1",
                "NO terms of service",
                "huangzhiyong@jkzl.com",
                "The Apache License, Version 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );

        return apiInfo;
    }

    @Bean
    public Docket patientApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Patient")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .paths(or(
                        regex(".patient.*[^(Page)]")))
                .build()
                .apiInfo(doctorApiInfo());
    }

    private ApiInfo patientApiInfo() {
        ApiInfo apiInfo = new ApiInfo("Electronic Health Record(EHR) Browser ordinary API",
                "FamilyDoctor's REST API, all the applications could access the Object model data via JSON.",
                "0.1",
                "NO terms of service",
                "huangzhiyong@jkzl.com",
                "The Apache License, Version 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0.html"
        );

        return apiInfo;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}