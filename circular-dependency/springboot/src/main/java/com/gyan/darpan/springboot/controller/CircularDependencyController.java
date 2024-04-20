package com.gyan.darpan.springboot.controller;


import com.gyan.darpan.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CircularDependencyController {

    private final EmployeeService employeeService;

    @Autowired
    public CircularDependencyController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @GetMapping(path = "circular/dependency/employee/details")
    public String getEmployeeDetails(){
        return employeeService.getEmployeeDetails(23L);
    }

}
