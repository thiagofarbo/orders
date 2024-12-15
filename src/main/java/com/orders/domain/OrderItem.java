package com.orders.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = OrderItem.TABLE_NAME)
@AllArgsConstructor
public class OrderItem {

    public static final String TABLE_NAME = "order_items";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gen_" + TABLE_NAME)
    @SequenceGenerator(name = "gen_" + TABLE_NAME, sequenceName = "sq_" + TABLE_NAME, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    public OrderItem() {
    }

    public OrderItem(String name, Double price, Long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderItem(String name, Double price, Long quantity, Order order) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
    }
}

