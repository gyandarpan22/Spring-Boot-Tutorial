package com.gyan.darpan.employee.controller;

import com.gyan.darpan.employee.model.Custom;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomController {

    @PostMapping(path = "custom", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Custom custom(@RequestBody Custom custom) {
        System.out.println("Custom api : " + custom.toString());

        return custom;
    }
}
