package com.gyan.darpan.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages =
		{
				"com.componentscanningdemo",
				"com.gyan.darpan.springboot"
		})
//@SpringBootApplication
//@ComponentScan(basePackages = {
//		"com.componentscanningdemo",
//		"com.gyan.darpan.springboot"
//})
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
