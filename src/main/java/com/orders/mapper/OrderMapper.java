package com.orders.mapper;

import com.orders.domain.Order;
import com.orders.domain.request.OrderRequest;
import com.orders.domain.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "request.externalId", target = "externalId"),
            @Mapping(source = "request.totalValue", target = "totalValue"),
            @Mapping(source = "request.status", target = "status"),

    })
    Order toEntity(OrderRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "entity.externalId", target = "externalId"),
            @Mapping(source = "entity.totalValue", target = "totalValue"),
            @Mapping(source = "entity.status", target = "status"),

    })
    OrderResponse toOrderResponse(Order entity);
}
