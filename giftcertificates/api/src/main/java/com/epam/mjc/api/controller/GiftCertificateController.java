package com.epam.mjc.api.controller;

import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

public interface GiftCertificateController {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<GiftCertificateDto> certificateCreate(
            @RequestBody GiftCertificateModelForCreate giftCertificateModelForCreate
    );

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<GiftCertificateDto> showById(@PathVariable Long id);

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Boolean> certificateUpdate(@RequestBody GiftCertificateModel giftCertificateModel);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Boolean> certificateDelete(@PathVariable Long id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<GiftCertificateDto>> certificateSearch(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String partName,
            @RequestParam(required = false) String partDescription,
            @RequestParam(required = false) String sort
    );
}
