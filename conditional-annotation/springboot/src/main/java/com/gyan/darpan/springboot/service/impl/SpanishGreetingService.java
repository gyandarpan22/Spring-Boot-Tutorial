package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.stereotype.Component;

@Component("spanishGreetingService")
//@ConditionalOnBean(name = "englishGreetingService1")
//@ConditionalOnClass(name = "com.gyan.darpan.springboot.service.impl.HindiGreetingService1")
@ConditionalOnMissingClass(value = "com.gyan.darpan.springboot.service.impl.HindiGreetingService1")
public class SpanishGreetingService implements GreetingService {

    public SpanishGreetingService() {
        System.out.println("Initializing::" + this.getClass().getSimpleName());
    }

    @Override
    public String getGreeting() {
        return "Hola";
    }
}
