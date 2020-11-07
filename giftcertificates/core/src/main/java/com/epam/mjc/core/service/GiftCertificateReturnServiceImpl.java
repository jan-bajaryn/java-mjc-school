package com.epam.mjc.core.service;

import com.epam.mjc.api.controller.mapper.GiftCertificateDtoMapper;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.service.GiftCertificateReturnService;
import com.epam.mjc.api.service.help.GiftCertificateService;
import com.epam.mjc.api.util.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateReturnServiceImpl implements GiftCertificateReturnService {

    private final GiftCertificateService service;
    private final GiftCertificateDtoMapper giftCertificateDtoMapper;

    @Autowired
    public GiftCertificateReturnServiceImpl(GiftCertificateService service, GiftCertificateDtoMapper giftCertificateDtoMapper) {
        this.service = service;
        this.giftCertificateDtoMapper = giftCertificateDtoMapper;
    }

    @Override
    public GiftCertificateDto create(GiftCertificateModelForCreate giftCertificateModelForCreate) {
        return giftCertificateDtoMapper.toGiftCertificateDto(
                service.create(
                        giftCertificateDtoMapper.toGiftCertificate(giftCertificateModelForCreate)
                )
        );
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return giftCertificateDtoMapper.toGiftCertificateDto(
                service.findById(id)
        );
    }

    @Override
    public boolean update(GiftCertificateModel giftCertificateModel) {
        return service.update(
                giftCertificateDtoMapper.toGiftCertificate(giftCertificateModel)
        );
    }

    @Override
    public boolean deleteById(Long id) {
        return service.deleteById(id);
    }

    @Override
    public List<GiftCertificateDto> search(SearchParams searchParams) {
        return giftCertificateDtoMapper.toGiftCertificateDto(
                service.search(searchParams)
        );
    }
}
