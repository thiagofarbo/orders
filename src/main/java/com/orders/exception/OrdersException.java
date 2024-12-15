package com.orders.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
public class OrdersException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus status;

    public OrdersException(HttpStatus status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
