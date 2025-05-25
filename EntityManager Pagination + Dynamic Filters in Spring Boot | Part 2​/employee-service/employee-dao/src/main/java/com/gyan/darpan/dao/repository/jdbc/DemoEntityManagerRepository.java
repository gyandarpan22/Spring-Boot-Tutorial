package com.gyan.darpan.dao.repository.jdbc;

import com.gyan.darpan.dao.entity.DepartmentCount;
import com.gyan.darpan.dao.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
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


    public List<Employee> findAll3() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);

        criteriaQuery.select(root);

        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    public List<Employee> findByEmployeeName1(String employeeName) {
        log.info("findByEmployeeName1 called");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);

        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get("employeeName"), employeeName));

        return entityManager.createQuery(criteriaQuery)
                .getResultList();

    }

    public List<Employee> findByEmployeeNameAndDepartment(String employeeName, String department) {
        log.info("findByEmployeeNameAndDepartment method called");


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);

        criteriaQuery.select(root);


        Predicate employeeNamePredicate = criteriaBuilder.equal(root.get("employeeName"), employeeName);
        Predicate departmentPredicate = criteriaBuilder.equal(root.get("department"), department);

        Predicate employeeNameAndDepartment = criteriaBuilder.and(employeeNamePredicate, departmentPredicate);  //employee_name=? and department=?

        criteriaQuery.where(employeeNameAndDepartment);

        return entityManager.createQuery(criteriaQuery)
                .getResultList();

    }

    /**
     * select *from employee where (employee_name='Pankaj Kumar' AND department ='DEVELOPER' )
     * OR (age>30 AND (employee_name like '%R%' OR department='DEVELOPER' ) )
     */

    public List<Employee> find(String employeeName, String department, int age, String employeeNameContain) {
        log.info("find method called");


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);

        criteriaQuery.select(root);

        Predicate employeeNamePredicate = criteriaBuilder.equal(root.get("employeeName"), employeeName);
        Predicate departmentPredicate = criteriaBuilder.equal(root.get("department"), department);
        Predicate firstGroup = criteriaBuilder.and(employeeNamePredicate, departmentPredicate);


        Predicate ageGreater = criteriaBuilder.greaterThan(root.get("age"), age);

        Predicate nameContain = criteriaBuilder.like(root.get("employeeName"), "%" + employeeNameContain + "%");
        Predicate departmentPredicate1 = criteriaBuilder.equal(root.get("department"), department);
        Predicate nameContainORDepartment = criteriaBuilder.or(nameContain, departmentPredicate1);

        Predicate secondGroup = criteriaBuilder.and(ageGreater, nameContainORDepartment);

        Predicate finalPredicate = criteriaBuilder.or(firstGroup, secondGroup);

        criteriaQuery.where(finalPredicate);

        return entityManager.createQuery(criteriaQuery)
                .getResultList();

    }

    public List<Employee> findAll4() {
        log.info("findAll4 method called");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);

        criteriaQuery.multiselect(root.get("employeeId"), root.get("employeeName"));


        return entityManager.createQuery(criteriaQuery)
                .getResultList();

    }

    public List<Employee> findAll5() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);

        criteriaQuery.select(root);

        Order employeeNameOrderBy = criteriaBuilder.asc(root.get("employeeName"));

        criteriaQuery.orderBy(employeeNameOrderBy);


        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }


    public List<Employee> findAll6() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);

        criteriaQuery.select(root);

        Order employeeNameOrderBy = criteriaBuilder.asc(root.get("employeeName"));
        Order ageOrderBy = criteriaBuilder.desc(root.get("age"));

        criteriaQuery.orderBy(employeeNameOrderBy, ageOrderBy);


        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    /**
     * SELECT department, COUNT(*) FROM employee
     * GROUP BY department
     * HAVING COUNT(*) > 5;
     **/

    public List<DepartmentCount> findDepartmentWithMinEmployees(long minCount) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<DepartmentCount> criteriaQuery = criteriaBuilder.createQuery(DepartmentCount.class);

        Root<Employee> root = criteriaQuery.from(Employee.class);

        Expression<Long> countExpression = criteriaBuilder.count(root);

//        CompoundSelection<DepartmentCount> countCompoundSelection =
//                criteriaBuilder.construct(DepartmentCount.class, root.get("department"), countExpression);

        //criteriaQuery.select(countCompoundSelection);

        criteriaQuery.multiselect(root.get("department"), countExpression);

        criteriaQuery.groupBy(root.get("department"));

        Predicate predicate = criteriaBuilder.greaterThan(countExpression, minCount);

        criteriaQuery.having(predicate);

        return entityManager.createQuery(criteriaQuery)
                .getResultList();

    }
}
