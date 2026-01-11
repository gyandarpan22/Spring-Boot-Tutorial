package com.gyan.darpan.datasource;

import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected @Nullable Object determineCurrentLookupKey() {
        return DbContextHolder.get();
    }
}
