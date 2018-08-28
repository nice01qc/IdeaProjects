package com.nice01qc.serial;

import java.util.Map;

import com.nice01qc.kafkademon.SeriallizeUtil;
import org.apache.kafka.common.serialization.Serializer;
public class EncodeingKafka implements Serializer<Object> {
    public void configure(Map configs, boolean isKey) {

    }
    public byte[] serialize(String topic, Object data) {
        return SeriallizeUtil.toByteArray(data);
    }
    /*
     * producer调用close()方法是调用
     */
    public void close() {
        System.out.println("EncodeingKafka is close");
    }
}
