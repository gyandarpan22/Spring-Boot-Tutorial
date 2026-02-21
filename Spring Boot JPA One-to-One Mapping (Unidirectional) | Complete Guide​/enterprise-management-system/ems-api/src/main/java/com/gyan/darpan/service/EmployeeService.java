package com.gyan.darpan.service;

import com.gyan.darpan.dto.BankDetailDto;
import com.gyan.darpan.dto.EmployeeDto;
import com.gyan.darpan.entity.BankDetail;
import com.gyan.darpan.entity.Employee;
import com.gyan.darpan.repository.BankDetailRepository;
import com.gyan.darpan.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BankDetailRepository bankDetailRepository;

    public EmployeeService(EmployeeRepository employeeRepository, BankDetailRepository bankDetailRepository) {
        this.employeeRepository = employeeRepository;
        this.bankDetailRepository = bankDetailRepository;
    }

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Employee employee = Employee.builder()
                .name(employeeDto.getEmployeeName())
                .age(employeeDto.getAge())
                .joiningDate(employeeDto.getJoiningDate())
                .department(employeeDto.getDepartment())
                .salary(employeeDto.getAge())
                .build();

        BankDetailDto bankDetailDto = employeeDto.getBankDetailDto();

        BankDetail bankDetail = BankDetail.builder()
                .bankName(bankDetailDto.getBankName())
                .ifscCode(bankDetailDto.getIfscCode())
                .accountNumber(bankDetailDto.getAccountNumber())
                .employee(employee)
                .build();

        employeeRepository.save(employee);
        bankDetailRepository.save(bankDetail);

        employeeDto.setEmployeeId(employee.getId());

        return employeeDto;

    }

    public EmployeeDto getEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();

        BankDetail bankDetail = bankDetailRepository.findByEmployeeId(employeeId).get();

        log.info("Employee detail from bankDetail entity : {} ", bankDetail.getEmployee());

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
                .department(employee.getDepartment())
                .salary(employee.getAge())
                .bankDetailDto(bankDetailDto)
                .build();

        return employeeDto;
    }
}
