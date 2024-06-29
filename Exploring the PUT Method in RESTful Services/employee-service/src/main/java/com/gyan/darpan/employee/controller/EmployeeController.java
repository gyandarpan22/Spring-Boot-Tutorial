package com.gyan.darpan.employee.controller;

import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.model.Employee;
import com.gyan.darpan.employee.response.EmployeeListResponse;
import com.gyan.darpan.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeListResponse> getEmployees(
            @RequestParam(name = "employeeName", required = false) String employeeName,
            @RequestParam(name = "department", required = false) Department department,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNumber
    ) {
        EmployeeListResponse employeeListResponse = employeeService.getEmployees(employeeName, department, pageNumber);

        return new ResponseEntity<>(employeeListResponse, HttpStatus.OK);
    }

    @GetMapping(path = "{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getEmployee(@PathVariable(name = "employeeId") long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee employee1 = employeeService.addEmployee(employee);

        return new ResponseEntity<>(employee1, HttpStatus.CREATED);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        Employee employee1 = employeeService.updateEmployee(employee);

        return new ResponseEntity<>(employee1, HttpStatus.OK);
    }

}
