package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component("spanishGreetingService")
//@SessionScope
@Scope("custom")
public class SpanishGreetingService implements GreetingService {

    private int count;

    public SpanishGreetingService(){
        System.out.println("Initializing::"+this.getClass().getSimpleName());
        count=0;
    }

    @Override
    public String getGreeting() {
        return "Hola : "+(count++);
    }
}
