package com;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;

public class PropConsumer {
    private static Properties props = new Properties();
    private static KafkaConsumer<String,String> consumer;
    static {
        props.put("bootstrap.servers","broker1:9092,broker2:9092");
        props.put("group.id","CountryCounter");
        props.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<String, String>(props);
    }

    public static void main(String[] args) {
        consumer.subscribe(Collections.singleton("customerCountries"));
        try{
            while (true){
                ConsumerRecords<String,String> records = consumer.poll(100);
                System.out.println(records.count());
                for (ConsumerRecord record : records){
                    System.out.println(record.key() + " " + record.partition() + " " + record.topic());
                }
            }
        }catch (Exception e){

        }finally {
            consumer.close();
        }
    }
}
