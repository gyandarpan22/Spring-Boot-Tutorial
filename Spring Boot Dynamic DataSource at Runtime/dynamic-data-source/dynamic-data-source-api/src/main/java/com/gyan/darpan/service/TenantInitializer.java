package com.gyan.darpan.service;

import com.gyan.darpan.datasource.DatasourceFactory;
import com.gyan.darpan.datasource.DynamicTenantRoutingDataSource;
import com.gyan.darpan.entity.master.TenantConfigDbDetail;
import com.gyan.darpan.repository.master.TenantConfigDetailRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class TenantInitializer {
    private final TenantConfigDetailRepository tenantConfigDetailRepository;
    private final DatasourceFactory datasourceFactory;
    private final DynamicTenantRoutingDataSource dynamicTenantRoutingDataSource;

    @Autowired
    public TenantInitializer(TenantConfigDetailRepository tenantConfigDetailRepository,
                             DatasourceFactory datasourceFactory,
                             @Qualifier("tenantDataSource") DynamicTenantRoutingDataSource dynamicTenantRoutingDataSource) {
        this.tenantConfigDetailRepository = tenantConfigDetailRepository;
        this.datasourceFactory = datasourceFactory;
        this.dynamicTenantRoutingDataSource = dynamicTenantRoutingDataSource;
    }

    @PostConstruct
    public void loadAllTenant() {
        List<TenantConfigDbDetail> tenantConfigDbDetails = tenantConfigDetailRepository.findAll();

        if (!tenantConfigDbDetails.isEmpty()) {
            for (TenantConfigDbDetail tenantConfigDbDetail : tenantConfigDbDetails) {
                DataSource dataSource = datasourceFactory.createDataSource(tenantConfigDbDetail);
                dynamicTenantRoutingDataSource.addTenantDataSource(tenantConfigDbDetail.getTenantId(), dataSource);
            }
        }
    }

    public void initlializeTenantIfNotPresent(String tenant) {
        if (dynamicTenantRoutingDataSource.isTenantDataSourcePresent(tenant)) {
            return;
        }

        synchronized (tenant.intern()) {
            if (!dynamicTenantRoutingDataSource.isTenantDataSourcePresent(tenant)) {
                TenantConfigDbDetail tenantConfigDbDetail = tenantConfigDetailRepository.findById(tenant)
                        .orElseThrow(() -> new RuntimeException(("tenant not found")));

                DataSource dataSource = datasourceFactory.createDataSource(tenantConfigDbDetail);
                dynamicTenantRoutingDataSource.addTenantDataSource(tenantConfigDbDetail.getTenantId(), dataSource);
            }
        }
    }
}
