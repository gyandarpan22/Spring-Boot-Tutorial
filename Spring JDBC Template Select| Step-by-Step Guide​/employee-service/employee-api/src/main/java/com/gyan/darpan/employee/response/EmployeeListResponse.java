package com.gyan.darpan.employee.response;

import com.gyan.darpan.employee.model.EmployeeDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeListResponse {
    private List<EmployeeDTO> employeeDTOS;
    private int currentPageNumber;
    private int totalPage;
}
