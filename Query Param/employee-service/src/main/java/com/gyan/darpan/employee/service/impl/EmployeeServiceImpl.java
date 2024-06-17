package com.gyan.darpan.employee.service.impl;

import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.model.Employee;
import com.gyan.darpan.employee.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Employee employee4 = Employee.builder()
                .employeeId(4L)
                .employeeName("Mohan")
                .age(24)
                .joiningDate(new Date())
                .department(Department.HR)
                .build();

        Employee employee5 = Employee.builder()
                .employeeId(5L)
                .employeeName("Sohan")
                .age(24)
                .joiningDate(new Date())
                .department(Department.DEVELOPER)
                .build();


        employeeMap.put(employee1.getEmployeeId(), employee1);
        employeeMap.put(employee2.getEmployeeId(), employee2);
        employeeMap.put(employee3.getEmployeeId(), employee3);
        employeeMap.put(employee4.getEmployeeId(), employee4);
        employeeMap.put(employee5.getEmployeeId(), employee5);

    }


    @Override
    public List<Employee> getEmployees(String employeeName, Department department) {
        List<Employee> employees = employeeMap.values()
                .stream()
                .filter(employee -> {
                    if (employeeName != null && !employeeName.isBlank()) {
                        return employeeName.equalsIgnoreCase(employee.getEmployeeName());
                    }
                    return true;
                })
                .filter(employee -> {
                    if (department != null) {
                        return department == employee.getDepartment();
                    }
                    return true;
                })
                .toList();

        return employees;
    }

    @Override
    public Employee getEmployee(long employeeId) {
        return employeeMap.get(employeeId);
    }
}
