package com.gyan.darpan.dao.repository.jpa;

import com.gyan.darpan.dao.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    //select *from employee where employee_name='Rahul Kumar'
    List<Employee> findByEmployeeName(String employeeName);

    //select *From employee where age<40
    List<Employee> findByAgeLessThan(int age);

    //select *from employee where employee_name='Rahul Kumar' and age<=29
    List<Employee> findByEmployeeNameAndAgeLessThanEqual(String employeeName, int age);
    

}
