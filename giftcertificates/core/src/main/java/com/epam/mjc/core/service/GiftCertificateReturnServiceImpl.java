package com.epam.mjc.core.service;

import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.service.GiftCertificateReturnService;
import com.epam.mjc.api.service.help.GiftCertificateService;
import com.epam.mjc.api.service.mapper.GiftCertificateDtoMapper;
import com.epam.mjc.api.service.mapper.SortMapper;
import com.epam.mjc.api.util.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateReturnServiceImpl implements GiftCertificateReturnService {

    private final GiftCertificateService service;
    private final GiftCertificateDtoMapper giftCertificateDtoMapper;
    private final SortMapper sortMapper;

    @Autowired
    public GiftCertificateReturnServiceImpl(GiftCertificateService service, GiftCertificateDtoMapper giftCertificateDtoMapper, SortMapper sortMapper) {
        this.service = service;
        this.giftCertificateDtoMapper = giftCertificateDtoMapper;
        this.sortMapper = sortMapper;
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
    public void update(GiftCertificateModel giftCertificateModel) {
        service.update(
                giftCertificateDtoMapper.toGiftCertificate(giftCertificateModel)
        );
    }

    @Override
    public void deleteById(Long id) {
        service.deleteById(id);
    }

    @Override
    public List<GiftCertificateDto> search(String tagName, String partName, String partDescription, String sort) {
        return giftCertificateDtoMapper.toGiftCertificateDto(
                service.search(new SearchParams(tagName, partName, partDescription, sortMapper.toSortParams(sort)))
        );
    }
}
