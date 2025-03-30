package com.gyan.darpan.employee.controller;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.jdbc.DemoJdbcTemplateRepository;
import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.mapper.EntityDTOMapper;
import com.gyan.darpan.employee.model.EmployeeDTO;
import com.gyan.darpan.employee.response.EmployeeListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoJdbcTemplateController {

    private final DemoJdbcTemplateRepository demoJdbcTemplateRepository;
    private final EntityDTOMapper<Employee, EmployeeDTO> employeeDTOEntityDTOMapper;

    @Autowired
    public DemoJdbcTemplateController(DemoJdbcTemplateRepository demoJdbcTemplateRepository,
                                      @Qualifier("employeeMapper") EntityDTOMapper<Employee, EmployeeDTO> employeeDTOEntityDTOMapper) {
        this.demoJdbcTemplateRepository = demoJdbcTemplateRepository;
        this.employeeDTOEntityDTOMapper = employeeDTOEntityDTOMapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "procedure/demo")
    public EmployeeListResponse getEmployees(
            @RequestParam(name = "employeeName", required = false) String employeeName,
            @RequestParam(name = "department", required = false) Department department,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNumber
    ) {
        PageResponse<Employee> employeePage = demoJdbcTemplateRepository.fetchEmployeesWithSimpleJdbc(pageNumber - 1, 5, employeeName, department != null ? department.name() : null);

        List<EmployeeDTO> employeeDTOS = employeePage.getContent().stream()
                .map(employeeDTOEntityDTOMapper.toDTO())
                .toList();

        return EmployeeListResponse.builder()
                .employeeDTOS(employeeDTOS)
                .currentPageNumber(employeePage.getPage() + 1)
                .totalPage(employeePage.getTotalPages())
                .build();


    }
}
