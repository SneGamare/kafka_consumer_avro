package com.plutus.kotak.commonlibs.service.impl;

import com.plutus.kotak.commonlibs.avro.PlutusFinacleData;
import com.plutus.kotak.commonlibs.entity.TransactionBlobEntity;
import com.plutus.kotak.commonlibs.entity.UPITransactionEntity;
import com.plutus.kotak.commonlibs.repository.TransactionBlobRepository;
import com.plutus.kotak.commonlibs.repository.UPITransactionRepository;
import com.plutus.kotak.commonlibs.service.TransactionService;
import com.plutus.kotak.commonlibs.util.AvroJsonConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UPITransactionRepository upiTransactionRepository;

    @Autowired
    private TransactionBlobRepository transactionBlobRepository;

    @Autowired
    private AvroJsonConverter avroJsonConverter;

    @Override
    @Transactional
    public UPITransactionEntity createTransaction(PlutusFinacleData plutusFinacleData) {
        try {
            String transactionId = UUID.randomUUID().toString();
            
            // Convert Avro to JSON Map
            Map<String, Object> jsonData = avroJsonConverter.convertAvroToJson(plutusFinacleData);
            
            // Create UPI transaction entity
            UPITransactionEntity upiEntity = new UPITransactionEntity();
            upiEntity.setTransactionId(transactionId);
            upiEntity.setUpiId("DEFAULT_UPI_ID"); // Set default value since not in Avro schema
            upiEntity.setRrn("DEFAULT_RRN"); // Set default value since not in Avro schema
            
            // Extract relevant fields from JSON data
            if (jsonData.containsKey("TRANAMT")) {
                upiEntity.setAmount(jsonData.get("TRANAMT").toString());
            }
            if (jsonData.containsKey("TRSTATUS")) {
                upiEntity.setStatus(jsonData.get("TRSTATUS").toString());
            }
            
            // Set audit fields
            upiEntity.setCreatedAt(LocalDateTime.now());
            upiEntity.setCreatedBy("SYSTEM");
            upiEntity.setModifiedAt(LocalDateTime.now());
            upiEntity.setModifiedBy("SYSTEM");

            // Create transaction blob entity with JSON data
            TransactionBlobEntity blobEntity = new TransactionBlobEntity();
            blobEntity.setTransactionId(transactionId);
            blobEntity.setJsonData(avroJsonConverter.convertToJsonString(jsonData));
            
            // Set audit fields
            blobEntity.setCreatedAt(LocalDateTime.now());
            blobEntity.setCreatedBy("SYSTEM");
            blobEntity.setModifiedAt(LocalDateTime.now());
            blobEntity.setModifiedBy("SYSTEM");

            // Save both entities
            UPITransactionEntity savedUpiEntity = upiTransactionRepository.save(upiEntity);
            transactionBlobRepository.save(blobEntity);

            log.info("Transaction saved successfully with ID: {}", transactionId);
            return savedUpiEntity;
        } catch (Exception e) {
            log.error("Error saving transaction: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save transaction", e);
        }
    }

    @Override
    public UPITransactionEntity getTransactionById(String transactionId) {
        return upiTransactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));
    }

    @Override
    public TransactionBlobEntity getTransactionBlobById(String transactionId) {
        return transactionBlobRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction blob not found with ID: " + transactionId));
    }
} 