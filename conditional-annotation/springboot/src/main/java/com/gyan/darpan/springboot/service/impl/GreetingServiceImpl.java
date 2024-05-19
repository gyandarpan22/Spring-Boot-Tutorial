package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.config.GreetingServiceImplCondition;
import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Component
@Conditional(GreetingServiceImplCondition.class)
public class GreetingServiceImpl implements GreetingService {

    public GreetingServiceImpl() {
        System.out.println("Initializing::" + this.getClass().getSimpleName());
    }

    @Override
    public String getGreeting() {
        return "Welcome to Our Channel";
    }
}
