package com.gyan.darpan.employee.controller;

import com.gyan.darpan.dao.entity.DepartmentCount;
import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.entity.Student;
import com.gyan.darpan.dao.repository.StudentRepository;
import com.gyan.darpan.dao.repository.jdbc.DemoEntityManagerRepository;
import com.gyan.darpan.dao.repository.jdbc.DemoJdbcTemplateRepository;
import com.gyan.darpan.dao.repository.jdbc.DemoNamedParameterJdbcRepository;
import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.exception.EmployeeNotFoundException;
import com.gyan.darpan.employee.mapper.EntityDTOMapper;
import com.gyan.darpan.employee.model.EmployeeDTO;
import com.gyan.darpan.employee.response.EmployeeListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class DemoJdbcTemplateController {

    private final DemoJdbcTemplateRepository demoJdbcTemplateRepository;
    private final EntityDTOMapper<Employee, EmployeeDTO> employeeDTOEntityDTOMapper;
    private final DemoNamedParameterJdbcRepository demoNamedParameterJdbcRepository;
    private final DemoEntityManagerRepository demoEntityManagerRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public DemoJdbcTemplateController(DemoJdbcTemplateRepository demoJdbcTemplateRepository,
                                      @Qualifier("employeeMapper") EntityDTOMapper<Employee, EmployeeDTO> employeeDTOEntityDTOMapper,
                                      DemoNamedParameterJdbcRepository demoNamedParameterJdbcRepository,
                                      DemoEntityManagerRepository demoEntityManagerRepository,
                                      StudentRepository studentRepository) {
        this.demoJdbcTemplateRepository = demoJdbcTemplateRepository;
        this.employeeDTOEntityDTOMapper = employeeDTOEntityDTOMapper;
        this.demoNamedParameterJdbcRepository = demoNamedParameterJdbcRepository;
        this.demoEntityManagerRepository = demoEntityManagerRepository;
        this.studentRepository = studentRepository;
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
        List<Employee> employees = demoEntityManagerRepository.findAll6();

        List<EmployeeDTO> employeeDTOS = employees.stream()
                .map(employeeDTOEntityDTOMapper.toDTO())
                .toList();

        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "findByEmployeeName")
    public ResponseEntity<List<EmployeeDTO>> findByEmployeeName(@RequestParam("employeeName") String employeeName) {
        List<Employee> employees = demoEntityManagerRepository.findByEmployeeName1(employeeName);

        List<EmployeeDTO> employeeDTOS = employees.stream()
                .map(employeeDTOEntityDTOMapper.toDTO())
                .toList();

        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "find/all1")
    public ResponseEntity<List<EmployeeDTO>> findAll1() {
        List<Employee> employees = demoEntityManagerRepository.findAll4();

        List<EmployeeDTO> employeeDTOS = employees.stream()
                .map(
                        employee -> EmployeeDTO.builder()
                                .employeeId(employee.getEmployeeId())
                                .employeeName(employee.getEmployeeName())
                                .build()

                )
                .toList();

        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "findByEmployeeNameAndDepartment")
    public ResponseEntity<List<EmployeeDTO>> findByEmployeeNameAndDepartment(@RequestParam("employeeName") String employeeName,
                                                                             @RequestParam("department") String department) {
        List<Employee> employees = demoEntityManagerRepository.findByEmployeeNameAndDepartment(employeeName, department);

        List<EmployeeDTO> employeeDTOS = employees.stream()
                .map(employeeDTOEntityDTOMapper.toDTO())
                .toList();

        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "find")
    public ResponseEntity<List<EmployeeDTO>> find(@RequestParam("employeeName") String employeeName,
                                                  @RequestParam("department") String department,
                                                  @RequestParam("age") int age,
                                                  @RequestParam("employeeNameContain") String employeeNameContain) {
        List<Employee> employees = demoEntityManagerRepository.find(employeeName, department, age, employeeNameContain);

        List<EmployeeDTO> employeeDTOS = employees.stream()
                .map(employeeDTOEntityDTOMapper.toDTO())
                .toList();

        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "findDepartmentWithMinEmployees")
    public ResponseEntity<List<DepartmentCount>> findDepartmentWithMinEmployees(@RequestParam("minCount") long minCount) {
        List<DepartmentCount> departmentCounts = demoEntityManagerRepository.findDepartmentWithMinEmployees(minCount);

        return new ResponseEntity<>(departmentCounts, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("updateEmployeeDemo")
    public EmployeeDTO updateEmployeeDemo(@RequestBody EmployeeDTO employeeDTO) {
        Optional<Employee> employeeOptional = demoEntityManagerRepository.findByEmployeeIdForUpdate(employeeDTO.getEmployeeId());

        if (employeeOptional.isEmpty()) {
            throw new EmployeeNotFoundException(employeeDTO.getEmployeeId());
        }

        Employee employee = employeeOptional.get();

        // Employee employeeEntity = employeeOptional.get();

        // Employee employee = new Employee();
        //employee.setEmployeeId(employeeEntity.getEmployeeId());
        //employee.setJoiningDate(employeeEntity.getJoiningDate());

        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setAge(employeeDTO.getAge());
        employee.setDepartment(employeeDTO.getDepartment().name());

        employee = demoEntityManagerRepository.updateEmployee(employee);

        return employeeDTOEntityDTOMapper.toDTO().apply(employee);
    }

    @Transactional
    @PutMapping("mergeMethodDemo")
    public EmployeeDTO mergeMethodDemo(@RequestBody EmployeeDTO employeeDTO) {

        Employee employee = employeeDTOEntityDTOMapper.toEntity().apply(employeeDTO);

        if (employeeDTO.getEmployeeId() == 0) {
            employee.setEmployeeId(null);
        }

        employee = demoEntityManagerRepository.updateEmployee1(employee);

        return employeeDTOEntityDTOMapper.toDTO().apply(employee);
    }

    @GetMapping("/refresh/demo/{employeeId}")
    public Employee getEmployee(@PathVariable("employeeId") long employeeId) {
        Employee employee = demoEntityManagerRepository.findByEmployeeId(employeeId);

        log.info("First call : {}", employee);

        try {
            Thread.sleep(30000);
        } catch (Exception e) {

        }

        demoEntityManagerRepository.refresh(employee);

        log.info("Second call : {}", employee);

        return employee;
    }

    @GetMapping("clear/demo")
    public String clearDemo() {
        Employee employee = demoEntityManagerRepository.findByEmployeeId(42);
        log.info("First call employee: {}", employee);

        Student student = studentRepository.findById(1L);

        log.info("First call student: {}", student);

        demoEntityManagerRepository.clear();

        employee = demoEntityManagerRepository.findByEmployeeId(42);
        log.info("Second call employee: {}", employee);

        student = studentRepository.findById(1L);

        log.info("Second call student: {}", student);

        return "clear demo";
    }


}
