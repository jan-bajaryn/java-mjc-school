package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.GiftCertificateController;
import com.epam.mjc.api.domain.Role;
import com.epam.mjc.api.domain.User;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.service.GiftCertificateReturnService;
import com.epam.mjc.api.util.HateoasManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certificates")
public class GiftCertificateControllerImpl implements GiftCertificateController {


    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "5";


    private static final Logger log = LoggerFactory.getLogger(GiftCertificateControllerImpl.class);

    private final HateoasManager hateoasManager;
    private final GiftCertificateReturnService giftCertificateReturnService;


    @Autowired
    public GiftCertificateControllerImpl(HateoasManager hateoasManager, GiftCertificateReturnService giftCertificateReturnService) {
        this.hateoasManager = hateoasManager;
        this.giftCertificateReturnService = giftCertificateReturnService;
    }

    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<GiftCertificateDto> certificateCreate(
            @RequestBody GiftCertificateModelForCreate giftCertificateModelForCreate
    ) {
        GiftCertificateDto result = giftCertificateReturnService.create(giftCertificateModelForCreate);
        hateoasManager.setSelfLinksAdmin(result);
        return new ResponseEntity<>(
                result,
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<GiftCertificateDto> showById(@PathVariable Long id,
                                                       @AuthenticationPrincipal User principal) {
        GiftCertificateDto byId = giftCertificateReturnService.findById(id);
        if (principal != null && principal.getRole() == Role.ADMIN) {
            hateoasManager.setSelfLinksAdmin(byId);
        } else {
            hateoasManager.setSelfLinksNotAdmin(byId);
        }
        return ResponseEntity.ok(
                byId
        );
    }

    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<GiftCertificateDto> certificateUpdate(@PathVariable Long id, @RequestBody GiftCertificateModel giftCertificateModel) {
        GiftCertificateDto update = giftCertificateReturnService.update(id, giftCertificateModel);
        hateoasManager.setSelfLinksAdmin(update);
        return ResponseEntity.ok(update);
    }

    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<Void> certificateDelete(@PathVariable Long id) {
        giftCertificateReturnService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CollectionModel<GiftCertificateDto>> certificateSearch(
            @RequestParam(required = false, name = "tagNames") String tagNames,
            @RequestParam(required = false) String partName,
            @RequestParam(required = false) String partDescription,
            @RequestParam(required = false, name = "sort") String sort,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
            @AuthenticationPrincipal User principal) {
        log.debug("sort = {}", sort);
        List<GiftCertificateDto> result = giftCertificateReturnService.search(tagNames, partName, partDescription, sort, pageNumber, pageSize);

        CollectionModel<GiftCertificateDto> model = new CollectionModel<>(result);

        if (principal != null && principal.getRole() == Role.ADMIN) {
            hateoasManager.certificateCollectionLinksAdmin(model);
        } else {
            hateoasManager.certificateCollectionLinksNotAdmin(model);
        }

        return ResponseEntity.ok(
                model
        );
    }


}
