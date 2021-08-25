package com.example.IntercepterTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    APIInterceptor apiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*set API Intetcept*/
        registry.addInterceptor(apiInterceptor)
                //  .addPathPatterns("/**")
                .excludePathPatterns("/**");
    }
}
