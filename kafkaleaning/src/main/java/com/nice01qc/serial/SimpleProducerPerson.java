package com.nice01qc.serial;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SimpleProducerPerson {
    public static void main(String[] args) throws Exception{

        String topicName = "mypartition001";
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("metadata.fetch.timeout.ms", 30000);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 3355443);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "com.nice01qc.serial.EncodeingKafka");

        Producer<String, Object> producer = new KafkaProducer<String, Object>(props);

        for(int i = 0; i < 2; i++){

            final int index = i;
            PerSon perSon = new PerSon();
            perSon.setAge(i);
            perSon.setAddr("My Producer TEST001"+i);
            perSon.setName("MyTest "+i);

            List<PerSon> asList = Arrays.asList(perSon,perSon);
            producer.send(new ProducerRecord<String, Object>(topicName, Integer.toString(i), asList), new Callback() {

                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (metadata != null) {
                        System.out.println(index+"  发送成功："+"checksum: "+metadata.checksum()+" offset: "+metadata.offset()+" partition: "+metadata.partition()+" topic: "+metadata.topic());
                    }
                    if (exception != null) {
                        System.out.println(index+"异常："+exception.getMessage());
                    }
                }
            });
        }
        producer.close();
    }
}

