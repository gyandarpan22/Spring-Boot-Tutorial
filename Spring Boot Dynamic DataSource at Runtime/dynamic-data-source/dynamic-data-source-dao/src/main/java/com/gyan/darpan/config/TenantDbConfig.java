package com.gyan.darpan.config;

import com.gyan.darpan.datasource.DynamicTenantRoutingDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jpa.autoconfigure.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.gyan.darpan.repository.tenant",
        entityManagerFactoryRef = "tenantEntityManagerFactory",
        transactionManagerRef = "tenantTransactionManager"
)
@EnableTransactionManagement
public class TenantDbConfig {

    @Bean("tenantDataSource")
    public DataSource tenantDataSource(@Qualifier("masterDatasource") DataSource masterDatasource) {
        DynamicTenantRoutingDataSource dynamicTenantRoutingDataSource = new DynamicTenantRoutingDataSource();
        dynamicTenantRoutingDataSource.setTargetDataSources(new ConcurrentHashMap<>());
        dynamicTenantRoutingDataSource.setDefaultTargetDataSource(masterDatasource);
        return dynamicTenantRoutingDataSource;
    }

    @Bean("tenantEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(
            @Qualifier("tenantDataSource") DataSource dataSource,
            JpaProperties jpaProperties
    ) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.gyan.darpan.entity.tenant");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName("tenantDbPersistentUnit");

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);

        localContainerEntityManagerFactoryBean.setJpaPropertyMap(jpaProperties.getProperties());

        return localContainerEntityManagerFactoryBean;
    }

    @Bean("tenantTransactionManager")
    public PlatformTransactionManager tenantTransactionManager(@Qualifier("tenantEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(
                entityManagerFactory
        );
    }
}
