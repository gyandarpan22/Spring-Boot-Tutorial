package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.stereotype.Component;

@Component
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String getGreeting() {
        return "Welcome to Our Channel";
    }
}
