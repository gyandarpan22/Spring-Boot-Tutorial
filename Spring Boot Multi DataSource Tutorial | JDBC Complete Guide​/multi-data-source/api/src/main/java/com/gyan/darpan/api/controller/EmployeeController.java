package com.gyan.darpan.api.controller;

import com.gyan.darpan.dao.employee.entity.Employee;
import com.gyan.darpan.dao.employee.repository.EmployeeJdbcRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("employee")
public class EmployeeController {


    private EmployeeJdbcRepository employeeJdbcRepository;

    public EmployeeController(EmployeeJdbcRepository employeeJdbcRepository) {
        this.employeeJdbcRepository = employeeJdbcRepository;
    }

    @GetMapping(path = "{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployeeDetail(@PathVariable("employeeId") Long employeeId) {
        return employeeJdbcRepository.findById(employeeId);
    }
}
