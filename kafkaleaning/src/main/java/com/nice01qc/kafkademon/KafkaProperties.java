package com.nice01qc.kafkademon;

public interface KafkaProperties {

    final String zkConnect = "localhost:2181";
    final String groupId = "group1";
    final String topic ="topic1";
    final String kafkaServerURL = "localhost";
    final int kafkaServerPort = 9092;
    final int kafkaProducerBufferSize = 64 * 1024;
    final int connectionTimeOut = 20000;
    final int reconnectInterval = 10000;
    final String topic2 = "topic2";
    final String topic3 = "topic3";
    final String clientId = "SimpleConsumerDemoClient";

}
