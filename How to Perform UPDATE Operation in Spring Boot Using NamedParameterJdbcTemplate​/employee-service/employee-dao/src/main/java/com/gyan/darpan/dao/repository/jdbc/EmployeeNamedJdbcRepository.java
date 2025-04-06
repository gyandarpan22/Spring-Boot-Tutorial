package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import com.gyan.darpan.dao.row.mapper.EmployeeRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("employeeNamedJdbcRepository")
@Slf4j
public class EmployeeNamedJdbcRepository implements EmployeeRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public EmployeeNamedJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        String query = "insert into employee(employee_name,age,joining_date,department) values (:employeeName,:age,:joiningDate,:department)";

//        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
//                .addValue("employeeName", employee.getEmployeeName())
//                .addValue("age", employee.getAge())
//                .addValue("joiningDate", employee.getJoiningDate())
//                .addValue("department", employee.getDepartment());

        SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(employee);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int insertCount = namedParameterJdbcTemplate.update(query, sqlParameterSource, keyHolder, new String[]{"employee_id"});

        employee.setEmployeeId(keyHolder.getKey().longValue());

        log.info("EmployeeNamedJdbcRepository::Row inserted : {}", insertCount);

        return employee;
    }

    @Override
    public Optional<Employee> findByEmployeeId(long employeeId) {

        String query = "Select * from employee where employee_id=:employeeId";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("employeeId", employeeId);

        Employee employee = null;
        try {
            employee = namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, new EmployeeRowMapper());
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.error("Data not found : {} ", emptyResultDataAccessException.getMessage());
        }

        return Optional.ofNullable(employee);
    }

    @Override
    public Optional<Employee> findByEmployeeIdForUpdate(long employeeId) {
        String query = "Select * from employee where employee_id=:employeeId for update";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("employeeId", employeeId);

        Employee employee = null;
        try {
            employee = namedParameterJdbcTemplate.queryForObject(query, sqlParameterSource, new EmployeeRowMapper());
        } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
            log.error("Data not found : {} ", emptyResultDataAccessException.getMessage());
        }

        return Optional.ofNullable(employee);
    }

    @Override
    public PageResponse<Employee> fetchEmployees(int pageNumber, int pageSize, String employeeName, String department) {

        List<String> conditions = new ArrayList<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        if (employeeName != null && !employeeName.isBlank()) {
            conditions.add("employee_name= :employeeName ");
            mapSqlParameterSource.addValue("employeeName", employeeName);
        }

        if (department != null && !department.isBlank()) {
            conditions.add("department=:department ");
            mapSqlParameterSource.addValue("department", department);
        }

        String query = "Select * from employee ";
        String countQuery = "Select count(*) from employee ";
        if (!conditions.isEmpty()) {
            String condition = " WHERE " + String.join(" AND ", conditions);
            query = query + condition;
            countQuery = countQuery + condition;
        }

        long totalEmployees = namedParameterJdbcTemplate.queryForObject(countQuery, mapSqlParameterSource, Long.class);

        int offset = pageNumber * pageSize;

        query = query + " limit :limit offset :offset";

        mapSqlParameterSource.addValue("limit", pageSize);
        mapSqlParameterSource.addValue("offset", offset);

        log.info("fetchEmployees query : {} ", query);

        List<Employee> employees = namedParameterJdbcTemplate.query(query, mapSqlParameterSource, new EmployeeRowMapper());

        return new PageResponse<>(employees, pageNumber, pageSize, totalEmployees);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        String query = "Update employee set employee_name=:employeeName , age=:age , department=:department where employee_id=:employeeId";

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("employeeName", employee.getEmployeeName())
                .addValue("age", employee.getAge())
                .addValue("department", employee.getDepartment())
                .addValue("employeeId", employee.getEmployeeId());

        int updateCount = namedParameterJdbcTemplate.update(query, sqlParameterSource);

        log.info("Update Count : {} ", updateCount);

        return employee;
    }

    @Override
    public int deleteEmployee(long employeeId) {
        return 0;
    }
}
