package com.gyan.darpan.employee.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomPropertyDemoController {

    private final int intValue;
    private final String stringValue;
    private final boolean booleanValue;
    private final int intWithDefaultValue;
    private List<String> fruits;
    private List<String> fruits1;

    public CustomPropertyDemoController(
            @Value("${custom.property.int}") int intValue,
            @Value("${custom.property.string}") String stringValue,
            @Value("${custom.property.boolean}") boolean booleanValue,
            @Value("${custom.property.intWithDefaultValue:2024}") int intWithDefaultValue,
            @Value("${custom.property.fruits}") List<String> fruits,
            @Value("#{'${custom.property.fruits1}'.split(';')}") List<String> fruits1) {
        this.intValue = intValue;
        this.stringValue = stringValue;
        this.booleanValue = booleanValue;
        this.intWithDefaultValue = intWithDefaultValue;
        this.fruits = fruits;
        this.fruits1 = fruits1;
    }

    @GetMapping(path = "custom/property/demo")
    public String getCustomProperty() {
        return "Integer Value : " + intValue
                + "\nString Value : " + stringValue
                + "\nBoolean Value : " + booleanValue
                + "\nInteger With Default Value : " + intWithDefaultValue;
    }

    @GetMapping(path = "custom/property/fruits")
    public List<String> getFruits() {
        return fruits;
    }

    @GetMapping(path = "custom/property/fruits1")
    public List<String> getFruits1() {
        return fruits1;
    }


}
