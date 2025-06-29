package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.Employee;
import com.gyan.darpan.dao.entity.PageResponse;
import com.gyan.darpan.dao.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            log.info("EmployeeEntityManagerRepository.saveEmployee::calling entityManager.persist");

            entityManager.persist(employee);

            log.info("EmployeeEntityManagerRepository.saveEmployee::completed entityManager.persist");

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

        Employee employee = entityManager.find(Employee.class, employeeId, LockModeType.PESSIMISTIC_WRITE);

        return Optional.ofNullable(employee);
    }

    @Override
    public PageResponse<Employee> fetchEmployees(int pageNumber, int pageSize, String employeeName, String department) {

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

        log.info("EmployeeEntityManagerRepository.updateEmployee::calling entityManager.merge");

        Employee updatedEmployee = entityManager.merge(employee);

        log.info("EmployeeEntityManagerRepository.updateEmployee::completed entityManager.merge");

        return updatedEmployee;
    }

    @Override
    @Transactional
    public int deleteEmployee(long employeeId) {
        try {

            //Approach1 start

           /* String query = "Delete from Employee emp where emp.employeeId=:employeeId";

            return entityManager.createQuery(query)
                    .setParameter("employeeId", employeeId)
                    .executeUpdate();

            */

            //Approach1 end


            //Approach2 start
           /*
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaDelete<Employee> criteriaDelete = criteriaBuilder.createCriteriaDelete(Employee.class);

            Root<Employee> root = criteriaDelete.getRoot();

            Predicate predicate = criteriaBuilder.equal(root.get("employeeId"), employeeId);

            criteriaDelete.where(predicate);

            return entityManager.createQuery(criteriaDelete)
                    .executeUpdate();

            */

            //Approach2 end

            //Approach3 start

            Optional<Employee> employeeOptional = findByEmployeeId(employeeId);

            if (employeeOptional.isPresent()) {
                entityManager.remove(employeeOptional.get());
                return 1;
            }

            //Approach3 end

        } catch (Exception exception) {
            log.error("deleteEmployee::Exception occur : ", exception);
            throw exception;
        }
        return 0;
    }
}
