package com.gyan.darpan.employee.model;

import com.gyan.darpan.employee.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private String userName;
    private int age;
    private boolean active;
    private List<Roles> roles;
}
