package com.ecommerce.payment_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthorizationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        var token = servletRequestAttributes.getRequest().getHeader("Authorization");

        if(StringUtils.hasText(token)) requestTemplate.header("Authorization", token);
    }
}
