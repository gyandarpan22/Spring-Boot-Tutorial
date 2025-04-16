package com.gyan.darpan.dao.repository.jdbc;


import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("employeeJdbcClientRepository")
@Slf4j
public class EmployeeJdbcClientRepository implements EmployeeRepository {

    private final JdbcClient jdbcClient;

    public EmployeeJdbcClientRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        //positional Parameter approach : 1st Approach,2nd Approach,3rd Approach
        //  String query = "insert into employee(employee_name,age,joining_date,department) values (?,?,?,?)";

        //named parameter approach: 4th Approach ,5th Approach,6th Approach
        String query = "insert into employee(employee_name,age,joining_date,department) values (:employeeName,:age,:joiningDate,:department)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        //1st Approach
//        int insertedRow = jdbcClient.sql(query)
//                .params(employee.getEmployeeName(), employee.getAge(), employee.getJoiningDate(), employee.getDepartment())
//                .update(keyHolder, "employee_id");

        //2nd Approach
//        int insertedRow = jdbcClient.sql(query)
//                .param(employee.getEmployeeName())
//                .param(employee.getAge())
//                .param(employee.getJoiningDate())
//                .param(employee.getDepartment())
//                .update(keyHolder, "employee_id");

        //3rd Approach
//        int insertedRow = jdbcClient.sql(query)
//                .param(1, employee.getEmployeeName())
//                .param(2, employee.getAge())
//                .param(3, employee.getJoiningDate())
//                .param(4, employee.getDepartment())
//                .update(keyHolder, "employee_id");


        //4th Approach
//        int insertedRow = jdbcClient.sql(query)
//                .param("employeeName", employee.getEmployeeName())
//                .param("age", employee.getAge())
//                .param("joiningDate", employee.getJoiningDate())
//                .param("department", employee.getDepartment())
//                .update(keyHolder, "employee_id");


        //5th Approach
//        int insertedRow = jdbcClient.sql(query)
//                .paramSource(new BeanPropertySqlParameterSource(employee))
//                .update(keyHolder, "employee_id");

        // 6th Approach
        int insertedRow = jdbcClient.sql(query)
                .paramSource(employee)
                .update(keyHolder, "employee_id");

        log.info("JdbcClient inserted Row : {}", insertedRow);

        employee.setEmployeeId(keyHolder.getKey().longValue());

        return employee;
    }

    @Override
    public Optional<Employee> findByEmployeeId(long employeeId) {
        String query = "Select * from employee where employee_id=:employeeId";

        return jdbcClient.sql(query)
                .param("employeeId", employeeId)
                .query(Employee.class)
                .optional();
    }

    @Override
    public Optional<Employee> findByEmployeeIdForUpdate(long employeeId) {
        String query = "Select * from employee where employee_id=:employeeId for update";

        return jdbcClient.sql(query)
                .param("employeeId", employeeId)
                .query(Employee.class)
                .optional();
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

        long totalEmployees = jdbcClient.sql(countQuery)
                .paramSource(mapSqlParameterSource)
                .query(Long.class)
                .single();

        int offset = pageNumber * pageSize;

        query = query + " limit :limit offset :offset";

        mapSqlParameterSource.addValue("limit", pageSize);
        mapSqlParameterSource.addValue("offset", offset);

        log.info("fetchEmployees query : {} ", query);

        List<Employee> employees = jdbcClient.sql(query)
                .paramSource(mapSqlParameterSource)
                .query(Employee.class)
                .list();

        return new PageResponse<>(employees, pageNumber, pageSize, totalEmployees);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        String query = "Update employee set employee_name=:employeeName , age=:age , department=:department where employee_id=:employeeId";

        int updatedCount = jdbcClient.sql(query)
                .paramSource(employee)
                .update();

        log.info("JdbcClient: updateEmployee : {}", updatedCount);

        return employee;
    }

    @Override
    public int deleteEmployee(long employeeId) {
        return 0;
    }
}
