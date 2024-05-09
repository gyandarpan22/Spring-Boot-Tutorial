package com.gyan.darpan.springboot.controller;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomScopeController {

    private ObjectProvider<GreetingService> greetingServiceObjectProvider;

    @Autowired
    public CustomScopeController(@Qualifier("spanishGreetingService") ObjectProvider<GreetingService> greetingServiceObjectProvider) {
        this.greetingServiceObjectProvider = greetingServiceObjectProvider;
    }

    @GetMapping("customScope/hashCode")
    public String getHashCode() {
        GreetingService greetingService=greetingServiceObjectProvider.getObject();
        return "HashCode of greetingService1 : " + greetingService
                + " , Greetings: " + greetingService.getGreeting();
    }

}
