package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public Employee findByEmployeeIdUsingJpql(long employeeId) {
        String query = "Select emp from Employee emp where emp.employeeId= :employeeId";

        return entityManager.createQuery(query, Employee.class)
                .setParameter("employeeId", employeeId)
                .getSingleResult();
    }


    public List<Employee> findAll() {
        String query = "Select emp from Employee emp";

        return entityManager.createQuery(query, Employee.class)
                .getResultList();

    }

    public List<Employee> findAll1() {
        String query = "Select new Employee(emp.employeeId,emp.employeeName) from Employee emp";

        return entityManager.createQuery(query, Employee.class)
                .getResultList();

    }

    public List<Employee> findAll2() {
        String query = "Select emp.employeeId,emp.employeeName from Employee emp";

        return entityManager.createQuery(query, Employee.class)
                .getResultList();

    }

    public List<Employee> findByEmployeeName(String employeeName) {
        String query = "Select emp from Employee emp where emp.employeeName= :employeeName";

        return entityManager.createQuery(query, Employee.class)
                .setParameter("employeeName", employeeName)
                .getResultList();

    }

    public List<Employee> findByEmployeeNameUsingNamedQuery(String employeeName) {
        return entityManager.createNamedQuery("Employee.findByEmployeeName", Employee.class)
                .setParameter("employeeName", employeeName)
                .getResultList();
    }
}
