package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.GiftCertificateController;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateCollectionDto;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.service.GiftCertificateReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certificates")
@CrossOrigin("*")
public class GiftCertificateControllerImpl implements GiftCertificateController {


    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PAGE_SIZE = "5";


    private static final Logger log = LoggerFactory.getLogger(GiftCertificateControllerImpl.class);

    private final GiftCertificateReturnService giftCertificateReturnService;


    @Autowired
    public GiftCertificateControllerImpl(GiftCertificateReturnService giftCertificateReturnService) {
        this.giftCertificateReturnService = giftCertificateReturnService;
    }

    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<GiftCertificateDto> certificateCreate(
            @RequestBody GiftCertificateModelForCreate giftCertificateModelForCreate
    ) {
        return new ResponseEntity<>(
                giftCertificateReturnService.create(giftCertificateModelForCreate),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<GiftCertificateDto> showById(@PathVariable Long id) {
        return ResponseEntity.ok(
                giftCertificateReturnService.findById(id)
        );
    }

    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<GiftCertificateDto> certificateUpdate(@PathVariable Long id, @RequestBody GiftCertificateModel giftCertificateModel) {
        return ResponseEntity.ok(giftCertificateReturnService.update(id, giftCertificateModel));
    }

    @Override
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public ResponseEntity<Void> certificateDelete(@PathVariable Long id) {
        giftCertificateReturnService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<GiftCertificateCollectionDto> certificateSearch(
            @RequestParam(required = false, name = "tagNames") String tagNames,
            @RequestParam(required = false) String partName,
            @RequestParam(required = false) String partDescription,
            @RequestParam(required = false, name = "sort") String sort,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {

        return ResponseEntity.ok(
                giftCertificateReturnService.search(tagNames, partName, partDescription, sort, pageNumber, pageSize)
        );
    }

}
