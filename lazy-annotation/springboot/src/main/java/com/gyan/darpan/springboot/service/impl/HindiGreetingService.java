package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.stereotype.Component;


@Component("hindiGreetingService")
public class HindiGreetingService implements GreetingService {

    public HindiGreetingService(){
        System.out.println("Initializing::"+this.getClass().getSimpleName());
    }

    @Override
    public String getGreeting() {
        return "Namaste";
    }
}
