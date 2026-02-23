package com.gyan.darpan.controller;

import com.gyan.darpan.dto.EmployeeDto;
import com.gyan.darpan.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeDto addEmployee(@RequestBody EmployeeDto employeeDto) {
        return employeeService.addEmployee(employeeDto);
    }

    @GetMapping("{employeeId}")
    public EmployeeDto getEmployee(@PathVariable long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @DeleteMapping("{employeeId}")
    public String deleteEmployee(@PathVariable long employeeId) {
        employeeService.deleteEmployee(employeeId);

        return "Deleted";
    }
}
