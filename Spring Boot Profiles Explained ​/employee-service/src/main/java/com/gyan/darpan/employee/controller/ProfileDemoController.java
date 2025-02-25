package com.gyan.darpan.employee.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileDemoController {
    private final String appMessage;

    public ProfileDemoController(@Value("${app.message}") String appMessage) {
        this.appMessage = appMessage;
    }

    @GetMapping("/profile/demo")
    public String getAppMessage() {
        return this.appMessage;
    }
}
