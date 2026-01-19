package com.gyan.darpan.entity.master;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tenant_db_config_detail")
public class TenantConfigDbDetail {
    @Id
    @Column(name = "tenant_id")
    private String tenantId;
    @Column(name = "jdbc_url")
    private String jdcUrl;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "driver_class_name")
    private String driverClassName;
}
