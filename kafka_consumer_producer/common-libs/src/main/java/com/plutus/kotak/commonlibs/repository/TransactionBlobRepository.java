package com.plutus.kotak.commonlibs.repository;

import com.plutus.kotak.commonlibs.entity.TransactionBlobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionBlobRepository extends JpaRepository<TransactionBlobEntity, Long> {
    Optional<TransactionBlobEntity> findByTransactionId(String transactionId);
} 