package com.gyan.darpan.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Employee {
    private Long employeeId;
    private String employeeName;
    private int age;
    private Date joiningDate;
    private String department;
    private double salary;
    private Timestamp created_date;
    private Timestamp modified_date;
}
