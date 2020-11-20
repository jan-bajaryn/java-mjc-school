package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.controller.OrderController;
import com.epam.mjc.api.domain.Order;
import com.epam.mjc.api.model.dto.OrderDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", uses = {UserMapper.class, PurchasesDtoMapper.class})
public interface OrderMapper {
    @Mapping(target = "purchases", source = "purchaseCertificates")
    OrderDto toOrderDto(Order order);

    @AfterMapping
    default void afterToOrderDto(Order from, @MappingTarget OrderDto to) {
        setSelfLinks(to);
    }

    default void setSelfLinks(OrderDto byId) {
        Link selfLink = linkTo(methodOn(OrderController.class)
                .findById(byId.getId())).withSelfRel();
        byId.add(selfLink);
    }

    List<OrderDto> toOrderDto(List<Order> orders);
}
