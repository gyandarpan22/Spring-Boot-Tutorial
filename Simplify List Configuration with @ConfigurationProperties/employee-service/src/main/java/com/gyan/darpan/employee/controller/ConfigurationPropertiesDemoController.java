package com.gyan.darpan.employee.controller;

import com.gyan.darpan.employee.config.UserConfig;
import com.gyan.darpan.employee.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationPropertiesDemoController {
    private UserConfig userConfig;

    @Autowired
    public ConfigurationPropertiesDemoController(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    @GetMapping(path = "configuration/properties/demo", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser() {
        return User.builder()
                .userName(userConfig.getUserName())
                .age(userConfig.getAge())
                .active(userConfig.isActive())
                .roles(userConfig.getRoles())
                .build();
    }
}
