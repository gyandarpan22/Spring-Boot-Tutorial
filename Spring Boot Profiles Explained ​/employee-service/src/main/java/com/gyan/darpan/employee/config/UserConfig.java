package com.gyan.darpan.employee.config;


import com.gyan.darpan.employee.enums.Roles;
import com.gyan.darpan.employee.model.Address;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "user")
@Configuration
@Data
public class UserConfig {
    private String userName;
    private int age;
    private boolean active;
    private List<Roles> roles;
    private Address address;
}
