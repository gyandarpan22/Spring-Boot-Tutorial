package com.gyan.darpan.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SlaveEmployeeDbConfig {

    @Bean("slaveEmployeeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.employee.slave")
    public DataSource slaveEmployeeDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JdbcTemplate slaveEmployeeJdbcTemplate(@Qualifier("slaveEmployeeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
