package com.gyan.darpan.repository;

import com.gyan.darpan.entity.Employee;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class EmployeeJdbcRepository {

    private final JdbcTemplate masterJdbcTemplate;
    private final JdbcTemplate slaveJdbcTemplate;

    public EmployeeJdbcRepository(@Qualifier("masterEmployeeJdbcTemplate") JdbcTemplate masterJdbcTemplate,
                                  @Qualifier("slaveEmployeeJdbcTemplate") JdbcTemplate slaveJdbcTemplate) {
        this.masterJdbcTemplate = masterJdbcTemplate;
        this.slaveJdbcTemplate = slaveJdbcTemplate;
    }

    public Employee findByEmployeeId(long employeeId) {
        String query = "Select *from employee where employee_id=?";
        return slaveJdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Employee.class), employeeId);
    }

    public Employee saveEmployee(Employee employee) {
        String query = "insert into employee (employee_name,age,joining_date,department,salary) values(?,?,?,?,?)";


        KeyHolder keyHolder = new GeneratedKeyHolder();

        int insertedCount = masterJdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(query, new String[]{"employee_id"});

            pstmt.setString(1, employee.getEmployeeName());
            pstmt.setInt(2, employee.getAge());
            pstmt.setDate(3, employee.getJoiningDate());
            pstmt.setString(4, employee.getDepartment());
            pstmt.setDouble(5, employee.getSalary());
            return pstmt;
        }, keyHolder);


        Long employeeId = keyHolder.getKey().longValue();

        employee.setEmployeeId(employeeId);

        return employee;

    }
}
