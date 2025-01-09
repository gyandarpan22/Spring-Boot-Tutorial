package com.gyan.darpan.employee.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomPropertyDemoController {

    private final int intValue;
    private final String stringValue;
    private final boolean booleanValue;
    private final int intWithDefaultValue;

    public CustomPropertyDemoController(
            @Value("${custom.property.int}") int intValue,
            @Value("${custom.property.string}") String stringValue,
            @Value("${custom.property.boolean}") boolean booleanValue,
            @Value("${custom.property.intWithDefaultValue:2024}") int intWithDefaultValue) {
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.booleanValue = booleanValue;
        this.intWithDefaultValue = intWithDefaultValue;
    }

    @GetMapping(path = "custom/property/demo")
    public String getCustomProperty() {
        return "Integer Value : " + intValue
                + "\nString Value : " + stringValue
                + "\nBoolean Value : " + booleanValue
                + "\nInteger With Default Value : " + intWithDefaultValue;
    }

}
