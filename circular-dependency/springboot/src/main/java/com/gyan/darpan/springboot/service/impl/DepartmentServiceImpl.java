package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.DepartmentService;
import com.gyan.darpan.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    private EmployeeService employeeService;

//    @Autowired
//    public DepartmentServiceImpl(@Lazy EmployeeService employeeService){
//        this.employeeService=employeeService;
//    }


    @Override
    public String getDepartmentNameByEmployeeId(long employeeId) {
        return "Engineering";
    }

    @Override
    public String getEmployeesInDepartment(String department) {
        return "List of employees in "+department;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
}
