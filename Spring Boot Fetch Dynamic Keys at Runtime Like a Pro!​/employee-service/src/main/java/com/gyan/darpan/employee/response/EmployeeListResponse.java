package com.gyan.darpan.employee.response;

import com.gyan.darpan.employee.model.Employee;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeListResponse {
    private List<Employee> employees;
    private int currentPageNumber;
    private int totalPage;
}
