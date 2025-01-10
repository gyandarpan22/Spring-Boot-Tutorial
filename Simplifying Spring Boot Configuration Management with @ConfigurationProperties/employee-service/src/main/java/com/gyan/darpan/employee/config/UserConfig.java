package com.gyan.darpan.employee.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "user")
@Configuration
@Data
public class UserConfig {
    private String userName;
    private int age;
    private boolean active;
}
