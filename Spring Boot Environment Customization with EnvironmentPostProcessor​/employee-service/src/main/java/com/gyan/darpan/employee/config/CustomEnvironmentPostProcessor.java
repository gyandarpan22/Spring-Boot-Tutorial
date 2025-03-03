package com.gyan.darpan.employee.config;

import org.apache.commons.logging.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLogFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private Log log;

    public CustomEnvironmentPostProcessor(DeferredLogFactory logFactory) {
        this.log = logFactory.getLog(this.getClass());
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("inside CustomEnvironmentPostProcessor");

        String appMessage = environment.getProperty("app.message");

        log.info("AppMessage : " + appMessage);

        //fetch properties from external environment

        Map<String, Object> propertyMap = new HashMap<>();

        propertyMap.put("custom.environment.property1", "CustomEnvironmentPostProcessor");


        MapPropertySource mapPropertySource = new MapPropertySource("customExternalProperty", propertyMap);

        environment.getPropertySources().addLast(mapPropertySource);

    }
}
