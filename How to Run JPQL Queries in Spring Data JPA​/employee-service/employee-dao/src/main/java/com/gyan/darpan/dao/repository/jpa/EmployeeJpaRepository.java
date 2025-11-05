package com.gyan.darpan.dao.repository.jpa;

import com.gyan.darpan.dao.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    //select *from employee where employee_name='Rahul Kumar'
    List<Employee> findByEmployeeName(String employeeName);

    //select *From employee where age<40
    List<Employee> findByAgeLessThan(int age);

    //select *From employee where age<40 order by employee_name asc
    List<Employee> findByAgeLessThanOrderByEmployeeNameAsc(int age);

    //select *From employee where age<40 order by employee_name asc, age desc
    List<Employee> findByAgeLessThanOrderByEmployeeNameAscAgeDesc(int age);

    List<Employee> findByAgeLessThan(int age, Sort sort);

    //select *from employee where employee_name='Rahul Kumar' and age<=29
    List<Employee> findByEmployeeNameAndAgeLessThanEqual(String employeeName, int age);

    //Employee findByEmployeeId(Long employeeId);

    Optional<Employee> findByEmployeeId(Long employeeId);

    //select *from employee order by salary desc limit 5
    List<Employee> findTop5ByOrderBySalaryDesc();

    //select *from employee where department='DEVELOPER' order by salary desc limit 5
    List<Employee> findTop5ByDepartmentOrderBySalaryDesc(String department);

    //select *from employee where department='DEVELOPER' order by salary desc limit 5
    Page<Employee> findByDepartment(String department, Pageable pageable);

    //SELECT * FROM employee WHERE salary > 200000 AND (department = 'DEVELOPER' OR age > 30);
    @Query(value = "SELECT emp FROM Employee emp WHERE emp.salary > :salary AND (emp.department= :dept OR age > :age )")
    List<Employee> findHighSalaryEmployeeByDepartmentOrAge(double salary, @Param("dept") String department, int age);
}
