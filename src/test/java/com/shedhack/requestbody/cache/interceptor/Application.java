package com.shedhack.requestbody.cache.interceptor;

import com.shedhack.exception.controller.spring.ExceptionInterceptor;
import com.shedhack.exception.controller.spring.config.EnableExceptionController;
import com.shedhack.requestbody.cache.filter.RequestBodyCacheFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

/**
 * Test Application
 */
@SpringBootApplication
@EnableExceptionController
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Filter requestBodyFilter() {
        return new RequestBodyCacheFilter();
    }

    @Bean
    public ExceptionInterceptor requestBodyExceptionInterceptor() {
        return new RequestBodyExceptionInterceptor();
    }
}
