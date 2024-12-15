package com.orders.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderEnum {

    PENDING("PENDING"),
    PROCESSED("PROCESSED");

    private String description;
}
