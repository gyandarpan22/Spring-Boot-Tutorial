package com.gyan.darpan.dao.repository;

import com.gyan.darpan.dao.entity.Employee;

import java.util.Optional;

public interface EmployeeRepository {

    Employee saveEmployee(Employee employee);

    Optional<Employee> findByEmployeeId(long employeeId);

}
