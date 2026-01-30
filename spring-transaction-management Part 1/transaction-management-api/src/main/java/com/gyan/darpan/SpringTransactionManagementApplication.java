package com.gyan.darpan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.gyan.darpan")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.gyan.darpan")
@EntityScan(basePackages = "com.gyan.darpan")
public class SpringTransactionManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTransactionManagementApplication.class, args);
	}

}
