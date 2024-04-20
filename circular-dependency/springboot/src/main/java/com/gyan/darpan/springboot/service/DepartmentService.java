package com.gyan.darpan.springboot.service;

public interface DepartmentService {

    String getDepartmentNameByEmployeeId(long employeeId);

    String getEmployeesInDepartment(String department);

}
