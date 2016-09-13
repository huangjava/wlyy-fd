package com.yihu.wlyy.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan("com.yihu.wlyy.controllers")
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
        return new ApiInfoBuilder()
                .title("Common RESTful APIs")
                .description("家庭医生-公共Rest API， all the applications could access the Object model data via JSON")
                .termsOfServiceUrl("NO terms of service")
                .version("1.0")
                .contact(new Contact("huangzhiyong@jkzl.com", "", ""))
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
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
        return new ApiInfoBuilder()
                .title("医生端 RESTful APIs")
                .description("家庭医生-医生端Rest API， all the applications could access the Object model data via JSON")
                .termsOfServiceUrl("NO terms of service")
                .version("1.0")
                .contact(new Contact("huangzhiyong@jkzl.com", "", ""))
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
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
                .apiInfo(patientApiInfo());
    }

    private ApiInfo patientApiInfo() {
        return new ApiInfoBuilder()
                .title("患者端 RESTful APIs")
                .description("家庭医生-患者端 Rest API， all the applications could access the Object model data via JSON")
                .termsOfServiceUrl("NO terms of service")
                .version("1.0")
                .contact(new Contact("huangzhiyong@jkzl.com", "", ""))
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}