# requestbody-cache-interceptor

[![Build Status](https://travis-ci.org/imamchishty/requestbody-cache-interceptor.svg?branch=master "requestbody-cache-interceptor")](https://travis-ci.org/imamchishty/requestbody-cache-interceptor) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.shedhack.requestbody/cache-interceptor/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.shedhack.requestbody/cache-interceptor)

## Introduction
This component is an implementation of an exception interceptor from [exception controller spring](https://github.com/imamchishty/exception-controller-spring). What this does it to set the `postBody` property on the `exception model` that will be returned by the controller.

The actual request body property is set by [Request Body Cache Filter](https://github.com/imamchishty/requestbody-cache-filter) and is accessible via `RequestBodyThreadLocal`. So you'll need to add that project to your pom.xml and configure it.

## Configuration

    @Bean
    public Filter requestBodyFilter() {
        return new RequestBodyCacheFilter();
    }

    @Bean
    public ExceptionInterceptor requestBodyExceptionInterceptor() {
        return new RequestBodyExceptionInterceptor();
    }

The `ExceptionInterceptor` will be autowired directly to the exception controller.

And that's how easy it is!
    
