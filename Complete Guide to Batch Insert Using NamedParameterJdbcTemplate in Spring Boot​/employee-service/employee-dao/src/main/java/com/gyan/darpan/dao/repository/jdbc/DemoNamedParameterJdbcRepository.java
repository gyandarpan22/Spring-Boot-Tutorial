package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
public class DemoNamedParameterJdbcRepository {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DemoNamedParameterJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void insertEmployees(List<Employee> employees) {
        String query = "insert into employee(employee_name,age,joining_date,department) values (:employeeName,:age,:joiningDate,:department)";

        SqlParameterSource[] sqlParameterSources = new SqlParameterSource[employees.size()];

        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            sqlParameterSources[i] = new MapSqlParameterSource()
                    .addValue("employeeName", employee.getEmployeeName())
                    .addValue("age", employee.getAge())
                    .addValue("joiningDate", employee.getJoiningDate())
                    .addValue("department", employee.getDepartment());
        }

        int[] insertedRow = namedParameterJdbcTemplate.batchUpdate(query, sqlParameterSources);

    }

    public void insertEmployees1(List<Employee> employees) {
        String query = "insert into employee(employee_name,age,joining_date,department) values (:employeeName,:age,:joiningDate,:department)";

        SqlParameterSource[] sqlParameterSources = SqlParameterSourceUtils.createBatch(employees);


        int[] insertedRow = namedParameterJdbcTemplate.batchUpdate(query, sqlParameterSources);

    }

    public List<Employee> insertEmployees2(List<Employee> employees) {
        String query = "insert into employee(employee_name,age,joining_date,department) values (:employeeName,:age,:joiningDate,:department)";

        SqlParameterSource[] sqlParameterSources = SqlParameterSourceUtils.createBatch(employees);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int[] insertedRow = namedParameterJdbcTemplate.batchUpdate(query, sqlParameterSources, keyHolder, new String[]{"employee_id"});

        List<Map<String, Object>> generatedKeys = keyHolder.getKeyList();

        for (int i = 0; i < employees.size(); i++) {
            Map<String, Object> generateKeyMap = generatedKeys.get(i);
            long employeeId = ((BigInteger) generateKeyMap.get("GENERATED_KEY")).longValue();

            employees.get(i).setEmployeeId(employeeId);
        }

        return employees;

    }

}
