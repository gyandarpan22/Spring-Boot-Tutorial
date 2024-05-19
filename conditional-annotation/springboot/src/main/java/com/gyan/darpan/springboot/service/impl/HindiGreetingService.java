package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Component("hindiGreetingService")
//@ConditionalOnProperty(name = "greeting.language",havingValue = "hindi")
@ConditionalOnExpression("'${greeting.language}'.equals('hindi')")
public class HindiGreetingService implements GreetingService {
    public HindiGreetingService() {
        System.out.println("Initializing::" + this.getClass().getSimpleName());
    }

    @Override
    public String getGreeting() {
        return "Namaste";
    }
}
