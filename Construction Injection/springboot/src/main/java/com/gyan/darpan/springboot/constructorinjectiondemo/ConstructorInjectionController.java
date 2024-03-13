package com.gyan.darpan.springboot.constructorinjectiondemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstructorInjectionController {

    private final GreetingService greetingService;

    //Constructor Injection
    @Autowired
    public ConstructorInjectionController(GreetingService greetingService){
        this.greetingService=greetingService;
    }

    @GetMapping("/greeting")
    public String greet(){
        return greetingService.getGreeting();
    }

}
