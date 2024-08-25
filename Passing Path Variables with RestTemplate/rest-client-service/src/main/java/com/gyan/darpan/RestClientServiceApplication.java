package com.gyan.darpan;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.gyan.darpan.service.EmployeeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RestClientServiceApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RestClientServiceApplication.class, args);

        try {
            EmployeeService employeeService = applicationContext.getBean(EmployeeService.class);

            //employeeService.getEmployees();
            employeeService.getEmployee();
            employeeService.deleteEmployee();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }


}
