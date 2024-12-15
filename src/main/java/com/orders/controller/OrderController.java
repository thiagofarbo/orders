package com.orders.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.orders.domain.Order;
import com.orders.domain.request.OrderRequest;
import com.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping("/process")
    public ResponseEntity<Order> process(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
        Order order = orderService.processOrder(orderRequest);
        return ResponseEntity.ok(order);
    }
}
