package com.gyan.darpan.springboot.config;

import com.gyan.darpan.springboot.service.GreetingService;
import com.gyan.darpan.springboot.service.impl.EnglishGreetingService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class GreetingServiceConfig {

    @Bean
    @Scope(ConfigurableBeanFactory. SCOPE_PROTOTYPE)
    public GreetingService englishGreetingService() {
        return new EnglishGreetingService();
    }
}
