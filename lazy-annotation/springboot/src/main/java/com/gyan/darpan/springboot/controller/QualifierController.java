package com.gyan.darpan.springboot.controller;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QualifierController {

    private final GreetingService greetingService;

    @Autowired
    public QualifierController(@Lazy @Qualifier("englishGreetingService") GreetingService greetingService){
        System.out.println("Initializing::"+this.getClass().getSimpleName());
        this.greetingService=greetingService;
    }

    @GetMapping("/qualifier/greeting")
    public String getGreeting(){
        return greetingService.getGreeting();
    }

}
