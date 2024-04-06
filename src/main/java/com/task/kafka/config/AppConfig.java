package com.task.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;

//@Configuration
public class AppConfig {

//    public final String topicName;
//
//    public AppConfig(@Value("${application.kafka.topic}") String topicName) {
//        this.topicName = topicName;
//    }
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        return JacksonUtils.enhancedObjectMapper();
//    }
//
//    @Bean
//    public ProducerFactory<String, StringValue> producerFactory(
//            KafkaProperties kafkaProperties, ObjectMapper mapper) {
//        var props = kafkaProperties.buildProducerProperties();
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//
//        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, StringValue>(props);
//        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(mapper));
//        return kafkaProducerFactory;
//    }
//
//    @Bean
//    public KafkaTemplate<String, StringValue> kafkaTemplate(
//            ProducerFactory<String, StringValue> producerFactory) {
//        return new KafkaTemplate<>(producerFactory);
//    }
//
//    @Bean
//    public NewTopic topic() {
//        return TopicBuilder.name(topicName).partitions(1).replicas(1).build();
//    }
//
//    @Bean
//    public DataSender dataSender(NewTopic topic, KafkaTemplate<String, StringValue> kafkaTemplate) {
//        return new DataSenderKafka(
//                topic.name(),
//                kafkaTemplate,
//                stringValue -> log.info("asked, value:{}", stringValue));
//    }
//
//    @Bean
//    public StringValueSource stringValueSource(DataSender dataSender) {
//        return new StringValueSource(dataSender);
//    }
}
