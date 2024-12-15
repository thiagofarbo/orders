package com.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orders.domain.CalculatedOrders;
import com.orders.domain.Order;
import com.orders.domain.OrderItem;
import com.orders.domain.enums.OrderEnum;
import com.orders.domain.request.OrderItemRequest;
import com.orders.domain.request.OrderRequest;
import com.orders.exception.DuplicateOrdersException;
import com.orders.mapper.JsonConverter;
import com.orders.messages.OrderProducer;
import com.orders.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderProducer orderProducer;

    private final OrderRepository orderRepository;

    private final JsonConverter converter;

    @Transactional
    public Order processOrder(OrderRequest orderRequest) throws JsonProcessingException {
        validateDuplicateOrder(orderRequest);

        Order order = initializeOrder(orderRequest);
        List<OrderItem> items = mapOrderItems(orderRequest.getItems(), order);
        double totalValue = calculateTotalValue(items);

        order.setItems(items);
        order.setTotalValue(totalValue);

        Order savedOrder = saveOrder(order);
        publishOrderToKafka(savedOrder);

        return savedOrder;
    }

    private void validateDuplicateOrder(OrderRequest orderRequest) {
        if (orderRepository.findByExternalId(orderRequest.getExternalId()).isPresent()) {
            throw new DuplicateOrdersException(HttpStatus.BAD_REQUEST, "Order already exists");
        }
    }

    private Order initializeOrder(OrderRequest orderRequest) {
        orderRequest.setStatus(OrderEnum.PENDING.name());

        Order order = new Order();
        order.setExternalId(orderRequest.getExternalId());
        order.setStatus(orderRequest.getStatus());
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalValue(orderRequest.getTotalValue());

        return orderRepository.save(order);
    }

    private List<OrderItem> mapOrderItems(List<OrderItemRequest> itemRequests, Order order) {
        return itemRequests.stream()
                .map(itemDto -> new OrderItem(
                        itemDto.getName(),
                        itemDto.getPrice(),
                        itemDto.getQuantity(),
                        order))
                .collect(Collectors.toList());
    }

    private double calculateTotalValue(List<OrderItem> items) {
        return items.stream()
                .mapToDouble(OrderItem::getPrice)
                .sum();
    }

    private Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    private void publishOrderToKafka(Order savedOrder) throws JsonProcessingException {
        CalculatedOrders calculatedOrders = CalculatedOrders.builder()
                .orderId(savedOrder.getId())
                .externalId(savedOrder.getExternalId())
                .status(savedOrder.getStatus())
                .totalValue(savedOrder.getTotalValue())
                .createdAt(savedOrder.getCreatedAt())
                .build();

        final String message = converter.toJsonString(calculatedOrders);
        orderProducer.sendOrderCalculated(message);
    }

}
