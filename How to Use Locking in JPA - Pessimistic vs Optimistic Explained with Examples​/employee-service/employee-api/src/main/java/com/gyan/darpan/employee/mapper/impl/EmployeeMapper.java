package com.gyan.darpan.employee.mapper.impl;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.mapper.EntityDTOMapper;
import com.gyan.darpan.employee.model.EmployeeDTO;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.function.Function;

@Component("employeeMapper")
public class EmployeeMapper implements EntityDTOMapper<Employee, EmployeeDTO> {
    @Override
    public Function<Employee, EmployeeDTO> toDTO() {
        return employee -> EmployeeDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .age(employee.getAge())
                .joiningDate(employee.getJoiningDate().toLocalDate())
                .department(Department.valueOf(employee.getDepartment()))
                .build();
    }

    @Override
    public Function<EmployeeDTO, Employee> toEntity() {
        return employee -> Employee.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .age(employee.getAge())
                .joiningDate(Date.valueOf(employee.getJoiningDate()))
                .department(employee.getDepartment().name())
                .build();
    }
}
