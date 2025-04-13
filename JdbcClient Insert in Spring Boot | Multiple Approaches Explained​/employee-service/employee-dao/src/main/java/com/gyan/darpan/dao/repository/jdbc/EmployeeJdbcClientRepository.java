package com.gyan.darpan.dao.repository.jdbc;


import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
        return Optional.empty();
    }

    @Override
    public Optional<Employee> findByEmployeeIdForUpdate(long employeeId) {
        return Optional.empty();
    }

    @Override
    public PageResponse<Employee> fetchEmployees(int pageNumber, int pageSize, String employeeName, String department) {
        return null;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public int deleteEmployee(long employeeId) {
        return 0;
    }
}
