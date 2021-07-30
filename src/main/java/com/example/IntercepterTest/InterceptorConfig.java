package com.example.IntercepterTest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;


@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    @Autowired
    APIInterceptor apiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*set API Intetcept*/
        registry.addInterceptor(apiInterceptor)
                .addPathPatterns("/**");
                //.addPathPatterns(Arrays.asList("/api/*", "/*"));
    }
}
