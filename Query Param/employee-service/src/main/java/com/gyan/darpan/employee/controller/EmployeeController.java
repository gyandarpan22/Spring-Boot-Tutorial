package com.gyan.darpan.employee.controller;

import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.model.Employee;
import com.gyan.darpan.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Employee>> getEmployees(
            @RequestParam(name = "employeeName", required = false) String employeeName,
            @RequestParam(name = "department", required = false) Department department
    ) {
        List<Employee> employees = employeeService.getEmployees(employeeName, department);

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(path = "{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getEmployee(@PathVariable(name = "employeeId") long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

}
