package com.gyan.darpan.service;

import com.gyan.darpan.dto.BankDetailDto;
import com.gyan.darpan.dto.DepartmentDto;
import com.gyan.darpan.dto.EmployeeDto;
import com.gyan.darpan.entity.BankDetail;
import com.gyan.darpan.entity.Department;
import com.gyan.darpan.entity.Employee;
import com.gyan.darpan.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        Department department = Department.builder()
                .name(departmentDto.getDepartmentName())
                .build();

        if (departmentDto.getEmployees() != null && !departmentDto.getEmployees().isEmpty()) {
            List<Employee> employees = new ArrayList<>();
            for (EmployeeDto employeeDto : departmentDto.getEmployees()) {
                BankDetailDto bankDetailDto = employeeDto.getBankDetailDto();

                BankDetail bankDetail = BankDetail.builder()
                        .bankName(bankDetailDto.getBankName())
                        .ifscCode(bankDetailDto.getIfscCode())
                        .accountNumber(bankDetailDto.getAccountNumber())
                        .build();

                Employee employee = Employee.builder()
                        .name(employeeDto.getEmployeeName())
                        .age(employeeDto.getAge())
                        .joiningDate(employeeDto.getJoiningDate())
                        .department(department)
                        .salary(employeeDto.getAge())
                        .bankDetail(bankDetail)
                        .build();

                bankDetail.setEmployee(employee);

                employees.add(employee);
            }

            department.setEmployees(employees);
        }

        departmentRepository.save(department);

        return departmentDto(department);
    }

    public DepartmentDto fetchDepartmentDetail(Long departmentId) {

        log.info("inside fetchDepartmentDetail : before calling departmentRepository.findById()");

        Department department = departmentRepository.findById(departmentId).orElseThrow();

        log.info("inside fetchDepartmentDetail : after calling departmentRepository.findById()");

        log.info("inside fetchDepartmentDetail : employee size : {}", department.getEmployees().size());

        return departmentDto(department);
    }


    private DepartmentDto departmentDto(Department department) {
        DepartmentDto departmentDto = DepartmentDto.builder()
                .departmentId(department.getId())
                .departmentName(department.getName())
                .build();

        if (department.getEmployees() != null && !department.getEmployees().isEmpty()) {
            List<EmployeeDto> employees = new ArrayList<>();
            for (Employee employee : department.getEmployees()) {

                BankDetail bankDetail = employee.getBankDetail();

                BankDetailDto bankDetailDto = BankDetailDto.builder()
                        .bankName(bankDetail.getBankName())
                        .ifscCode(bankDetail.getIfscCode())
                        .accountNumber(bankDetail.getAccountNumber())
                        .build();

                EmployeeDto employeeDto = EmployeeDto.builder()
                        .employeeId(employee.getId())
                        .employeeName(employee.getName())
                        .age(employee.getAge())
                        .joiningDate(employee.getJoiningDate())
                        .departmentId(employee.getDepartment().getId())
                        .salary(employee.getAge())
                        .bankDetailDto(bankDetailDto)
                        .build();

                employees.add(employeeDto);
            }
            departmentDto.setEmployees(employees);
        }
        return departmentDto;
    }
}
