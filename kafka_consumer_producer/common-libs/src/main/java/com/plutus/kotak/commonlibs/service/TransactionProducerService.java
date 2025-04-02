package com.plutus.kotak.commonlibs.service;

import com.plutus.kotak.commonlibs.avro.PlutusFinacleData;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class TransactionProducerService {
    private final Sinks.Many<Message<PlutusFinacleData>> sink = Sinks.many().multicast().onBackpressureBuffer();

    public void sendTransaction(PlutusFinacleData transaction) {
        if (transaction == null || transaction.getFORACID() == null) {
            throw new IllegalArgumentException("Transaction and FORACID cannot be null");
        }

        Message<PlutusFinacleData> message = MessageBuilder
            .withPayload(transaction)
            .setHeader("FORACID", transaction.getFORACID().toString())
            .build();

        sink.tryEmitNext(message);
    }

    public Sinks.Many<Message<PlutusFinacleData>> getSink() {
        return sink;
    }
} 