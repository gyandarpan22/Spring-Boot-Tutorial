package com.gyan.darpan.employee.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;

@Configuration
//@PropertySource("classpath:email-configuration.properties")
public class EmailConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "email.gmail")
    public EmailConfig gmailConfig() {
        return new EmailConfig();
    }


    @Bean
    @ConfigurationProperties(prefix = "email.outlook")
    public EmailConfig outlookConfig() {
        return new EmailConfig();
    }

}
