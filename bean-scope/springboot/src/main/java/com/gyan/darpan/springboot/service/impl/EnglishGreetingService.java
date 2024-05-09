package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("englishGreetingService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EnglishGreetingService implements GreetingService {

    public EnglishGreetingService() {
        System.out.println("Initializing::" + this.getClass().getSimpleName());
    }

    @Override
    public String getGreeting() {
        return "Hello";
    }
}
