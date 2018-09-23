package com;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class PropProduce {
    private static Properties kafkaProps = new Properties();
    private static KafkaProducer<String,String> producer;
    static {
        kafkaProps.put("bootstrap.servers", "broker1:9092,broker2:9092");
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<String, String>(kafkaProps);
    }

    public static void main(String[] args) {

    }


    public static void send1(){
        ProducerRecord<String,String> record = new ProducerRecord<String, String>(
                "CustomerCountry","Precision","France"
        );
        try{
            producer.send(record);

        }catch (Exception e){
            e.printStackTrace();
        }



    }
}
