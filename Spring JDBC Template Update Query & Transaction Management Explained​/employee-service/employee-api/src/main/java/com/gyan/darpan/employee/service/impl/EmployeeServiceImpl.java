package com.gyan.darpan.employee.service.impl;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import com.gyan.darpan.employee.enums.Department;
import com.gyan.darpan.employee.exception.EmployeeNotFoundException;
import com.gyan.darpan.employee.mapper.EntityDTOMapper;
import com.gyan.darpan.employee.model.EmployeeDTO;
import com.gyan.darpan.employee.response.EmployeeListResponse;
import com.gyan.darpan.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EntityDTOMapper<Employee, EmployeeDTO> employeeDTOEntityDTOMapper;

    private Map<Long, EmployeeDTO> employeeMap;
    private long sequenceId;

    private final int pageSize;


    @Autowired
    public EmployeeServiceImpl(@Qualifier("employeeJdbcRepository") EmployeeRepository employeeRepository,
                               @Qualifier("employeeMapper") EntityDTOMapper<Employee, EmployeeDTO> employeeDTOEntityDTOMapper,
                               @Value("${employee.page.size}") int pageSize) {
        this.employeeRepository = employeeRepository;
        this.employeeDTOEntityDTOMapper = employeeDTOEntityDTOMapper;

        this.pageSize = pageSize;
        this.sequenceId = 1L;

        employeeMap = new LinkedHashMap<>();

        EmployeeDTO employeeDTO1 = EmployeeDTO.builder()
                .employeeId(sequenceId++)
                .employeeName("Ram")
                .age(22)
                .joiningDate(LocalDate.now())
                .department(Department.DEVELOPER)
                .build();

        EmployeeDTO employeeDTO2 = EmployeeDTO.builder()
                .employeeId(sequenceId++)
                .employeeName("Shyam")
                .age(25)
                .joiningDate(LocalDate.now())
                .department(Department.QA)
                .build();

        EmployeeDTO employeeDTO3 = EmployeeDTO.builder()
                .employeeId(sequenceId++)
                .employeeName("Mohan")
                .age(24)
                .joiningDate(LocalDate.now())
                .department(Department.HR)
                .build();

        EmployeeDTO employeeDTO4 = EmployeeDTO.builder()
                .employeeId(sequenceId++)
                .employeeName("Mohan")
                .age(24)
                .joiningDate(LocalDate.now())
                .department(Department.HR)
                .build();

        EmployeeDTO employeeDTO5 = EmployeeDTO.builder()
                .employeeId(sequenceId++)
                .employeeName("Sohan")
                .age(24)
                .joiningDate(LocalDate.now())
                .department(Department.DEVELOPER)
                .build();


        employeeMap.put(employeeDTO1.getEmployeeId(), employeeDTO1);
        employeeMap.put(employeeDTO2.getEmployeeId(), employeeDTO2);
        employeeMap.put(employeeDTO3.getEmployeeId(), employeeDTO3);
        employeeMap.put(employeeDTO4.getEmployeeId(), employeeDTO4);
        employeeMap.put(employeeDTO5.getEmployeeId(), employeeDTO5);

    }


    @Override
    public EmployeeListResponse getEmployees(String employeeName, Department department, int pageNumber) {

        PageResponse<Employee> employeePage = employeeRepository.fetchEmployees(pageNumber - 1, pageSize, employeeName, department != null ? department.name() : null);

        List<EmployeeDTO> employeeDTOS = employeePage.getContent().stream()
                .map(employeeDTOEntityDTOMapper.toDTO())
                .toList();

        return EmployeeListResponse.builder()
                .employeeDTOS(employeeDTOS)
                .currentPageNumber(employeePage.getPage() + 1)
                .totalPage(employeePage.getTotalPages())
                .build();
    }

    @Override
    public EmployeeDTO getEmployeeById(long employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeId(employeeId);

        if (employeeOptional.isEmpty()) {
            throw new EmployeeNotFoundException(employeeId);
        }

        return employeeDTOEntityDTOMapper.toDTO().apply(employeeOptional.get());

    }

    @Override
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOEntityDTOMapper.toEntity().apply(employeeDTO);

        employee = employeeRepository.saveEmployee(employee);

        return employeeDTOEntityDTOMapper.toDTO().apply(employee);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeIdForUpdate(employeeDTO.getEmployeeId());

        if (employeeOptional.isEmpty()) {
            throw new EmployeeNotFoundException(employeeDTO.getEmployeeId());
        }

        Employee employee = employeeOptional.get();
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setAge(employeeDTO.getAge());
        employee.setDepartment(employeeDTO.getDepartment().name());

        employee = employeeRepository.updateEmployee(employee);

        return employeeDTOEntityDTOMapper.toDTO().apply(employee);
    }

    @Override
    public boolean deleteEmployee(long employeeId) {
        if (!employeeMap.containsKey(employeeId)) {
            return false;
        }

        employeeMap.remove(employeeId);

        return true;
    }
}
