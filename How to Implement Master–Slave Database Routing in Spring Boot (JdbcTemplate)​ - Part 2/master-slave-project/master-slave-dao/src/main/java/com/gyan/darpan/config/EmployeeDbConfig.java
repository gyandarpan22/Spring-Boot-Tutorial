package com.gyan.darpan.config;

import com.gyan.darpan.datasource.RoutingDataSource;
import com.gyan.darpan.enums.DbType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class EmployeeDbConfig {

    @Bean("employeeDatasource")
    public DataSource employeeDatasource(@Qualifier("masterEmployeeDataSource") DataSource masterEmployeeDataSource,
                                         @Qualifier("slaveEmployeeDataSource") DataSource slaveEmployeeDataSource) {

        RoutingDataSource routingDataSource = new RoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DbType.MASTER, masterEmployeeDataSource);
        dataSourceMap.put(DbType.SLAVE, slaveEmployeeDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterEmployeeDataSource);

        return routingDataSource;
    }

    @Bean
    public JdbcTemplate employeeJdbcTemplate(@Qualifier("employeeDatasource") DataSource employeeDatasource) {
        return new JdbcTemplate(employeeDatasource);
    }

}
