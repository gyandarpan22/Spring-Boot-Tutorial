package com.gyan.darpan.springboot.controller;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionScopeController {

    private GreetingService greetingService;

    @Autowired
    public SessionScopeController(@Qualifier("spanishGreetingService")GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("sessionScope/hashCode")
    public String getHashCode(){
        return "HashCode of greetingService1 : "+greetingService
                +" , Greetings: "+greetingService.getGreeting();
    }

}
