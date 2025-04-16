package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.row.mapper.EmployeeRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
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

    public void saveAll(List<Employee> employees) {
        String query = "insert into employee(employee_name,age,joining_date,department) values (?,?,?,?)";

        int[] numberOfRowAffected = jdbcTemplate.batchUpdate(
                query,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement pstmt, int i) throws SQLException {

                        Employee employee = employees.get(i);

                        pstmt.setString(1, employee.getEmployeeName());
                        pstmt.setInt(2, employee.getAge());
                        pstmt.setDate(3, employee.getJoiningDate());
                        pstmt.setString(4, employee.getDepartment());
                    }

                    @Override
                    public int getBatchSize() {
                        return employees.size();
                    }
                }
        );

        log.info("saveAll : {} ", numberOfRowAffected);
    }

    public void saveAll1(List<Employee> employees) {
        String query = "insert into employee(employee_name,age,joining_date,department) values (?,?,?,?)";

        List<Object[]> batchArgs = employees.stream()
                .map(
                        employee -> new Object[]{employee.getEmployeeName(), employee.getAge(), employee.getJoiningDate(), employee.getDepartment()}
                )
                .toList();

        int[] numberOfRowAffected = jdbcTemplate.batchUpdate(query, batchArgs);

        log.info("saveAll1 : {} ", numberOfRowAffected);

    }
}
