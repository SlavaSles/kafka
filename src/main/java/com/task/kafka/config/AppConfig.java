package com.task.kafka.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Файл конфигурации приложения, создающий топики для producer и consumer, если они еще не созданы.
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Value("${application.kafka.topics.producer.topic:demo-topic}")
    private final String producerTopic;

    @Value("${application.kafka.topics.producer.partitions:1}")
    private final Integer producerNumPartitions;

    @Value("${application.kafka.topics.producer.partitions:1}")
    private final Integer producerReplicationFactor;

    @Value("${application.kafka.topics.consumer.topic:demo-topic}")
    private final String consumerTopic;

    @Value("${application.kafka.topics.consumer.partitions:1}")
    private final Integer consumerNumPartitions;

    @Value("${application.kafka.topics.consumer.partitions:1}")
    private final Integer consumerReplicationFactor;

    @Bean
    @Qualifier("producer")
    public NewTopic producerTopic() {
        return TopicBuilder.name(
            producerTopic)
            .partitions(producerNumPartitions)
            .replicas(producerReplicationFactor)
            .build();
    }

    @Bean
    @Qualifier("consumer")
    public NewTopic consumerTopic() {
        return TopicBuilder.name(
            consumerTopic)
            .partitions(consumerNumPartitions)
            .replicas(consumerReplicationFactor)
            .build();
    }
}
