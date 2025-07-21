package com.gyan.darpan.dao.repository.jpa;

import com.gyan.darpan.dao.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
}
