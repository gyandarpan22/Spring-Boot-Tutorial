package com.gyan.darpan.api.controller;

import com.gyan.darpan.dao.employee.entity.Employee;
import com.gyan.darpan.dao.employee.repository.EmployeeJpaRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private EmployeeJpaRepository employeeJpaRepository;

    public EmployeeController(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @GetMapping(path = "{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployeeDetail(@PathVariable("employeeId") Long employeeId) {
        return employeeJpaRepository.findById(employeeId).orElse(null);
    }
}
