package com.gyan.darpan.springboot.controller;


import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class OptionalDependencyController {

    private GreetingService greetingService;

    private Optional<GreetingService> optionalGreetingService;

    @GetMapping("optional/dependency/greeting")
    public String getGreeting() {
        if (greetingService == null) {
            return "default greeting";
        }
        return greetingService.getGreeting();
    }

    @GetMapping("optional/dependency/greeting1")
    public String getGreeting1() {
        return optionalGreetingService.map(GreetingService::getGreeting)
                .orElse("default greeting1");
    }

    @Autowired(required = false)
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Autowired
    public void setOptionalGreetingService(Optional<GreetingService> optionalGreetingService) {
        this.optionalGreetingService = optionalGreetingService;
    }
}
