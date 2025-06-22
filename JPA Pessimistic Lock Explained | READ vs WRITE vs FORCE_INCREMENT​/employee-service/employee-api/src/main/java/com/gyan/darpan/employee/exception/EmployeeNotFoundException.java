package com.gyan.darpan.employee.exception;

import lombok.Getter;

@Getter
public class EmployeeNotFoundException extends RuntimeException {
    private String message;
    private int statusCode;

    public EmployeeNotFoundException(long employeeId) {
        super("Employee with id " + employeeId + " not found");
        this.message = "Employee with id " + employeeId + " not found";
        this.statusCode = 404;
    }

}
