package com.orders.domain.request;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table
@Entity
public class OrderRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String externalId;
    private Double totalValue;
    private String status;
    private LocalDateTime createdAt;
}
