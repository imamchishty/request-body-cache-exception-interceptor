package com.shedhack.requestbody.cache.interceptor;

import com.shedhack.exception.controller.spring.ExceptionController;
import com.shedhack.exception.core.ExceptionModel;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RequestBodyIntegrationTest {

    @Autowired //http://localhost:${local.server.port}
    private TestRestTemplate template;

    @Test
    public void sayHello() throws Exception {

        assertEquals("Hello Imam", template.getForEntity("/hello/{user}", Message.class, "Imam").getBody().getMessage());
    }


    @Test
    public void setMessage() throws Exception {

        // Arrange
        String message = "What's up ";
        String language = "German";
        String user = "Imam";

        // Act
        template.postForEntity("/hello?caps=true", new Message(message, language), Message.class);
        Message response = template.getForEntity("/hello/{user}?caps=true", Message.class, user).getBody();

        // Assert
        assertEquals(new String(message + user).toUpperCase(), response.getMessage());
        assertEquals(language.toUpperCase(), response.getLanguage());
    }

    @Test
    public void getProblem() throws Exception {

        // Arrange
        String message = "What's up ";
        String language = "German";

        ResponseEntity<ExceptionModel> response = template.postForEntity("/problem", new Message(message, language), ExceptionModel.class);

        // Check that the response is the exception model
        assertThat(response.getStatusCode().value(), equalTo(400));

        // body
        assertThat(response.getBody().getApplicationName(), equalTo("demo"));
        assertThat(response.getBody().getTraceId(), isEmptyOrNullString());
        assertThat(response.getBody().getExceptionId(), notNullValue());
        assertThat(response.getBody().getHttpStatusDescription(), equalTo("Bad Request"));
        assertThat(response.getBody().getPath(), equalTo("/problem"));
        assertThat(response.getBody().getSessionId(), notNullValue());
        assertThat(response.getBody().getHelpLink(), equalTo("http://link"));
        assertThat(response.getBody().getMessage(), equalTo("Something horrible happened"));
        assertThat(response.getBody().getExceptionClass(), equalTo("com.shedhack.exception.core.BusinessException"));
        assertThat(response.getBody().getMetadata(), equalTo("exception-core-model"));
        assertThat(response.getBody().getHttpStatusCode(), equalTo(400));
        assertThat(response.getBody().getParams(), hasKey("user"));
        assertThat(response.getBody().getParams().get("user"), Matchers.<Object>equalTo("imam"));
        assertThat(response.getBody().getBusinessCodes(), hasKey("E100"));
        assertThat(response.getBody().getContext(), hasKey("threadName"));
        assertThat(response.getBody().getDateTime(), notNullValue());
        assertThat(response.getBody().getExceptionChain(), notNullValue());

        // The test interceptor should have added the request body
        assertThat(response.getBody().getRequestBody(), equalTo("{\"message\":\"What's up \",\"language\":\"German\"}"));

        // header
        assertThat(response.getHeaders().get("exceptionId"), notNullValue());
        assertThat(response.getHeaders().get("exceptionId").toString(), equalTo("["+response.getBody().getExceptionId()+"]"));
        assertThat(response.getHeaders().get("exceptionType"), notNullValue());

        // check the exception count
        assertThat(ExceptionController.getExceptionCount(), equalTo(1));

    }

}