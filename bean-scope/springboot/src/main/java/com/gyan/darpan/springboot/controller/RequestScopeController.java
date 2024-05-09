package com.gyan.darpan.springboot.controller;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestScopeController {

    private GreetingService greetingService;

    @Autowired
    public RequestScopeController(@Qualifier("greetingServiceImpl")GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("requestScope/hashCode")
    public String getHashCode(){
        return "HashCode of greetingService1 : "+greetingService
                +" , Greetings: "+greetingService.getGreeting();
    }

}
