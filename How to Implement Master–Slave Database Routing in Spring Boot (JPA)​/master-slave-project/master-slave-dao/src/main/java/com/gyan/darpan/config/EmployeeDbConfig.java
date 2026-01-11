package com.gyan.darpan.config;

import com.gyan.darpan.datasource.RoutingDataSource;
import com.gyan.darpan.enums.DbType;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jpa.autoconfigure.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.gyan.darpan",
        entityManagerFactoryRef = "employeeEntityManagerFactory",
        transactionManagerRef = "employeeTransactionManager"
)
@EnableTransactionManagement
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

    @Bean
    public LocalContainerEntityManagerFactoryBean employeeEntityManagerFactory(
            @Qualifier("employeeDatasource") DataSource dataSource,
            JpaProperties jpaProperties
    ) {

        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.gyan.darpan");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("employeePersistentUnit");

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        localContainerEntityManagerFactoryBean.setJpaPropertyMap(jpaProperties.getProperties());

        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager employeeTransactionManager(@Qualifier("employeeEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}
