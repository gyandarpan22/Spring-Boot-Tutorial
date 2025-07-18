package com.gyan.darpan.dao.repository.jpa;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository("employeeRepositoryJpaImpl")
public class EmployeeRepositoryJpaImpl implements EmployeeRepository {

    private final EmployeeJpaRepository employeeJpaRepository;

    public EmployeeRepositoryJpaImpl(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeJpaRepository.save(employee);
    }

    @Override
    public Optional<Employee> findByEmployeeId(long employeeId) {
        return Optional.empty();
    }

    @Override
    public Optional<Employee> findByEmployeeIdForUpdate(long employeeId) {
        return Optional.empty();
    }

    @Override
    public PageResponse<Employee> fetchEmployees(int pageNumber, int pageSize, String employeeName, String department) {
        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public int deleteEmployee(long employeeId) {
        return 0;
    }
}
