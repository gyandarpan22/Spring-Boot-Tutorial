package com.gyan.darpan.employee.service.impl;

import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.exception.EmployeeNotFoundException;
import com.gyan.darpan.employee.model.Employee;
import com.gyan.darpan.employee.response.EmployeeListResponse;
import com.gyan.darpan.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private Map<Long, Employee> employeeMap;
    private long sequenceId;

    private final int pageSize;


    @Autowired
    public EmployeeServiceImpl(@Value("${employee.page.size}") int pageSize) {
        this.pageSize = pageSize;
        this.sequenceId = 1L;

        employeeMap = new LinkedHashMap<>();

        Employee employee1 = Employee.builder()
                .employeeId(sequenceId++)
                .employeeName("Ram")
                .age(22)
                .joiningDate(new Date())
                .department(Department.DEVELOPER)
                .build();

        Employee employee2 = Employee.builder()
                .employeeId(sequenceId++)
                .employeeName("Shyam")
                .age(25)
                .joiningDate(new Date())
                .department(Department.QA)
                .build();

        Employee employee3 = Employee.builder()
                .employeeId(sequenceId++)
                .employeeName("Mohan")
                .age(24)
                .joiningDate(new Date())
                .department(Department.HR)
                .build();

        Employee employee4 = Employee.builder()
                .employeeId(sequenceId++)
                .employeeName("Mohan")
                .age(24)
                .joiningDate(new Date())
                .department(Department.HR)
                .build();

        Employee employee5 = Employee.builder()
                .employeeId(sequenceId++)
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
    public EmployeeListResponse getEmployees(String employeeName, Department department, int pageNumber) {
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

        int totalPage = employees.size() / pageSize + ((employees.size() % pageSize) == 0 ? 0 : 1);

        int skip = (pageNumber - 1) * pageSize;

        List<Employee> employees1 = employees.stream()
                .skip(skip)
                .limit(pageSize)
                .toList();


        return EmployeeListResponse.builder()
                .employees(employees1)
                .currentPageNumber(pageNumber)
                .totalPage(totalPage)
                .build();
    }

    @Override
    public Employee getEmployee(long employeeId) {
        return employeeMap.get(employeeId);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        employee.setEmployeeId(sequenceId++);
        employee.setJoiningDate(new Date());

        employeeMap.put(employee.getEmployeeId(), employee);

        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        if (!employeeMap.containsKey(employee.getEmployeeId())) {
            throw new EmployeeNotFoundException(employee.getEmployeeId());
        }

        Employee oldEmployeeObj = employeeMap.get(employee.getEmployeeId());

        oldEmployeeObj.setEmployeeName(employee.getEmployeeName());
        oldEmployeeObj.setAge(employee.getAge());
        oldEmployeeObj.setDepartment(employee.getDepartment());

        return oldEmployeeObj;

    }
}
