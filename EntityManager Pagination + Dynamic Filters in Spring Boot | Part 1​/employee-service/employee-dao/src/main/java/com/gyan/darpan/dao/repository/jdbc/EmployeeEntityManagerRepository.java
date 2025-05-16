package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository("employeeEntityManagerRepository")
@Slf4j
public class EmployeeEntityManagerRepository implements EmployeeRepository {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Employee saveEmployee(Employee employee) {
        try {
            log.info("EmployeeEntityManagerRepository::saveEmployee");
            log.info("Entity Manager : {} ", entityManager.toString());
            entityManager.persist(employee);
        } catch (Exception e) {
            log.error("Exception Occur :", e);
            throw e;
        }
        return employee;
    }

    @Override
    public Optional<Employee> findByEmployeeId(long employeeId) {
        log.info("EmployeeEntityManagerRepository::findByEmployeeId");
        Employee employee = entityManager.find(Employee.class, employeeId);

        return Optional.ofNullable(employee);
    }

    @Override
    public Optional<Employee> findByEmployeeIdForUpdate(long employeeId) {
        return Optional.empty();
    }

    @Override
    public PageResponse<Employee> fetchEmployees(int pageNumber, int pageSize, String employeeName, String department) {
        List<String> conditions = new ArrayList<>();
        Map<String, Object> parameterSource = new HashMap<>();

        if (employeeName != null && !employeeName.isBlank()) {
            conditions.add("emp.employeeName= :employeeName ");
            parameterSource.put("employeeName", employeeName);
        }

        if (department != null && !department.isBlank()) {
            conditions.add("emp.department=:department ");
            parameterSource.put("department", department);
        }

        String query = "Select emp from Employee emp ";
        String countQuery = "Select count(emp) from Employee emp";

        if (!conditions.isEmpty()) {
            String condition = " WHERE " + String.join(" AND ", conditions);
            query = query + condition;
            countQuery = countQuery + condition;
        }

        TypedQuery<Long> typeCountQuery = entityManager.createQuery(countQuery, Long.class);

        parameterSource.forEach(typeCountQuery::setParameter);

        long totalEmployees = typeCountQuery.getSingleResult();

        int offset = pageNumber * pageSize;

        TypedQuery<Employee> typedQuery = entityManager.createQuery(query, Employee.class);

        parameterSource.forEach(typedQuery::setParameter);

        typedQuery.setFirstResult(offset);
        typedQuery.setMaxResults(pageSize);


        List<Employee> employees = typedQuery.getResultList();

        return new PageResponse<>(employees, pageNumber, pageSize, totalEmployees);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public int deleteEmployee(long employeeId) {
        return 0;
    }
}
