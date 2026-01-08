package com.gyan.darpan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.gyan.darpan")
public class MasterSlaveProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasterSlaveProjectApplication.class, args);
    }

}
