package com.gyan.darpan.employee.controller;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.repository.jpa.EmployeeJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoJpaController {

    private final EmployeeJpaRepository employeeJpaRepository;

    public DemoJpaController(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @GetMapping("jpa/demo/findByEmployeeName")
    public List<Employee> getEmployeeByEmployeeName(@RequestParam("employeeName") String employeeName) {
        return employeeJpaRepository.findByEmployeeName(employeeName);
    }

    @GetMapping("jpa/demo/findByAge")
    public List<Employee> findByAge(@RequestParam("age") int age) {
        return employeeJpaRepository.findByAgeLessThan(age);
    }

    @GetMapping("jpa/demo/findByNameAndAge")
    public List<Employee> findByNameAndAge(@RequestParam("employeeName") String employeeName,
                                           @RequestParam("age") int age) {
        return employeeJpaRepository.findByEmployeeNameAndAgeLessThanEqual(employeeName, age);
    }

}
