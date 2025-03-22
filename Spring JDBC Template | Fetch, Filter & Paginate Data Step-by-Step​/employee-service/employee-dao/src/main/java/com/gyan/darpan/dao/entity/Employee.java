package com.gyan.darpan.dao.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    private long employeeId;
    private String employeeName;
    private int age;
    private Date joiningDate;
    private String department;
    private Timestamp createdDate;
    private Timestamp modifiedDate;

}
