package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("hindiGreetingService")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HindiGreetingService implements GreetingService {
    private int count;
    public HindiGreetingService(){
        System.out.println("Initializing::"+this.getClass().getSimpleName());
        count=0;
    }

    @Override
    public String getGreeting() {
        return "Namaste"+(count++);
    }
}
