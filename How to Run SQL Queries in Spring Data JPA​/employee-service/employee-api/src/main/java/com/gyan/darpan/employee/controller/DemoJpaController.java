package com.gyan.darpan.employee.controller;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.repository.jpa.EmployeeJpaRepository;
import com.gyan.darpan.employee.exception.EmployeeNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class DemoJpaController {

    private final EmployeeJpaRepository employeeJpaRepository;

    public DemoJpaController(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @GetMapping("jpa/demo/findByEmployeeName")
    public List<Employee> getEmployeeByEmployeeName(@RequestParam("employeeName") String employeeName) {
        return employeeJpaRepository.findByEmployeeName(employeeName);
    }

    @GetMapping("jpa/demo/findByAge")
    public List<Employee> findByAge(@RequestParam("age") int age) {
        Sort sort = Sort.by(Sort.Order.asc("employeeName"));
        return employeeJpaRepository.findByAgeLessThan(age, sort);
    }

    @GetMapping("jpa/demo/findByNameAndAge")
    public List<Employee> findByNameAndAge(@RequestParam("employeeName") String employeeName,
                                           @RequestParam("age") int age) {
        return employeeJpaRepository.findByEmployeeNameAndAgeLessThanEqual(employeeName, age);
    }

    @GetMapping("jpa/demo/findByEmployeeId")
    public Employee findByEmployeeId(@RequestParam("employeeId") Long employeeId) {
        Optional<Employee> employeeOptional = employeeJpaRepository.findByEmployeeId(employeeId);

        if (employeeOptional.isPresent()) {
            return employeeOptional.get();
        }

        throw new EmployeeNotFoundException(employeeId);
    }

    @GetMapping("jpa/demo/topSalaryEmployee")
    public List<Employee> topSalaryEmployee() {
        return employeeJpaRepository.findTop5ByOrderBySalaryDesc();
    }

    @GetMapping("jpa/demo/topSalaryEmployeeByDepartment")
    public List<Employee> topSalaryEmployeeByDepartment(@RequestParam("department") String department) {
        return employeeJpaRepository.findTop5ByDepartmentOrderBySalaryDesc(department);
    }


    @GetMapping("jpa/demo/getEmployeesByDepartmentUsingPagination")
    public List<Employee> getEmployeesByDepartmentUsingPagination(@RequestParam("department") String department,
                                                                  @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                                                  @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        Sort sort = Sort.by(Sort.Order.desc("salary"));

        Pageable pageable = PageRequest.of(pageNumber, pageSize - 1, sort);

        Page<Employee> employeePage = employeeJpaRepository.findByDepartment(department, pageable);

        return employeePage.getContent();
    }

    @GetMapping("jpa/demo/findHighSalaryEmployeeByDepartmentOrAge")
    public List<Employee> findHighSalaryEmployeeByDepartmentOrAge(@RequestParam("salary") double salary,
                                                                  @RequestParam("department") String department,
                                                                  @RequestParam("age") int age) {
        return employeeJpaRepository.findHighSalaryEmployeeByDepartmentOrAgeSQL1(salary, department, age);
    }

}
