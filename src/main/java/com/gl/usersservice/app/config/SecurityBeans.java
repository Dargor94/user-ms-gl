package com.gl.usersservice.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Configuration
public class SecurityBeans {

    @Bean
    @Primary
    public ExceptionHandlerExceptionResolver getResolver() {
        return new ExceptionHandlerExceptionResolver();
    }

}
