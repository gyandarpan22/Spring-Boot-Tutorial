package com.gyan.darpan.employee.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomEnvironmentController {

    private String property;

    public CustomEnvironmentController(
            @Value("${custom.environment.property1}") String property
    ) {
        this.property = property;
    }

    @GetMapping("custom/environment/post/processor/demo")
    public String getProperty() {
        return this.property;
    }
}
