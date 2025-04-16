package com.gyan.darpan.employee.service;

import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.model.EmployeeDTO;
import com.gyan.darpan.employee.response.EmployeeListResponse;

public interface EmployeeService {

    EmployeeListResponse getEmployees(String employeeName, Department department, int pageNumber);

    EmployeeDTO getEmployeeById(long employeeId);

    EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO);

    boolean deleteEmployee(long employeeId);
}
