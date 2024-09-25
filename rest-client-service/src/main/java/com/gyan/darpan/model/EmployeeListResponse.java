package com.gyan.darpan.model;

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
