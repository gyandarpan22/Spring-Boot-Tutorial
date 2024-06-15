package com.gyan.darpan.employee.service;

import com.gyan.darpan.employee.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getEmployees();

    Employee getEmployee(long employeeId);
}
