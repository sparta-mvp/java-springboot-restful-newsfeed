package com.example.newsfeed.common.config;

import com.example.newsfeed.common.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/v1/users/me/**")
                .addPathPatterns("/api/v1/posts/**")
                .addPathPatterns("/api/v1/comments/**")
                .addPathPatterns("/api/v1/auth/logout")
                .order(1);
    }
}
