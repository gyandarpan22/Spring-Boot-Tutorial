package com.gyan.darpan.controller;

import com.gyan.darpan.datasource.DbContextHolder;
import com.gyan.darpan.entity.Employee;
import com.gyan.darpan.enums.DbType;
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
        DbContextHolder.set(DbType.MASTER);
        Employee employee1 = employeeJdbcRepository.saveEmployee(employee);
        DbContextHolder.clear();
        return employee1;
    }

    @GetMapping(path = "{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployeeById(@PathVariable("employeeId") long employeeId) {
        DbContextHolder.set(DbType.SLAVE);
        Employee employee = employeeJdbcRepository.findByEmployeeId(employeeId);
        DbContextHolder.clear();
        return employee;
    }
}
