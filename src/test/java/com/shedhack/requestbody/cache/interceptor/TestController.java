package com.shedhack.requestbody.cache.interceptor;

import com.shedhack.exception.core.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    private String MESSAGE = "Hello ";
    private String LANGUAGE = "English";

    @RequestMapping(value = "/hello/{user}", method = RequestMethod.GET)
    public Message hello(@PathVariable String user, @RequestParam(value = "caps", required = false) boolean caps ){

        String mess = caps == true ? new String(MESSAGE + user).toUpperCase() : new String(MESSAGE + user);
        String lang = caps == true ? LANGUAGE.toUpperCase() : LANGUAGE;

        return new Message(mess, lang);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public void message(@RequestBody Message message, @RequestParam(value = "caps") boolean caps) {

        MESSAGE = caps == true ? message.getMessage().toUpperCase() : message.getMessage();
        LANGUAGE = caps == true ? message.getLanguage() : message.getLanguage();
    }

    @RequestMapping(value  ="/problem", method = RequestMethod.POST)
    public ResponseEntity<String> problem(@RequestBody Message message){

        throw BusinessException.builder("Something horrible happened").withBusinessCode(BusinessCodes.E100)
                .withParam("user", "imam").build();
    }

}
