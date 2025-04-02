package com.plutus.kotak.commonlibs.controller;

import com.plutus.kotak.commonlibs.avro.PlutusFinacleData;
import com.plutus.kotak.commonlibs.entity.TransactionBlobEntity;
import com.plutus.kotak.commonlibs.entity.UPITransactionEntity;
import com.plutus.kotak.commonlibs.model.ApiResponse;
import com.plutus.kotak.commonlibs.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ApiResponse<UPITransactionEntity>> createTransaction(@RequestBody Map<String, List<Map<String, Object>>> request) {
        try {
            log.info("Received transaction request: {}", request);
            
            if (request == null || request.isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Request body cannot be empty", null));
            }

            // Validate required fields
            if (!request.containsKey("FORACID") || !request.containsKey("ACCTNAME") || 
                !request.containsKey("TRANAMT") || !request.containsKey("TRSTATUS")) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Missing required fields", null));
            }

            // Create PlutusFinacleData from request
            PlutusFinacleData.Builder builder = PlutusFinacleData.newBuilder();
            
            // Set FORACID
            String foracid = request.get("FORACID").get(0).get("value").toString();
            builder.setFORACID(ByteBuffer.wrap(foracid.getBytes()));
            
            // Set ACCTNAME
            String acctname = request.get("ACCTNAME").get(0).get("value").toString();
            builder.setACCTNAME(ByteBuffer.wrap(acctname.getBytes()));
            
            // Set TRANAMT
            String tranamt = request.get("TRANAMT").get(0).get("value").toString();
            builder.setTRANAMT(Double.parseDouble(tranamt));
            
            // Set TRSTATUS
            String trstatus = request.get("TRSTATUS").get(0).get("value").toString();
            builder.setTRSTATUS(ByteBuffer.wrap(trstatus.getBytes()));

            // Build the PlutusFinacleData object
            PlutusFinacleData plutusFinacleData = builder.build();

            // Save transaction
            UPITransactionEntity savedTransaction = transactionService.createTransaction(plutusFinacleData);
            
            return ResponseEntity.ok(new ApiResponse<>(true, "Transaction created successfully", savedTransaction));
        } catch (NumberFormatException e) {
            log.error("Invalid number format in request: {}", e.getMessage());
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Invalid number format in request", null));
        } catch (Exception e) {
            log.error("Error creating transaction: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse<>(false, "Failed to create transaction: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<UPITransactionEntity>> getTransaction(@PathVariable String transactionId) {
        try {
            if (transactionId == null || transactionId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Transaction ID cannot be empty", null));
            }

            UPITransactionEntity transaction = transactionService.getTransactionById(transactionId);
            if (transaction == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Transaction retrieved successfully", transaction));
        } catch (Exception e) {
            log.error("Error retrieving transaction: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse<>(false, "Failed to retrieve transaction: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{transactionId}/blob")
    public ResponseEntity<ApiResponse<TransactionBlobEntity>> getTransactionBlob(@PathVariable String transactionId) {
        try {
            if (transactionId == null || transactionId.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Transaction ID cannot be empty", null));
            }

            TransactionBlobEntity blob = transactionService.getTransactionBlobById(transactionId);
            if (blob == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new ApiResponse<>(true, "Transaction blob retrieved successfully", blob));
        } catch (Exception e) {
            log.error("Error retrieving transaction blob: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError()
                .body(new ApiResponse<>(false, "Failed to retrieve transaction blob: " + e.getMessage(), null));
        }
    }
} 