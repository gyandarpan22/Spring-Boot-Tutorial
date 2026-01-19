package com.gyan.darpan.filter;


import com.gyan.darpan.datasource.TenantContextHolder;
import com.gyan.darpan.service.TenantInitializer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {
    private final TenantInitializer tenantInitializer;

    @Autowired
    public TenantFilter(TenantInitializer tenantInitializer) {
        this.tenantInitializer = tenantInitializer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String tenantId = request.getHeader("X-TENANT-ID");

        if (tenantId == null || tenantId.isBlank()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        tenantInitializer.initlializeTenantIfNotPresent(tenantId);

        try {
            TenantContextHolder.set(tenantId);

            filterChain.doFilter(request, response);
        } finally {
            TenantContextHolder.clear();
        }
    }
}
