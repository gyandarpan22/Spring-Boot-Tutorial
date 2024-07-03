package com.gyan.darpan.employee.service;

import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.model.Employee;
import com.gyan.darpan.employee.response.EmployeeListResponse;

public interface EmployeeService {

    EmployeeListResponse getEmployees(String employeeName, Department department, int pageNumber);

    Employee getEmployee(long employeeId);

    Employee addEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    boolean deleteEmployee(long employeeId);
}
