package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.service.DepartmentService;
import com.gyan.darpan.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private DepartmentService departmentService;

//    @Autowired
//    public EmployeeServiceImpl(@Lazy DepartmentService departmentService) {
//        this.departmentService = departmentService;
//    }

    @Override
    public String getEmployeeDetails(long employeeId) {
        return "Employee ID: " + employeeId + ", Department : " + departmentService.getDepartmentNameByEmployeeId(employeeId);
    }

    @Autowired
    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
}
