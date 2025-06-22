package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
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
import java.util.ArrayList;
import java.util.List;
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

        Employee employee = null;
        try {
            employee = jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), employeeId);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.error("Data not found : {} ", emptyResultDataAccessException.getMessage());
        }

        return Optional.ofNullable(employee);
    }

    @Override
    public Optional<Employee> findByEmployeeIdForUpdate(long employeeId) {

        String query = "Select * from employee where employee_id=? for update";

        Employee employee = null;
        try {
            employee = jdbcTemplate.queryForObject(query, new EmployeeRowMapper(), employeeId);
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.error("Data not found : {} ", emptyResultDataAccessException.getMessage());
        }

        return Optional.ofNullable(employee);
    }

    //pageNumber start from 0,1,...
    @Override
    public PageResponse<Employee> fetchEmployees(int pageNumber, int pageSize, String employeeName, String department) {

        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        if (employeeName != null && !employeeName.isBlank()) {
            conditions.add("employee_name= ? ");
            params.add(employeeName);
        }

        if (department != null && !department.isBlank()) {
            conditions.add("department= ? ");
            params.add(department);
        }

        String query = "Select * from employee ";
        String countQuery = "Select count(*) from employee ";
        if (!conditions.isEmpty()) {
            String condition = " WHERE " + String.join(" AND ", conditions);
            query = query + condition;
            countQuery = countQuery + condition;
        }

        long totalEmployees = jdbcTemplate.queryForObject(countQuery, Long.class, params.toArray());

        int offset = pageNumber * pageSize;

        query = query + " limit ? offset ?";

        params.add(pageSize);
        params.add(offset);

        log.info("fetchEmployees query : {} ", query);

        List<Employee> employees = jdbcTemplate.query(query, new EmployeeRowMapper(), params.toArray());

        return new PageResponse<>(employees, pageNumber, pageSize, totalEmployees);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        String query = "Update employee set employee_name=? , age=? , department=? where employee_id=?";

        int updateCount = jdbcTemplate.update(query, employee.getEmployeeName(), employee.getAge(), employee.getDepartment(), employee.getEmployeeId());

        log.info("Update Count : {} ", updateCount);

        return employee;
    }

    @Override
    public int deleteEmployee(long employeeId) {
        String query = "delete from employee where employee_id=?";

        int deleteCount = jdbcTemplate.update(query, employeeId);

        log.info("Delete Count : {} ", deleteCount);

        return deleteCount;

    }


}
