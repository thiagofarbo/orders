package com.orders.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends OrdersException {

    private static final long serialVersionUID = -7331739769883331451L;

    public NotFoundException(final String message) {
        super(NOT_FOUND, message);
    }
}
