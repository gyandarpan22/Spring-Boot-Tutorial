package com.gyan.darpan.springboot.constructorinjectiondemo;

import org.springframework.stereotype.Component;

@Component
public class GreetingServiceImpl implements GreetingService{
    @Override
    public String getGreeting() {
        return "Welcome to Our Channel";
    }
}
