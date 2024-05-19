package com.gyan.darpan.springboot.config;

import com.gyan.darpan.springboot.service.GreetingService;
import com.gyan.darpan.springboot.service.impl.EnglishGreetingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingServiceConfig {

    @Bean
    @ConditionalOnMissingBean(EnglishGreetingService.class)
    public GreetingService englishGreetingService() {
        System.out.println("EnglishGreetingService bean is created by bean annotation");
        return new EnglishGreetingService();
    }
}
