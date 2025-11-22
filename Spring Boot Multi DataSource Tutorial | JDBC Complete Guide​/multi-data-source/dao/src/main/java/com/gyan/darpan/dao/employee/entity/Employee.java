package com.gyan.darpan.dao.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Long employeeId;
    private String employeeName;
    private Integer age;
    private Date joiningDate;
    private String department;
    private Double salary;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
}
