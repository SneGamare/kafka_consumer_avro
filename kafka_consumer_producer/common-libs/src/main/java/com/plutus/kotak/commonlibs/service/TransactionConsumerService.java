package com.plutus.kotak.commonlibs.service;

import com.plutus.kotak.commonlibs.avro.PlutusFinacleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class TransactionConsumerService {

    @Autowired
    private TransactionService transactionService;

    @Bean
    public Consumer<PlutusFinacleData> processTransaction() {
        return transaction -> {
            try {
                saveTransaction(transaction);
            } catch (Exception e) {
                // Handle error appropriately
                e.printStackTrace();
            }
        };
    }

    protected void saveTransaction(PlutusFinacleData transaction) {
        transactionService.createTransaction(transaction);
    }
} 