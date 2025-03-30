package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.row.mapper.EmployeeRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class DemoJdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public DemoJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PageResponse<Employee> fetchEmployees(int pageNumber, int pageSize, String employeeName, String department) {
        String query = "{CALL fetch_employees(?,?,?,?)}";

        return jdbcTemplate.execute(
                query,
                (CallableStatementCallback<PageResponse<Employee>>) cs -> {
                    cs.setString(1, employeeName);
                    cs.setString(2, department);
                    cs.setInt(3, pageNumber);
                    cs.setInt(4, pageSize);

                    boolean hasResults = cs.execute();

                    long totalRecord = 0;
                    List<Employee> employees = new ArrayList<>();

                    if (hasResults) {
                        try (ResultSet rs = cs.getResultSet()) {
                            if (rs.next()) {
                                totalRecord = rs.getLong(1);
                            }
                        }
                    }
                    hasResults = cs.getMoreResults();

                    EmployeeRowMapper employeeRowMapper = new EmployeeRowMapper();

                    if (hasResults) {
                        try (ResultSet rs = cs.getResultSet()) {
                            int rowNum = 0;
                            while (rs.next()) {
                                employees.add(employeeRowMapper.mapRow(rs, rowNum++));
                            }
                        }
                    }

                    return new PageResponse<>(employees, pageNumber, pageSize, totalRecord);

                }
        );
    }

    public PageResponse<Employee> fetchEmployeesWithSimpleJdbc(int pageNumber, int pageSize, String employeeName, String department) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("fetch_employees")
                .returningResultSet("totalRecords", (rs, rowNum) -> rs.getLong(1))
                .returningResultSet("employees", new EmployeeRowMapper());

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("employee_name", employeeName);
        mapSqlParameterSource.addValue("department", department);
        mapSqlParameterSource.addValue("page_number", pageNumber);
        mapSqlParameterSource.addValue("page_size", pageSize);

        Map<String, Object> result = simpleJdbcCall.execute(mapSqlParameterSource);

        long totalRecord = ((List<Long>) result.get("totalRecords")).get(0);
        List<Employee> employees = (List<Employee>) result.get("employees");

        return new PageResponse<>(employees, pageNumber, pageSize, totalRecord);
    }
}
