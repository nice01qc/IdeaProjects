package com.nice01qc.serial;


import java.util.Map;

import com.nice01qc.kafkademon.SeriallizeUtil;
import org.apache.kafka.common.serialization.Deserializer;

public class DecodeingKafka implements Deserializer<Object> {

    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    public Object deserialize(String topic, byte[] data) {
        return SeriallizeUtil.toObject(data);
    }

    public void close() {

    }


}

