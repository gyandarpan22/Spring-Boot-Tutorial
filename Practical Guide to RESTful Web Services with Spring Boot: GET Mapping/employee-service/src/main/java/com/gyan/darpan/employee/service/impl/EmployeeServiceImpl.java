package com.gyan.darpan.employee.service.impl;

import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.model.Employee;
import com.gyan.darpan.employee.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private Map<Long, Employee> employeeMap;

    public EmployeeServiceImpl() {
        employeeMap = new HashMap<>();

        Employee employee1 = Employee.builder()
                .employeeId(1L)
                .employeeName("Ram")
                .age(22)
                .joiningDate(new Date())
                .department(Department.DEVELOPER)
                .build();

        Employee employee2 = Employee.builder()
                .employeeId(2L)
                .employeeName("Shyam")
                .age(25)
                .joiningDate(new Date())
                .department(Department.QA)
                .build();

        Employee employee3 = Employee.builder()
                .employeeId(3L)
                .employeeName("Mohan")
                .age(24)
                .joiningDate(new Date())
                .department(Department.HR)
                .build();


        employeeMap.put(employee1.getEmployeeId(), employee1);
        employeeMap.put(employee2.getEmployeeId(), employee2);
        employeeMap.put(employee3.getEmployeeId(), employee3);

    }


    @Override
    public List<Employee> getEmployees() {
        return new ArrayList<>(employeeMap.values());
    }
}
