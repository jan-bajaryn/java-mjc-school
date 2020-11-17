package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.model.dto.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, GiftCertificateDtoMapper.class})
public interface OrderMapper {
    OrderDto toOrderDto(Order order);

    List<OrderDto> toOrderDto(List<Order> orders);
}
