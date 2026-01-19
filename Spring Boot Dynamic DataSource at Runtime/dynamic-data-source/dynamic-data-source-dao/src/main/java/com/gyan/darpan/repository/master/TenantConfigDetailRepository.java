package com.gyan.darpan.repository.master;

import com.gyan.darpan.entity.master.TenantConfigDbDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantConfigDetailRepository extends JpaRepository<TenantConfigDbDetail, String> {
}
