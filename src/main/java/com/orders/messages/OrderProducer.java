package com.orders.messages;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Configuration
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${topic.order.producer}")
    private String topicName;

    @Value("${topic.orders-calculated}")
    private String topicOrdersCalculated;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendOrder(final String order) {
        log.info("Payload: {}", order);
        kafkaTemplate.send(topicName, order);
    }

    public void sendOrderCalculated(final String order) {
        log.info("Sending order to Kafka: {}", order);
        kafkaTemplate.send(topicOrdersCalculated, order);
    }
}
