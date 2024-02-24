package com.gyan.darpan.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {



    @GetMapping("hello")
    public String helloWorld(){
        return "Hello World!";
    }

}
