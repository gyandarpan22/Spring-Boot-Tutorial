package com.gyan.darpan.employee.controller;

import com.gyan.darpan.employee.annotation.GlobalExceptionHandlerAnnotation;
import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.model.EmployeeDTO;
import com.gyan.darpan.employee.response.EmployeeListResponse;
import com.gyan.darpan.employee.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
@GlobalExceptionHandlerAnnotation
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeListResponse> getEmployees(
            @RequestParam(name = "employeeName", required = false) String employeeName,
            @RequestParam(name = "department", required = false) Department department,
            @RequestParam(name = "pageNo", defaultValue = "1") int pageNumber
    ) {
        EmployeeListResponse employeeListResponse = employeeService.getEmployees(employeeName, department, pageNumber);

        return new ResponseEntity<>(employeeListResponse, HttpStatus.OK);
    }

    @GetMapping(path = "{employeeId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") long employeeId) {
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(employeeId);

        return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO) {

        EmployeeDTO employeeDTO1 = employeeService.addEmployee(employeeDTO);

        return new ResponseEntity<>(employeeDTO1, HttpStatus.CREATED);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO employeeDTO1 = employeeService.updateEmployee(employeeDTO);

        return new ResponseEntity<>(employeeDTO1, HttpStatus.OK);
    }

    @DeleteMapping(path = "{employeeId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long employeeId) {
        if (employeeService.deleteEmployee(employeeId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
