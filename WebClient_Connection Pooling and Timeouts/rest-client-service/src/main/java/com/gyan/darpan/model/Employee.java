package com.gyan.darpan.model;

import com.gyan.darpan.enums.Department;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    private long employeeId;
    private String employeeName;
    private int age;
    private Date joiningDate;
    private Department department;
}
