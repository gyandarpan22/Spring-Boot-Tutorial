package com.gyan.darpan.dao.repository.jpa;

import com.gyan.darpan.dao.entity.Employee;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    //SELECT * FROM employee WHERE salary > 200000 AND (department = 'DEVELOPER' OR age > 30);
    @Query(value = "SELECT * FROM employee emp WHERE emp.salary > :salary AND (emp.department= :dept OR age > :age )", nativeQuery = true)
    List<Employee> findHighSalaryEmployeeByDepartmentOrAgeSQL(double salary, @Param("dept") String department, int age);

    //SELECT * FROM employee WHERE salary > 200000 AND (department = 'DEVELOPER' OR age > 30);
    @Query(value = "SELECT * FROM employee emp WHERE emp.salary > ? AND (emp.department= ? OR age > ? )", nativeQuery = true)
    List<Employee> findHighSalaryEmployeeByDepartmentOrAgeSQL1(double salary, String department, int age);

    @Query(value = "SELECT emp FROM Employee emp WHERE emp.salary > :salary AND (emp.department= :dept OR age > :age )")
    Page<Employee> findHighSalaryEmployeeByDepartmentOrAge(double salary, @Param("dept") String department, int age, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "SELECT emp from Employee emp where emp.employeeId = :employeeId")
    Optional<Employee> findByEmployeeIdForUpdate(long employeeId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Employee emp SET emp.salary = :salary where emp.employeeId = :employeeId")
    int updateEmployeeSalary(double salary,long employeeId);

}
