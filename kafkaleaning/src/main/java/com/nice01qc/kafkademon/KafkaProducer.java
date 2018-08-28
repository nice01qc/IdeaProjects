package com.nice01qc.kafkademon;


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class KafkaProducer extends Thread {

    private final Producer<Integer,String> producer;
    private final String topic;
    private final Properties props = new Properties();
    public KafkaProducer(String topic){
        props.put("serializer.class","kafka.serializer.StringEncoder");
        props.put("metadata.broker.list","localhost:9092");
        producer = new Producer<Integer, String>(new ProducerConfig(props));
        this.topic = topic;
    }


    public void run(){
        int messageNo = 1;
        while (true){
            String messageStr = new String("Message_" + messageNo);
            System.out.println("Send: " + messageStr);
            User user = new User();
            user.setUserName(messageStr);
            producer.send(new KeyedMessage<Integer, String>(topic,new String(SeriallizeUtil.toByteArray(user))));
            messageNo++;
            try{
                sleep(700);
            }catch (InterruptedException e){
                System.out.println("I am interrupted...");
            }
        }
    }


    public static void main(String[] args) {
        KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);
        producerThread.start();
    }




}
