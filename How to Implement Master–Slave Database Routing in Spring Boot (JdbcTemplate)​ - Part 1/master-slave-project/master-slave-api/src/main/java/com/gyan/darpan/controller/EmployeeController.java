package com.gyan.darpan.controller;

import com.gyan.darpan.entity.Employee;
import com.gyan.darpan.repository.EmployeeJdbcRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("master-slave/employee")
public class EmployeeController {
    private final EmployeeJdbcRepository employeeJdbcRepository;

    public EmployeeController(EmployeeJdbcRepository employeeJdbcRepository) {
        this.employeeJdbcRepository = employeeJdbcRepository;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeJdbcRepository.saveEmployee(employee);
    }

    @GetMapping(path = "{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployeeById(@PathVariable("employeeId") long employeeId) {
        return employeeJdbcRepository.findByEmployeeId(employeeId);
    }
}
