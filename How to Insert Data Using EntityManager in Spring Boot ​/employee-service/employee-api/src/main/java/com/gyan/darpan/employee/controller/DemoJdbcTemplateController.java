package com.gyan.darpan.employee.controller;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.jdbc.DemoJdbcTemplateRepository;
import com.gyan.darpan.dao.repository.jdbc.DemoNamedParameterJdbcRepository;
import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.mapper.EntityDTOMapper;
import com.gyan.darpan.employee.model.EmployeeDTO;
import com.gyan.darpan.employee.response.EmployeeListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DemoJdbcTemplateController {

    private final DemoJdbcTemplateRepository demoJdbcTemplateRepository;
    private final EntityDTOMapper<Employee, EmployeeDTO> employeeDTOEntityDTOMapper;
    private final DemoNamedParameterJdbcRepository demoNamedParameterJdbcRepository;

    @Autowired
    public DemoJdbcTemplateController(DemoJdbcTemplateRepository demoJdbcTemplateRepository,
                                      @Qualifier("employeeMapper") EntityDTOMapper<Employee, EmployeeDTO> employeeDTOEntityDTOMapper,
                                      DemoNamedParameterJdbcRepository demoNamedParameterJdbcRepository) {
        this.demoJdbcTemplateRepository = demoJdbcTemplateRepository;
        this.employeeDTOEntityDTOMapper = employeeDTOEntityDTOMapper;
        this.demoNamedParameterJdbcRepository = demoNamedParameterJdbcRepository;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            path = "batch/demo")
    public ResponseEntity<Void> addEmployee(@RequestBody List<EmployeeDTO> employeeDTO) {
        List<Employee> employees = employeeDTO.stream()
                .map(employeeDTOEntityDTOMapper.toEntity())
                .toList();

        demoNamedParameterJdbcRepository.insertEmployees1(employees);

        return ResponseEntity.noContent().build();

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE,
            path = "batch/demo1")
    public ResponseEntity<List<EmployeeDTO>> addEmployee1(@RequestBody List<EmployeeDTO> employeeDTO) {
        List<Employee> employees = employeeDTO.stream()
                .map(employeeDTOEntityDTOMapper.toEntity())
                .toList();

        employees = demoNamedParameterJdbcRepository.insertEmployees2(employees);

        List<EmployeeDTO> employeeDTOS = employees.stream()
                .map(employeeDTOEntityDTOMapper.toDTO())
                .toList();

        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);

    }
}
