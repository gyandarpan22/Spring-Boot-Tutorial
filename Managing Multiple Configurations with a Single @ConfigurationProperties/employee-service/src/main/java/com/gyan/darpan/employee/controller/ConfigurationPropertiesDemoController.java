package com.gyan.darpan.employee.controller;

import com.gyan.darpan.employee.config.EmailConfig;
import com.gyan.darpan.employee.config.UserConfig;
import com.gyan.darpan.employee.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigurationPropertiesDemoController {
    private UserConfig userConfig;
    private EmailConfig gmailConfig;
    private EmailConfig outlookConfig;

    @Autowired
    public ConfigurationPropertiesDemoController(UserConfig userConfig,
                                                 @Qualifier("gmailConfig") EmailConfig gmailConfig,
                                                 @Qualifier("outlookConfig") EmailConfig outlookConfig) {
        this.userConfig = userConfig;
        this.gmailConfig = gmailConfig;
        this.outlookConfig = outlookConfig;
    }

    @GetMapping(path = "configuration/properties/demo", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser() {
        return User.builder()
                .userName(userConfig.getUserName())
                .age(userConfig.getAge())
                .active(userConfig.isActive())
                .roles(userConfig.getRoles())
                .address(userConfig.getAddress())
                .build();
    }

    @GetMapping(path = "multiple/configuration/properties/demo", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmailConfig getEmail(@RequestParam("emailType") String emailType) {
        EmailConfig emailConfig = "gmail".equals(emailType) ? gmailConfig : outlookConfig;

        return EmailConfig.builder()
                .host(emailConfig.getHost())
                .port(emailConfig.getPort())
                .username(emailConfig.getUsername())
                .password(emailConfig.getPassword())
                .build();
    }


}
