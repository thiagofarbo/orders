package com.orders.domain.request;

import com.orders.domain.Order;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private Double price;
    private Long quantity;
}
