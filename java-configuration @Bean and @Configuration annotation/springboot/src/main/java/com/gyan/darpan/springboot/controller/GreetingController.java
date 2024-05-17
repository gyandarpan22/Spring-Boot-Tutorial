package com.gyan.darpan.springboot.controller;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private GreetingService greetingService;

    @Autowired
    public GreetingController(@Qualifier("englishGreetingService") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("greeting")
    public String greeting() {
        return greetingService.getGreeting();
    }

}
