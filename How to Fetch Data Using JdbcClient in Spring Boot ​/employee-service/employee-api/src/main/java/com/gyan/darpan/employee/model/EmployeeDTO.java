package com.gyan.darpan.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.gyan.darpan.employee.enums.Department;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JacksonXmlRootElement(localName = "employee")
public class EmployeeDTO {
    private long employeeId;
    private String employeeName;
    private int age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;
    private Department department;
}
