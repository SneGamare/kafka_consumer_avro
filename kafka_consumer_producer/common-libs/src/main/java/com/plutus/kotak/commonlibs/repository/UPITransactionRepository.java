package com.plutus.kotak.commonlibs.repository;

import com.plutus.kotak.commonlibs.entity.UPITransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UPITransactionRepository extends JpaRepository<UPITransactionEntity, Long> {
    Optional<UPITransactionEntity> findByTransactionId(String transactionId);
} 