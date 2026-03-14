package com.gyan.darpan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    private Date joiningDate;
    private double salary;
    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    private BankDetail bankDetail;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
