package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.controller.GiftCertificateController;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.hateoas.Link;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(componentModel = "spring", uses = {TagDtoMapper.class})
public interface GiftCertificateDtoMapper {
    GiftCertificateDtoMapper INSTANCE = Mappers.getMapper(GiftCertificateDtoMapper.class);
    String DELETE = "delete";
    String UPDATE = "update";

    GiftCertificateDto toGiftCertificateDto(GiftCertificate giftCertificate);

    @AfterMapping
    default void afterToOrderDto(GiftCertificate from, @MappingTarget GiftCertificateDto to) {
        setSelfLinks(to);
    }


    default void setSelfLinks(GiftCertificateDto byId) {
        Link selfLink = linkTo(methodOn(GiftCertificateController.class)
                .showById(byId.getId())).withSelfRel();
        Link delete = linkTo(methodOn(GiftCertificateController.class)
                .certificateDelete(byId.getId())).withRel(DELETE);
        Link update = linkTo(methodOn(GiftCertificateController.class)
                .certificateUpdate(byId.getId(), new GiftCertificateModel())).withRel(UPDATE);
        byId.add(selfLink, delete, update);
    }

    List<GiftCertificateDto> toGiftCertificateDto(List<GiftCertificate> giftCertificates);

    GiftCertificate toGiftCertificate(GiftCertificateModelForCreate forCreate);

    GiftCertificate toGiftCertificate(GiftCertificateModel model);
}
