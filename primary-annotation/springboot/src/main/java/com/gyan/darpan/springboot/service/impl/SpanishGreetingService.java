package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.stereotype.Component;

@Component("spanishGreetingService")
public class SpanishGreetingService implements GreetingService {
    @Override
    public String getGreeting() {
        return "Hola";
    }
}
