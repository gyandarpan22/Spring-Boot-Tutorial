package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

public class EnglishGreetingService implements GreetingService {

    public EnglishGreetingService() {
        System.out.println("Initializing::" + this.getClass().getSimpleName());
    }

    @Override
    public String getGreeting() {
        return "Hello";
    }

    @PostConstruct
    public void initialize() {
        System.out.println("Post Construct called::" + this.getClass().getSimpleName());
    }

    @PreDestroy
    public void cleanUp() {
        System.out.println("Pre Destroy called::" + this.getClass().getSimpleName());
    }
}
