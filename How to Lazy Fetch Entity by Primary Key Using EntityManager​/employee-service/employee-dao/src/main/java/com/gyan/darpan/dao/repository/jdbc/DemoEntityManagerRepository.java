package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class DemoEntityManagerRepository {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Employee findByEmployeeId(long employeeId) {
        log.info("DemoEntityManagerRepository::findByEmployeeId started");
        Employee employee = entityManager.find(Employee.class, employeeId);
        log.info("DemoEntityManagerRepository::findByEmployeeId completed");
        return employee;
    }

    public Employee findByEmployeeIdLazy(long employeeId) {
        log.info("DemoEntityManagerRepository::findByEmployeeIdLazy started");

        Employee employee = entityManager.getReference(Employee.class, employeeId);

        log.info("DemoEntityManagerRepository::findByEmployeeIdLazy completed");
        return employee;
    }
}
