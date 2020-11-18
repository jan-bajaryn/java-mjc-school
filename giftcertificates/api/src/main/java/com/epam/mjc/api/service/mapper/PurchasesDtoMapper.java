package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.domain.PurchaseCertificate;
import com.epam.mjc.api.model.dto.PurchaseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchasesDtoMapper {
    PurchasesDtoMapper INSTANCE = Mappers.getMapper(PurchasesDtoMapper.class);

    @Mapping(target = "name", source = "oldName")
    @Mapping(target = "certificateId", source = "giftCertificate.id")
    PurchaseDto toPurchaseDto(PurchaseCertificate purchaseCertificate);

    List<PurchaseDto> toPurchaseDto(List<PurchaseCertificate> purchaseCertificates);

}
