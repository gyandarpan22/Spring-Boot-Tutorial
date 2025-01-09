package com.gyan.darpan.employee.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.gyan.darpan.employee.enums.Department;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "employee")
public class Employee {
    private long employeeId;
    private String employeeName;
    private int age;
    private Date joiningDate;
    private Department department;
}
