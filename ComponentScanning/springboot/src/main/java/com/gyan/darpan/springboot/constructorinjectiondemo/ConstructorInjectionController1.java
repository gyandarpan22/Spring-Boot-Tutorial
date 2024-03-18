package com.gyan.darpan.springboot.constructorinjectiondemo;

import com.componentscanningdemo.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConstructorInjectionController1 {

    private final NotificationService notificationService;
    private final GreetingService greetingService;

    public ConstructorInjectionController1(GreetingService greetingService){
        this.greetingService=greetingService;
        this.notificationService=null;
    }

    @Autowired
    public ConstructorInjectionController1(GreetingService greetingService,NotificationService notificationService){
        this.greetingService=greetingService;
        this.notificationService=notificationService;
    }

    @GetMapping("two/constructor/greeting/demo")
    public String greet(){
        return greetingService.getGreeting();
    }

    @GetMapping("two/constructor/demo")
    public String greetAndNotification(){
        return "Greeting : "+greetingService.getGreeting()+"\nNotification : "+notificationService.getNotification();
    }
}
