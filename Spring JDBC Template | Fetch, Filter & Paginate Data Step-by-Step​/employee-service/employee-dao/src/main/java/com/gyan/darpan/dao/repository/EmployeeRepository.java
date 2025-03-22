package com.gyan.darpan.dao.repository;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;

import java.util.Optional;

public interface EmployeeRepository {

    Employee saveEmployee(Employee employee);

    Optional<Employee> findByEmployeeId(long employeeId);

    PageResponse<Employee> fetchEmployees(int pageNumber, int pageSize, String employeeName, String department);

}
