package com.gyan.darpan.dao.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class EmployeeDbConfig {

    @Bean("employeeDataSource")
    @ConfigurationProperties("spring.datasource.employee")
    public DataSource employeeDataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean("employeeJdbcTemplate")
    public JdbcTemplate employeeJdbcTemplate(@Qualifier("employeeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
