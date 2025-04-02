package com.plutus.kotak.commonlibs.service;

import com.plutus.kotak.commonlibs.avro.PlutusFinacleData;
import com.plutus.kotak.commonlibs.entity.TransactionEntity;
import com.plutus.kotak.commonlibs.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void testCreateTransaction() {
        // Create a test transaction
        PlutusFinacleData data = new PlutusFinacleData();
        data.put(0, Base64.getEncoder().encodeToString("1234567890".getBytes())); // FORACID
        data.put(1, Base64.getEncoder().encodeToString("John Doe".getBytes())); // ACCT_NAME
        data.put(2, "2024-03-20"); // LAST_TRAN_DATE_CR
        data.put(3, "2024-03-20"); // TRAN_DATE
        data.put(4, Base64.getEncoder().encodeToString("Transaction123".getBytes())); // TRAN_ID
        data.put(5, Base64.getEncoder().encodeToString("12345".getBytes())); // PART_TRAN_SRL_NUM
        data.put(6, Base64.getEncoder().encodeToString("N".getBytes())); // DEL_FLG
        data.put(7, Base64.getEncoder().encodeToString("Credit".getBytes())); // TRAN_TYPE
        data.put(8, Base64.getEncoder().encodeToString("Transfer".getBytes())); // TRAN_SUB_TYPE
        data.put(12, "2024-03-20"); // VALUE_DATE
        data.put(13, 1000.50); // TRAN_AMT
        data.put(14, Base64.getEncoder().encodeToString("Test Transaction".getBytes())); // TRAN_PARTICULAR
        data.put(15, "2024-03-20"); // ENTRY_DATE
        data.put(16, "2024-03-20"); // PSTD_DATE

        // Mock repository behavior
        String transactionId = UUID.randomUUID().toString();
        TransactionEntity savedEntity = new TransactionEntity();
        savedEntity.setTransactionId(transactionId);
        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(savedEntity);

        // Create transaction
        String result = transactionService.createTransaction(data);

        // Verify result
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(transactionId);
    }
} 