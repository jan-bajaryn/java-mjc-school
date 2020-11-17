package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TagDtoMapper.class})
public interface GiftCertificateDtoMapper {
    GiftCertificateDtoMapper INSTANCE = Mappers.getMapper(GiftCertificateDtoMapper.class);

    GiftCertificateDto toGiftCertificateDto(GiftCertificate giftCertificate);

    List<GiftCertificateDto> toGiftCertificateDto(List<GiftCertificate> giftCertificates);

    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    GiftCertificate toGiftCertificate(GiftCertificateModelForCreate forCreate);

    @Mapping(target = "orders", ignore = true)
    GiftCertificate toGiftCertificate(GiftCertificateModel model);

}
