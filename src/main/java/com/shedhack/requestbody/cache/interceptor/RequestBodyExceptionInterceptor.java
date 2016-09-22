package com.shedhack.requestbody.cache.interceptor;


import com.shedhack.exception.controller.spring.ExceptionInterceptor;
import com.shedhack.exception.core.ExceptionModel;
import com.shedhack.requestbody.cache.filter.RequestBodyThreadLocal;

/**
 * Exception Interceptor attempts to add the request body via the  {@link RequestBodyThreadLocal}
 * The assumption is that this is has been set via {@link com.shedhack.requestbody.cache.filter.RequestBodyCacheFilter}
 */
public class RequestBodyExceptionInterceptor implements ExceptionInterceptor {

    /**
     * Cannot guarantee if the request body has a value, all depends on the filter.
     * @param model exception model created by the exception controller.
     * @param exception original exception
     */
    @Override
    public void handle(ExceptionModel model, Exception exception) {
        model.setRequestBody(RequestBodyThreadLocal.get());
    }
}
