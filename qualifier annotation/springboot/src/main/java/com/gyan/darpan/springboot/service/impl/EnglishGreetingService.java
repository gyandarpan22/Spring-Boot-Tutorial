package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.stereotype.Component;

@Component("englishGreetingService")
public class EnglishGreetingService implements GreetingService {
    @Override
    public String getGreeting() {
        return "Hello";
    }
}
