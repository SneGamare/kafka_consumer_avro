package com.plutus.kotak.commonlibs.repository;

import com.plutus.kotak.commonlibs.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testSaveAndFindTransaction() {
        // Create a test transaction
        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionId("TEST123");
        transaction.setForacid("1234567890");
        transaction.setAcctName("John Doe");
        transaction.setLastTranDateCr("2024-03-20");
        transaction.setTranDate("2024-03-20");
        transaction.setValueDate("2024-03-20");
        transaction.setTranId("Transaction123");
        transaction.setPartTranSrlNum("12345");
        transaction.setDelFlg("N");
        transaction.setTranType("Credit");
        transaction.setTranSubType("Transfer");
        transaction.setTranAmt(1000.50);

        // Save the transaction
        TransactionEntity savedTransaction = transactionRepository.save(transaction);
        assertThat(savedTransaction).isNotNull();
        assertThat(savedTransaction.getTransactionId()).isEqualTo("TEST123");

        // Find the transaction
        Optional<TransactionEntity> foundTransaction = transactionRepository.findById("TEST123");
        assertThat(foundTransaction).isPresent();
        assertThat(foundTransaction.get().getTransactionId()).isEqualTo("TEST123");
        assertThat(foundTransaction.get().getTranAmt()).isEqualTo(1000.50);
    }
} 