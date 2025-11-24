package com.example.Final_Project_Java.util;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/admin/**") // chỉ kiểm tra các endpoint admin
                .excludePathPatterns(Arrays.asList(
                        "/admin/login"
                ));
   }
}

