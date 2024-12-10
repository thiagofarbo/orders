package com.orders.controller;

import com.orders.domain.Order;
import com.orders.domain.request.OrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @PostMapping("/receive")
    public ResponseEntity<Order> receiveOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(null);
    }
}
