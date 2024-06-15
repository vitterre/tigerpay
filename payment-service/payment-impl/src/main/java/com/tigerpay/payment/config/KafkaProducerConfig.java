package com.tigerpay.payment.config;

import com.tigerpay.payment.event.TransferEvent;
import lombok.val;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@PropertySource("classpath:kafka.yaml")
public class KafkaProducerConfig {

    @Value("${kafka.bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Value("${kafka.producer.client-id}")
    private String kafkaProducerId;

    @Bean
    public Map<String, Object> producerConfig() {
        val properties = new HashMap<String, Object>();

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProducerId);

        return properties;
    }

    @Bean
    public ProducerFactory<UUID, TransferEvent> producerTransferEventFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<UUID, TransferEvent> kafkaTransferEventTemplate() {
        val template = new KafkaTemplate<>(producerTransferEventFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }
}
