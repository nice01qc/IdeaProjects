package com.nice01qc.serial;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
public class SimpleConsumerPerSon {
    public static void main(String[] args) throws Exception {

        String topicName = "mypartition001";
        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "partitiontest05");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");

        //要发送自定义对象，需要指定对象的反序列化类
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "com.nice01qc.serial.DecodeingKafka");

        @SuppressWarnings("resource")
        KafkaConsumer<String, Object> consumer = new KafkaConsumer<String, Object>(props);
        consumer.subscribe(Arrays.asList(topicName));
        System.out.println("Subscribed to topic "+ topicName);


        while (true) {
            ConsumerRecords<String, Object> records = consumer.poll(100);
            for (ConsumerRecord<String, Object> record : records)
                System.out.println(record.toString());
        }

    }
}

