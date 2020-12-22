package com.epam.mjc.core.service;

import com.epam.mjc.api.domain.Role;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.service.GiftCertificateReturnService;
import com.epam.mjc.api.service.SecurityService;
import com.epam.mjc.api.service.help.GiftCertificateService;
import com.epam.mjc.api.service.mapper.GiftCertificateDtoMapper;
import com.epam.mjc.api.service.mapper.SortMapper;
import com.epam.mjc.api.service.mapper.TagNameMapper;
import com.epam.mjc.api.util.HateoasManager;
import com.epam.mjc.api.util.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateReturnServiceImpl implements GiftCertificateReturnService {

    private final GiftCertificateService service;
    private final GiftCertificateDtoMapper giftCertificateDtoMapper;
    private final SortMapper sortMapper;
    private final TagNameMapper tagNameMapper;
    private final HateoasManager hateoasManager;
    private final SecurityService securityService;


    @Autowired
    public GiftCertificateReturnServiceImpl(GiftCertificateService service, GiftCertificateDtoMapper giftCertificateDtoMapper, SortMapper sortMapper, TagNameMapper tagNameMapper, HateoasManager hateoasManager, SecurityService securityService) {
        this.service = service;
        this.giftCertificateDtoMapper = giftCertificateDtoMapper;
        this.sortMapper = sortMapper;
        this.tagNameMapper = tagNameMapper;
        this.hateoasManager = hateoasManager;
        this.securityService = securityService;
    }

    @Override
    public GiftCertificateDto create(GiftCertificateModelForCreate giftCertificateModelForCreate) {

        GiftCertificateDto result = giftCertificateDtoMapper.toGiftCertificateDto(
                service.create(
                        giftCertificateDtoMapper.toGiftCertificate(giftCertificateModelForCreate)
                )
        );
        hateoasManager.setSelfLinksAdmin(result);
        return result;
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        Optional<User> principal = securityService.getPrincipal();

        GiftCertificateDto byId = giftCertificateDtoMapper.toGiftCertificateDto(
                service.findById(id)
        );

        if (principal.isPresent() && principal.get().getRole() == Role.ADMIN) {
            hateoasManager.setSelfLinksAdmin(byId);
        } else {
            hateoasManager.setSelfLinksNotAdmin(byId);
        }
        return byId;
    }

    @Override
    public GiftCertificateDto update(Long id, GiftCertificateModel giftCertificateModel) {
        GiftCertificateDto result = giftCertificateDtoMapper.toGiftCertificateDto(service.update(id,
                giftCertificateDtoMapper.toGiftCertificate(giftCertificateModel)
                )
        );
        hateoasManager.setSelfLinksAdmin(result);
        return result;
    }

    @Override
    public void deleteById(Long id) {
        service.deleteById(id);
    }

    @Override
    public CollectionModel<GiftCertificateDto> search(String tagName, String partName, String partDescription, String sort, Integer pageNumber, Integer pageSize) {
        Optional<User> principal = securityService.getPrincipal();


        List<GiftCertificateDto> giftCertificateDtos = giftCertificateDtoMapper.toGiftCertificateDto(
                service.search(new SearchParams(tagNameMapper.toTagNameList(tagName), partName, partDescription, sortMapper.toSortParams(sort)), pageNumber, pageSize)
        );

        CollectionModel<GiftCertificateDto> model = new CollectionModel<>(giftCertificateDtos);

        if (principal.isPresent() && principal.get().getRole() == Role.ADMIN) {
            hateoasManager.certificateCollectionLinksAdmin(model);
        } else {
            hateoasManager.certificateCollectionLinksNotAdmin(model);
        }

        return model;
    }
}
