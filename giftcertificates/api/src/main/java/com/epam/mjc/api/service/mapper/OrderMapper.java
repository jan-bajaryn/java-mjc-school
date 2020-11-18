package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.model.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, PurchasesDtoMapper.class})
public interface OrderMapper {
    @Mapping(target = "purchases", source = "purchaseCertificates")
    OrderDto toOrderDto(Order order);

    List<OrderDto> toOrderDto(List<Order> orders);
}
