package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component("englishGreetingService")
@Lazy
public class EnglishGreetingService implements GreetingService {

    public EnglishGreetingService(){
        System.out.println("Initializing::"+this.getClass().getSimpleName());
    }

    @Override
    public String getGreeting() {
        return "Hello";
    }
}
