package com.gyan.darpan.springboot.controller;

import com.gyan.darpan.springboot.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private ApplicationContext applicationContext;

    @Autowired
    public GreetingController(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @GetMapping("greeting")
    public String greeting() {
        GreetingService greetingService = null;
        try {
            greetingService = applicationContext.getBean("englishGreetingService", GreetingService.class);
            return greetingService.getGreeting();
        } finally {
            if (greetingService != null) {
                applicationContext.getAutowireCapableBeanFactory().destroyBean(greetingService);
            }
        }
    }

}
