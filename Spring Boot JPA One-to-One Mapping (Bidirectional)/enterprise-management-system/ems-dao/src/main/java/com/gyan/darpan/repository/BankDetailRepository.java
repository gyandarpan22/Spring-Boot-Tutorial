package com.gyan.darpan.repository;

import com.gyan.darpan.entity.BankDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankDetailRepository extends JpaRepository<BankDetail, Long> {

    Optional<BankDetail> findByEmployeeId(Long employeeId);
}
