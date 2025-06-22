package com.gyan.darpan.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.gyan.darpan")
//@EnableJpaRepositories(basePackages = "com.gyan.darpan.dao")
@EntityScan(basePackages = "com.gyan.darpan.dao")
public class EmployeeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceApplication.class, args);
    }

}
