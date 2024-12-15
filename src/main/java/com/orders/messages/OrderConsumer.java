package com.orders.messages;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.config.ConfigurationUtils;
import com.orders.domain.CalculatedOrders;
import com.orders.domain.enums.OrderEnum;
import com.orders.domain.request.OrderRequest;
import com.orders.repository.CalculatedOrdersRepository;
import com.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Properties;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderConsumer {

    @Value("${topic.order.consumer}")
    private String topicName;

    @Value("${spring.kafka.bootstrap-servers}")
    public String bootStrapServer;

    @Value("${spring.kafka.consumer.group-id}")
    public String idGroup;

    private final OrderService orderService;

    private final ObjectMapper objectMapper;

    private final CalculatedOrdersRepository calculatedOrdersRepository;

    @KafkaListener(topics = "${topic.order.consumer}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(ConsumerRecord<String, String> payload) {

        final Properties properties = ConfigurationUtils.consumerConfiguration();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Arrays.asList(topicName));

            log.info("key: {}", payload.key());
            log.info("Headers: {}", payload.headers());
            log.info("Partition: {}", payload.partition());
            log.info("Result Order: {}", payload.value());

            try {
                objectMapper.findAndRegisterModules();
                final String message = payload.value();
                var orderRequest = objectMapper.readValue(message, OrderRequest.class);
                orderService.processOrder(orderRequest);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar a mensagem Kafka", e);
            }
        }
    }

    @KafkaListener(topics = "orders-calculated", groupId = "product-external-b-group_id")
    public void receiveProcessedOrder(ConsumerRecord<String, String> payload) {

        final Properties properties = ConfigurationUtils.consumerConfiguration();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties)) {
            consumer.subscribe(Arrays.asList(topicName));

            log.info("key: {}", payload.key());
            log.info("Headers: {}", payload.headers());
            log.info("Partition: {}", payload.partition());
            log.info("Received order processed: {}", payload.value());

            try {
                objectMapper.findAndRegisterModules();
                final String message = payload.value();
                CalculatedOrders calculatedOrders = objectMapper.readValue(message, CalculatedOrders.class);
                calculatedOrders.setStatus(OrderEnum.PROCESSED.name());
                calculatedOrdersRepository.save(calculatedOrders);
            } catch (Exception e) {
                throw new RuntimeException("Error to process a message calculated Kafka", e);
            }
        }
    }
}
