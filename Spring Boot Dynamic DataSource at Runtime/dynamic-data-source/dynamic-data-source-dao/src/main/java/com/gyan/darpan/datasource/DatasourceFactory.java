package com.gyan.darpan.datasource;

import com.gyan.darpan.entity.master.TenantConfigDbDetail;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatasourceFactory {

    public DataSource createDataSource(TenantConfigDbDetail tenantConfigDbDetail) {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setPoolName(tenantConfigDbDetail.getTenantId());
        hikariConfig.setJdbcUrl(tenantConfigDbDetail.getJdcUrl());
        hikariConfig.setUsername(tenantConfigDbDetail.getUsername());
        hikariConfig.setPassword(tenantConfigDbDetail.getPassword());
        hikariConfig.setDriverClassName(tenantConfigDbDetail.getDriverClassName());

        return new HikariDataSource(hikariConfig);
    }
}
