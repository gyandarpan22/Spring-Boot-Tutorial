package com.gyan.darpan.dao.repository.jpa;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        return employeeJpaRepository.findById(employeeId);
    }

    @Override
    public Optional<Employee> findByEmployeeIdForUpdate(long employeeId) {
        return Optional.empty();
    }

    @Override
    public PageResponse<Employee> fetchEmployees(int pageNumber, int pageSize, String employeeName, String department) {
        //Sort sort=Sort.by("employeeName").ascending()

        // Sort sort=Sort.by("employeeName").ascending().and(Sort.by("department").descending());

        Sort sort = Sort.by(Sort.Order.asc("employeeName"), Sort.Order.desc("department"));


        List<Employee> employees = employeeJpaRepository.findAll(sort);

        long totalEmployees = employees.size();

        return new PageResponse<>(employees, pageNumber, pageSize, totalEmployees);
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
