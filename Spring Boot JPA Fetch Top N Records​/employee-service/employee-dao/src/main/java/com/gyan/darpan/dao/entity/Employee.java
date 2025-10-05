package com.gyan.darpan.dao.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
@NamedQuery(name = "Employee.findByEmployeeName",
        query = "Select emp from Employee emp where emp.employeeName= :employeeName")
public class Employee {
    @Column(name = "employee_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    @Column(name = "employee_name", nullable = false)
    private String employeeName;
    @Column(name = "age")
    private int age;
    @Column(name = "joining_date", nullable = false, updatable = false)
    private Date joiningDate;
    @Column(name = "department", nullable = false)
    private String department;
    @Column(name = "salary")
    private double salary;
    @Column(name = "created_date", nullable = false, insertable = false, updatable = false,
            columnDefinition = "timestamp not null default current_timestamp")
    private Timestamp createdDate;
    @Column(name = "modified_date", nullable = false, insertable = false, updatable = false,
            columnDefinition = "timestamp not null default current_timestamp on update current_timestamp")
    private Timestamp modifiedDate;

    public Employee(Long employeeId, String employeeName) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }
}
