package com.gyan.darpan.employee.controller;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.jdbc.DemoEntityManagerRepository;
import com.gyan.darpan.dao.repository.jdbc.DemoJdbcTemplateRepository;
import com.gyan.darpan.dao.repository.jdbc.DemoNamedParameterJdbcRepository;
import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.mapper.EntityDTOMapper;
import com.gyan.darpan.employee.model.EmployeeDTO;
import com.gyan.darpan.employee.response.EmployeeListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class DemoJdbcTemplateController {

    private final DemoJdbcTemplateRepository demoJdbcTemplateRepository;
    private final EntityDTOMapper<Employee, EmployeeDTO> employeeDTOEntityDTOMapper;
    private final DemoNamedParameterJdbcRepository demoNamedParameterJdbcRepository;
    private final DemoEntityManagerRepository demoEntityManagerRepository;

    @Autowired
    public DemoJdbcTemplateController(DemoJdbcTemplateRepository demoJdbcTemplateRepository,
                                      @Qualifier("employeeMapper") EntityDTOMapper<Employee, EmployeeDTO> employeeDTOEntityDTOMapper,
                                      DemoNamedParameterJdbcRepository demoNamedParameterJdbcRepository,
                                      DemoEntityManagerRepository demoEntityManagerRepository) {
        this.demoJdbcTemplateRepository = demoJdbcTemplateRepository;
        this.employeeDTOEntityDTOMapper = employeeDTOEntityDTOMapper;
        this.demoNamedParameterJdbcRepository = demoNamedParameterJdbcRepository;
        this.demoEntityManagerRepository = demoEntityManagerRepository;
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

    @GetMapping(path = "demo/findByEmployeeId/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable("employeeId") long employeeId) {
        Employee employee = demoEntityManagerRepository.findByEmployeeIdUsingJpql(employeeId);

        log.info("fetch completed");

        EmployeeDTO employeeDTO = employeeDTOEntityDTOMapper.toDTO().apply(employee);

        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @GetMapping(path = "find/all")
    public ResponseEntity<List<EmployeeDTO>> findAll() {
        List<Employee> employees = demoEntityManagerRepository.findAllSpecificColumnEmployeeIdAndEmployeeName();

        List<EmployeeDTO> employeeDTOS = employees.stream()
                .map(employeeDTOEntityDTOMapper.toDTO())
                .toList();

        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "findByEmployeeName")
    public ResponseEntity<List<EmployeeDTO>> findByEmployeeName(@RequestParam("employeeName") String employeeName) {
        List<Employee> employees = demoEntityManagerRepository.findByEmployeeNameUsingNamedQuery(employeeName);

        List<EmployeeDTO> employeeDTOS = employees.stream()
                .map(employeeDTOEntityDTOMapper.toDTO())
                .toList();

        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }
}
