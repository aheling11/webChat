package com.webchat.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class AuthorizationServiceInterceptorAppConfig extends WebMvcConfigurerAdapter {
    @Autowired
    AuthorizationServiceInterceptor authorizationServiceInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationServiceInterceptor);
    }
}