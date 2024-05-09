package com.gyan.darpan.springboot.controller;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SingletonBeanScopeController {

    private GreetingService greetingService1;
    private GreetingService greetingService2;

    @Autowired
    public SingletonBeanScopeController(@Qualifier("englishGreetingService") GreetingService greetingService1,
                                        @Qualifier("englishGreetingService") GreetingService greetingService2) {
        this.greetingService1 = greetingService1;
        this.greetingService2 = greetingService2;
    }

    @GetMapping("singleton/hashCode")
    public String getHashCode(){
        return "HashCode of greetingService1 : "+greetingService1.hashCode()
                +" HashCode of greetingService2 : "+greetingService2.hashCode();
    }

}
