package com.epam.mjc.core.controller;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.sort.SortParams;
import com.epam.mjc.api.service.GiftCertificateService;
import com.epam.mjc.api.util.SearchParams;
import com.epam.mjc.core.controller.mapper.GiftCertificateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateMapper giftCertificateMapper;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, GiftCertificateMapper giftCertificateMapper) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateMapper = giftCertificateMapper;
    }

    @PostMapping
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

//    @GetMapping
//    public ResponseEntity<List<GiftCertificate>> certificateShowAll() {
//        return ResponseEntity.ok(giftCertificateService.findAll());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> showById(@PathVariable Long id) {
        return ResponseEntity.ok(giftCertificateService.findById(id));
    }

    @PutMapping
    public ResponseEntity<Boolean> certificateUpdate(@RequestBody GiftCertificateModel giftCertificateModel) {
        return ResponseEntity.ok(
                giftCertificateService.update(
                        giftCertificateMapper.toGiftCertificate(giftCertificateModel)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> certificateDelete(@PathVariable Long id) {
        return ResponseEntity.ok(giftCertificateService.deleteById(id));
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificate>> certificateSearch(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String partName,
            @RequestParam(required = false) String partDescription,
            @RequestParam(required = false) SortParams sortParams
    ) {
        return ResponseEntity.ok(giftCertificateService.search(new SearchParams(tagName,partName,partDescription,sortParams)));
    }

}
