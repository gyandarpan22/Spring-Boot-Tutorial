package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.stereotype.Component;


@Component("hindiGreetingService")
public class HindiGreetingService implements GreetingService {
    @Override
    public String getGreeting() {
        return "Namaste";
    }
}
