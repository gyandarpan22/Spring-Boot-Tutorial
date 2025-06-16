package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
        //Approach1 start
    /*
        String query = "Select emp from Employee emp where emp.employeeId=:employeeId";
        Employee employee = null;
        try {
            employee = entityManager.createQuery(query, Employee.class)
                    .setParameter("employeeId", employeeId)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            log.error("findByEmployeeIdForUpdate::no record found ::{}", noResultException.getMessage());
        }
    */

        //Approach1 end

        //Approach2 start
/*
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("employeeId"), employeeId));

        Employee employee = null;
        try {
            employee = entityManager.createQuery(criteriaQuery)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            log.error("findByEmployeeIdForUpdate::no record found ::{}", noResultException.getMessage());
        }
*/

        //Approach2 end

        //Approach3 start
        Employee employee = entityManager.find(Employee.class, employeeId, LockModeType.PESSIMISTIC_WRITE);
        //Approach3 end

        return Optional.ofNullable(employee);
    }

    @Override
    public PageResponse<Employee> fetchEmployees(int pageNumber, int pageSize, String employeeName, String department) {
//        List<String> conditions = new ArrayList<>();
//        Map<String, Object> parameterSource = new HashMap<>();
//
//        if (employeeName != null && !employeeName.isBlank()) {
//            conditions.add("emp.employeeName= :employeeName ");
//            parameterSource.put("employeeName", employeeName);
//        }
//
//        if (department != null && !department.isBlank()) {
//            conditions.add("emp.department=:department ");
//            parameterSource.put("department", department);
//        }
//
//        String query = "Select emp from Employee emp ";
//        String countQuery = "Select count(emp) from Employee emp";
//
//        if (!conditions.isEmpty()) {
//            String condition = " WHERE " + String.join(" AND ", conditions);
//            query = query + condition;
//            countQuery = countQuery + condition;
//        }
//
//        TypedQuery<Long> typeCountQuery = entityManager.createQuery(countQuery, Long.class);
//
//        parameterSource.forEach(typeCountQuery::setParameter);
//
//        long totalEmployees = typeCountQuery.getSingleResult();
//
//        int offset = pageNumber * pageSize;
//
//        TypedQuery<Employee> typedQuery = entityManager.createQuery(query, Employee.class);
//
//        parameterSource.forEach(typedQuery::setParameter);
//
//        typedQuery.setFirstResult(offset);
//        typedQuery.setMaxResults(pageSize);
//
//
//        List<Employee> employees = typedQuery.getResultList();
//
//        return new PageResponse<>(employees, pageNumber, pageSize, totalEmployees);


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);

        List<Predicate> predicates = getPredicates(employeeName, department, criteriaBuilder, root);

        criteriaQuery.select(root);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        int offset = pageNumber * pageSize;

        List<Employee> employees = entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();


        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);

        List<Predicate> countPredicate = getPredicates(employeeName, department, criteriaBuilder, countRoot);

        countQuery.select(criteriaBuilder.count(countRoot));
        countQuery.where(countPredicate.toArray(new Predicate[0]));

        long totalEmployees = entityManager.createQuery(countQuery)
                .getSingleResult();

        return new PageResponse<>(employees, pageNumber, pageSize, totalEmployees);
    }

    private List<Predicate> getPredicates(String employeeName, String department, CriteriaBuilder criteriaBuilder, Root<Employee> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (employeeName != null && !employeeName.isBlank()) {
            predicates.add(criteriaBuilder.equal(root.get("employeeName"), employeeName));
        }

        if (department != null && !department.isBlank()) {
            predicates.add(criteriaBuilder.equal(root.get("department"), department));
        }
        return predicates;
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
