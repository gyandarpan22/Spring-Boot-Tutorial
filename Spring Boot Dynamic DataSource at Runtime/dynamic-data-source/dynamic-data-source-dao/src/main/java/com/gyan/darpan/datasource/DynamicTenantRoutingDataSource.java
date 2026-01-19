package com.gyan.darpan.datasource;

import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class DynamicTenantRoutingDataSource extends AbstractRoutingDataSource {

    private final Map<Object, Object> tenantDataSource = new ConcurrentHashMap<>();

    @Override
    protected @Nullable Object determineCurrentLookupKey() {
        return TenantContextHolder.get();
    }

    public void addTenantDataSource(String tenantId, DataSource dataSource) {
        tenantDataSource.put(tenantId, dataSource);
        setTargetDataSources(tenantDataSource);
        afterPropertiesSet();
    }

    public boolean isTenantDataSourcePresent(String tenantId) {
        return tenantDataSource.containsKey(tenantId);
    }
}
