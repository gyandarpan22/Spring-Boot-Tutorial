package com.gyan.darpan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDto {
    private Long employeeId;
    private String employeeName;
    private int age;
    private Date joiningDate;
    private String department;
    private double salary;
    private BankDetailDto bankDetailDto;
}
