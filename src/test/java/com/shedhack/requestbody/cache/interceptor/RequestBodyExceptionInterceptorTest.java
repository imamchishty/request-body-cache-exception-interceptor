package com.shedhack.requestbody.cache.interceptor;


import com.shedhack.exception.core.BusinessException;
import com.shedhack.exception.core.ExceptionModel;
import com.shedhack.requestbody.cache.filter.RequestBodyThreadLocal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RequestBodyExceptionInterceptorTest {

    private RequestBodyExceptionInterceptor interceptor = new RequestBodyExceptionInterceptor();

    private static final String REQUEST_BODY = "{user: imam}";

    @Before
    public void setup() {
        RequestBodyThreadLocal.set("{user: imam}");
    }

    @After
    public void after() {
        RequestBodyThreadLocal.clear();
    }

    @Test
    public void should_set_request_body() {

        // Arrange
        ExceptionModel model = new ExceptionModel();
        BusinessException exception = new BusinessException("Problem");

        // Act
        interceptor.handle(model, exception);

        // Assert
        assertEquals(REQUEST_BODY, model.getPostBody());
    }

}
