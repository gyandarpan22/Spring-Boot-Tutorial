package com.gyan.darpan.dao.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "created_at", nullable = false, insertable = false, updatable = false,
            columnDefinition = "timestamp not null default current_timestamp")
    private Timestamp createdAt;
    @Column(name = "updated_at", nullable = false, insertable = false, updatable = false,
            columnDefinition = "timestamp not null default current_timestamp on update current_timestamp")
    private Timestamp updatedAt;
}
