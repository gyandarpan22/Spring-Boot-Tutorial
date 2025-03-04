package com.gyan.darpan.employee.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomEnvironmentController {

    private String property;

    private Environment environment;

    public CustomEnvironmentController(
            @Value("${custom.environment.property1}") String property,
            Environment environment
    ) {
        this.property = property;
        this.environment = environment;
    }

    @GetMapping("custom/environment/post/processor/demo")
    public String getProperty() {
        return this.property;
    }

    @GetMapping("custom/environment/demo")
    public String getAppMessage() {
        return environment.getProperty("app.message");
    }
}
