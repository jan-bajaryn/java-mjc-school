package com.epam.mjc.controller;

import com.epam.mjc.api.entity.GiftCertificate;
import com.epam.mjc.api.service.GiftCertificateService;
import com.epam.mjc.model.GiftCertificateModel;
import com.epam.mjc.model.GiftCertificateModelForCreate;
import com.epam.mjc.model.mapper.GiftCertificateMapper;
import com.epam.mjc.model.sort.SortParams;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create")
    public ResponseEntity<GiftCertificate> certificateCreate(
            @RequestBody GiftCertificateModelForCreate giftCertificateModelForCreate
    ) {
        return ResponseEntity.ok(
                giftCertificateService.create(
                        giftCertificateMapper.toGiftCertificate(giftCertificateModelForCreate)
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificate>> certificateShowAll() {
        return ResponseEntity.ok(giftCertificateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> showById(@PathVariable Long id) {
        return ResponseEntity.ok(giftCertificateService.findById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> certificateUpdate(@RequestBody GiftCertificateModel giftCertificateModel) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> certificateDelete(@PathVariable Long id) {
        return ResponseEntity.ok(giftCertificateService.deleteById(id));
    }

    @GetMapping("/show-by-tag-name/{tagName}")
    public ResponseEntity<List<GiftCertificate>> certificateShowByTagName(@PathVariable String tagName) {
        return null;
    }

    @GetMapping("/show-by-part-name/{partName}/{partDescription}")
    public ResponseEntity<List<GiftCertificate>> certificateShowByPartName(
            @PathVariable String partName,
            @PathVariable String partDescription
    ) {
        return null;
    }

    @GetMapping("/sort-by")
    public ResponseEntity<List<GiftCertificate>> certificateSortBy(
            @RequestBody SortParams sortParams
    ) {
        return null;
    }

}
