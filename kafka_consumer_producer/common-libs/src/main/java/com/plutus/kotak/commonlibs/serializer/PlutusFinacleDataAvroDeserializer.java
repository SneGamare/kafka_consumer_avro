package com.plutus.kotak.commonlibs.serializer;

import com.plutus.kotak.commonlibs.avro.PlutusFinacleData;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.avro.specific.SpecificRecord;

import java.util.Map;

public class PlutusFinacleDataAvroDeserializer implements Deserializer<PlutusFinacleData> {
    private final KafkaAvroDeserializer inner;

    public PlutusFinacleDataAvroDeserializer() {
        this.inner = new KafkaAvroDeserializer();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        inner.configure(configs, isKey);
    }

    @Override
    public PlutusFinacleData deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            SpecificRecord record = (SpecificRecord) inner.deserialize(topic, data);
            return (PlutusFinacleData) record;
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing PlutusFinacleData", e);
        }
    }

    @Override
    public void close() {
        inner.close();
    }
} 