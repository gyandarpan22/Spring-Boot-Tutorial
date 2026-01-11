package com.gyan.darpan.controller;

import com.gyan.darpan.datasource.DbContextHolder;
import com.gyan.darpan.entity.Employee;
import com.gyan.darpan.enums.DbType;
import com.gyan.darpan.repository.EmployeeJpaRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("master-slave/employee")
public class EmployeeController {
    private final EmployeeJpaRepository employeeJpaRepository;

    public EmployeeController(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee addEmployee(@RequestBody Employee employee) {
        DbContextHolder.set(DbType.MASTER);
        Employee employee1 = employeeJpaRepository.save(employee);
        DbContextHolder.clear();
        return employee1;
    }

    @GetMapping(path = "{employeeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Employee getEmployeeById(@PathVariable("employeeId") long employeeId) {
        DbContextHolder.set(DbType.SLAVE);
        Employee employee = employeeJpaRepository.findById(employeeId).get();
        DbContextHolder.clear();
        return employee;
    }
}
