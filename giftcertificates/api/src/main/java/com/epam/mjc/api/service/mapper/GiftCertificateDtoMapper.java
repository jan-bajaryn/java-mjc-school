package com.epam.mjc.api.service.mapper;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TagDtoMapper.class})
public interface GiftCertificateDtoMapper {
    GiftCertificateDtoMapper INSTANCE = Mappers.getMapper(GiftCertificateDtoMapper.class);

    GiftCertificateDto toGiftCertificateDto(GiftCertificate giftCertificate);

    List<GiftCertificateDto> toGiftCertificateDto(List<GiftCertificate> giftCertificates);

    GiftCertificate toGiftCertificate(GiftCertificateModelForCreate forCreate);

    GiftCertificate toGiftCertificate(GiftCertificateModel model);
}
