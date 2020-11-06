package com.epam.mjc.api.controller;

import com.epam.mjc.api.domain.GiftCertificate;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.util.sort.SortParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GiftCertificateController {
    @PostMapping
    ResponseEntity<GiftCertificate> certificateCreate(
            @RequestBody GiftCertificateModelForCreate giftCertificateModelForCreate
    );

    @GetMapping("/{id}")
    ResponseEntity<GiftCertificate> showById(@PathVariable Long id);

    @PatchMapping
    ResponseEntity<Boolean> certificateUpdate(@RequestBody GiftCertificateModel giftCertificateModel);

    @DeleteMapping("/{id}")
    ResponseEntity<Boolean> certificateDelete(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<GiftCertificate>> certificateSearch(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String partName,
            @RequestParam(required = false) String partDescription,
            @RequestParam(required = false) SortParams sortParams
    );
}
