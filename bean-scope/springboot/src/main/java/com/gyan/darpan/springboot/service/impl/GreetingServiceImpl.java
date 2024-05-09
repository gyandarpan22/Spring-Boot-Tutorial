package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class GreetingServiceImpl implements GreetingService {
    private int count;

    public GreetingServiceImpl() {
        System.out.println("Initializing::" + this.getClass().getSimpleName());
        count=0;
    }

    @Override
    public String getGreeting() {
        return "Welcome to Our Channel"+(count++);
    }
}
