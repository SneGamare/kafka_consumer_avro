package com.plutus.kotak.commonlibs.serializer;

import com.plutus.kotak.commonlibs.avro.PlutusFinacleData;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.avro.specific.SpecificRecord;

import java.util.Map;

public class PlutusFinacleDataAvroSerializer implements Serializer<PlutusFinacleData> {
    private final KafkaAvroSerializer inner;

    public PlutusFinacleDataAvroSerializer() {
        this.inner = new KafkaAvroSerializer();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        inner.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, PlutusFinacleData data) {
        if (data == null) {
            return null;
        }

        try {
            return inner.serialize(topic, data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing PlutusFinacleData", e);
        }
    }

    @Override
    public void close() {
        inner.close();
    }
} 