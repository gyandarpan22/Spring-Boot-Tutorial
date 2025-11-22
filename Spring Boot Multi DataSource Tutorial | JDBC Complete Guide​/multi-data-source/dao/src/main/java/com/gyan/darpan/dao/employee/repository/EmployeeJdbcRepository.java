package com.gyan.darpan.dao.employee.repository;

import com.gyan.darpan.dao.employee.entity.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeJdbcRepository(@Qualifier("employeeJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Employee findById(Long employeeId) {
        String sql = "Select * from employee where employee_id = ?";

        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class), employeeId);
    }
}
