package com.gyan.darpan.springboot.controller;


import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FieldInjectionController {

    @Autowired
    private GreetingService greetingService;

    @GetMapping(path = "field/injection/greeting")
    public String greetings(){
        return greetingService.getGreeting();
    }

}
