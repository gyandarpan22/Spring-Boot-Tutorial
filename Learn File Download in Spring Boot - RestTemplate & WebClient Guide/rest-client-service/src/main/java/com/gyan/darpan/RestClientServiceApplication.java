package com.gyan.darpan;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.gyan.darpan.model.Employee;
import com.gyan.darpan.service.EmployeeService;
import com.gyan.darpan.service.FileUploadService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RestClientServiceApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RestClientServiceApplication.class, args);

        try {
//            EmployeeService employeeService = applicationContext.getBean(EmployeeService.class);
//
//            //employeeService.getEmployees();
//            // employeeService.getEmployee();
//            // employeeService.deleteEmployee();
//            System.out.print("Adding a new Employee----");
//            Employee employee = employeeService.addEmployee();
//
//            System.out.print("Fetching a new Employee detail----");
//            employeeService.getEmployee(employee.getEmployeeId());

            FileUploadService fileUploadService = applicationContext.getBean(FileUploadService.class);
            fileUploadService.downloadFileFromInputStream();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
