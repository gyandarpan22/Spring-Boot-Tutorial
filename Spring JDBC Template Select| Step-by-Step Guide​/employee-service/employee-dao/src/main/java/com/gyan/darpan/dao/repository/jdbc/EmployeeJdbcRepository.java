package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import com.gyan.darpan.dao.row.mapper.EmployeeRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

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

    @Override
    public Optional<Employee> findByEmployeeId(long employeeId) {

        String query = "Select * from employee where employee_id=?";

//        return jdbcTemplate.query(
//                query,
//                rs -> {
//                    if (rs.next()) {
//                        Employee employee = Employee.builder()
//                                .employeeId(rs.getLong("employee_id"))
//                                .employeeName(rs.getString("employee_name"))
//                                .age(rs.getInt("age"))
//                                .joiningDate(rs.getDate("joining_date"))
//                                .department(rs.getString("department"))
//                                .createdDate(rs.getTimestamp("created_date"))
//                                .modifiedDate(rs.getTimestamp("modified_date"))
//                                .build();
//                        return Optional.of(employee);
//                    }
//                    return Optional.empty();
//                },
//                employeeId
//        );
        Employee employee = null;
        try {
            //employee = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Employee.class), employeeId);
            employee = jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), employeeId);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.error("Data not found : {} ", emptyResultDataAccessException.getMessage());
        }

        return Optional.ofNullable(employee);
    }
}
