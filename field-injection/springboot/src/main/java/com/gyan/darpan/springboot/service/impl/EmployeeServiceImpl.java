package com.gyan.darpan.springboot.service.impl;

import com.gyan.darpan.springboot.annotation.ExcludeFilterAnnotation;
import com.gyan.darpan.springboot.service.EmployeeService;
import org.springframework.stereotype.Component;

//@ExcludeFilterAnnotation
@Component("employeeServiceImpl")
//employeeServiceImpl : it is a bean name
// EmployeeService employeeServiceImpl=new EmployeeServiceImpl();
//employeeServiceImpl : store the same bean with name "employeeServiceImpl"
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public String getEmployee() {
        return "Gyan Darpan";
    }
}
