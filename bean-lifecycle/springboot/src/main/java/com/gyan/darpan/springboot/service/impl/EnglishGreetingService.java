package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("englishGreetingService")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
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
