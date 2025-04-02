package com.plutus.kotak.commonlibs.service;

import com.plutus.kotak.commonlibs.avro.PlutusFinacleData;
import com.plutus.kotak.commonlibs.entity.TransactionBlobEntity;
import com.plutus.kotak.commonlibs.entity.UPITransactionEntity;

public interface TransactionService {
    UPITransactionEntity createTransaction(PlutusFinacleData plutusFinacleData);
    UPITransactionEntity getTransactionById(String transactionId);
    TransactionBlobEntity getTransactionBlobById(String transactionId);
} 