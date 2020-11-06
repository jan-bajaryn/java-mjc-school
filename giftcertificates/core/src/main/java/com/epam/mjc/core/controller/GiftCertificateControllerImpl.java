package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.GiftCertificateController;
import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.util.sort.SortParams;
import com.epam.mjc.api.service.GiftCertificateService;
import com.epam.mjc.api.util.SearchParams;
import com.epam.mjc.core.controller.mapper.GiftCertificateMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate")
public class GiftCertificateControllerImpl implements GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateMapperImpl giftCertificateMapper;

    @Autowired
    public GiftCertificateControllerImpl(GiftCertificateService giftCertificateService, GiftCertificateMapperImpl giftCertificateMapper) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @Override
    public ResponseEntity<GiftCertificate> certificateCreate(
            @RequestBody GiftCertificateModelForCreate giftCertificateModelForCreate
    ) {
        return new ResponseEntity<>(
                giftCertificateService.create(
                        giftCertificateMapper.toGiftCertificate(giftCertificateModelForCreate)
                ),
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<GiftCertificate> showById(@PathVariable Long id) {
        return ResponseEntity.ok(giftCertificateService.findById(id));
    }

    @Override
    public ResponseEntity<Boolean> certificateUpdate(@RequestBody GiftCertificateModel giftCertificateModel) {
        return ResponseEntity.ok(
                giftCertificateService.update(
                        giftCertificateMapper.toGiftCertificate(giftCertificateModel)
                )
        );
    }

    @Override
    public ResponseEntity<Boolean> certificateDelete(@PathVariable Long id) {
        return ResponseEntity.ok(giftCertificateService.deleteById(id));
    }

    @Override
    public ResponseEntity<List<GiftCertificate>> certificateSearch(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String partName,
            @RequestParam(required = false) String partDescription,
            @RequestParam(required = false) SortParams sortParams
    ) {
        return ResponseEntity.ok(giftCertificateService.search(new SearchParams(tagName,partName,partDescription,sortParams)));
    }

}
