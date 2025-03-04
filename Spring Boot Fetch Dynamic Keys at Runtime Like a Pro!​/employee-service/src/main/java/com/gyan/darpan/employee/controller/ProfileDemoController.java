package com.gyan.darpan.employee.controller;

import com.gyan.darpan.employee.service.FileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileDemoController {
    private final String appMessage;
    private final FileStorage fileStorage;

    public ProfileDemoController(@Value("${app.message}") String appMessage,
                                 FileStorage fileStorage) {
        this.appMessage = appMessage;
        this.fileStorage = fileStorage;
    }

    @GetMapping("/profile/demo")
    public String getAppMessage() {
        return this.appMessage;
    }

    @GetMapping("/profile/demo1")
    public String getAppMessage1() {
        return this.fileStorage.storeFile();
    }
}
