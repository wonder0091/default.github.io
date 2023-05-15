package com.bypx.synthesis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler 表示浏览器的请求地址里有包含以下字符串
        registry.addResourceHandler("/static/**","/img/**").addResourceLocations("classpath:/static/","file:F://temp/");
        super.addResourceHandlers(registry);
    }
}
