package com.gyan.darpan.service;

import com.gyan.darpan.dto.BankDetailDto;
import com.gyan.darpan.dto.EmployeeDto;
import com.gyan.darpan.dto.ProjectDto;
import com.gyan.darpan.entity.BankDetail;
import com.gyan.darpan.entity.Department;
import com.gyan.darpan.entity.Employee;
import com.gyan.darpan.entity.Project;
import com.gyan.darpan.repository.DepartmentRepository;
import com.gyan.darpan.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        Department department = departmentRepository.findById(employeeDto.getDepartmentId()).orElseThrow();

        BankDetailDto bankDetailDto = employeeDto.getBankDetail();

        List<ProjectDto> projectDtos = employeeDto.getProjects();

        BankDetail bankDetail = BankDetail.builder()
                .bankName(bankDetailDto.getBankName())
                .ifscCode(bankDetailDto.getIfscCode())
                .accountNumber(bankDetailDto.getAccountNumber())
                .build();


        List<Project> projects = new ArrayList<>();
        for (ProjectDto projectDto : projectDtos) {
            projects.add(
                    Project.builder()
                            .projectName(projectDto.getProjectName())
                            .build()
            );
        }

        Employee employee = Employee.builder()
                .name(employeeDto.getEmployeeName())
                .age(employeeDto.getAge())
                .joiningDate(employeeDto.getJoiningDate())
                .department(department)
                .salary(employeeDto.getAge())
                .bankDetail(bankDetail)
                .projects(projects)
                .build();

        bankDetail.setEmployee(employee);

        employee = employeeRepository.save(employee);


        return employeeDto(employee);

    }

    public EmployeeDto getEmployee(long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();

        return employeeDto(employee);
    }

    private EmployeeDto employeeDto(Employee employee) {
        BankDetail bankDetail = employee.getBankDetail();

        BankDetailDto bankDetailDto = BankDetailDto.builder()
                .bankName(bankDetail.getBankName())
                .ifscCode(bankDetail.getIfscCode())
                .accountNumber(bankDetail.getAccountNumber())
                .build();

        List<Project> projects = employee.getProjects();
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projects) {
            projectDtos.add(
                    ProjectDto.builder()
                            .projectId(project.getId())
                            .projectName(project.getProjectName())
                            .build()
            );
        }

        return EmployeeDto.builder()
                .employeeId(employee.getId())
                .employeeName(employee.getName())
                .age(employee.getAge())
                .joiningDate(employee.getJoiningDate())
                .departmentId(employee.getDepartment().getId())
                .salary(employee.getAge())
                .bankDetail(bankDetailDto)
                .projects(projectDtos)
                .build();
    }

    public void deleteEmployee(long employeeId) {
        employeeRepository.deleteById(employeeId);
    }
}
