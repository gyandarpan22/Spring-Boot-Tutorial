package com.gyan.darpan.springboot.controller;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimaryAnnotationController {

    private GreetingService greetingService;

    @Autowired
    public PrimaryAnnotationController(GreetingService greetingService){
        this.greetingService=greetingService;
    }

    @GetMapping(path = "primary/greeting")
    public String getGreeting(){
        return greetingService.getGreeting();
    }

}
