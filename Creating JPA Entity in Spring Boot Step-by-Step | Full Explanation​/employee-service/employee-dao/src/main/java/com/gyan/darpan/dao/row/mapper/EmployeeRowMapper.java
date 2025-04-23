package com.gyan.darpan.dao.row.mapper;

import com.gyan.darpan.dao.entity.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Employee.builder()
                .employeeId(rs.getLong("employee_id"))
                .employeeName(rs.getString("employee_name"))
                .age(rs.getInt("age"))
                .joiningDate(rs.getDate("joining_date"))
                .department(rs.getString("department"))
                .createdDate(rs.getTimestamp("created_date"))
                .modifiedDate(rs.getTimestamp("modified_date"))
                .build();
    }
}
