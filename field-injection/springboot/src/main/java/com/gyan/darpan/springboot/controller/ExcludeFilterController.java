package com.gyan.darpan.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcludeFilterController {

    //ApplicationContext : it contains all register bean
    private ApplicationContext applicationContext;

    @Autowired
    public ExcludeFilterController(ApplicationContext context) {
        this.applicationContext = context;
    }


    @GetMapping("/exclude/filter")
    public String exculdeFilterDemo() {
        boolean isBeanFound = applicationContext.containsBean("employeeServiceImpl");

        return "Bean Found : " + isBeanFound;

    }

}
