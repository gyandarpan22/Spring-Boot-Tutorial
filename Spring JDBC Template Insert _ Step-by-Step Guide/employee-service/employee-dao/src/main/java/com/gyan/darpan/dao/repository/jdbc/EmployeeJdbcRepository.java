package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository("employeeJdbcRepository")
@Slf4j
public class EmployeeJdbcRepository implements EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        String query = "insert into employee(employee_name,age,joining_date,department) values (?,?,?,?)";

        // int insertCount=jdbcTemplate.update(query,employee.getEmployeeName(),employee.getAge(),employee.getJoiningDate(),employee.getDepartment());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int insertCount = jdbcTemplate.update(
                con -> {
                    PreparedStatement pstmt = con.prepareStatement(query, new String[]{"employee_id"});
                    pstmt.setString(1, employee.getEmployeeName());
                    pstmt.setInt(2, employee.getAge());
                    pstmt.setDate(3, employee.getJoiningDate());
                    pstmt.setString(4, employee.getDepartment());

                    return pstmt;
                },
                keyHolder
        );

        employee.setEmployeeId(keyHolder.getKey().longValue());

        log.info("Row inserted : {}", insertCount);

        return employee;
    }
}
