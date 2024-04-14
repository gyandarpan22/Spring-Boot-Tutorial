package com.gyan.darpan.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

    @Value("${youtube.favourite.channel}")
    private String favouriteChannelName;

    @Value("${youtube.favourite.channel.url}")
    private String favouriteChannelUrl;



    @GetMapping("hello")
    public String helloWorld(){
        return "Hello World!";
    }

    @GetMapping("custom/values")
    public String customValues(){
        return "Favourite Channel: "+favouriteChannelName+" Favourite  Channel Url : "+favouriteChannelUrl;
    }

}
