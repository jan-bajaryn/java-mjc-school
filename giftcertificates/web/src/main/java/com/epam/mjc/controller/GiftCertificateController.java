package com.epam.mjc.controller;

import com.epam.mjc.api.entity.GiftCertificate;
import com.epam.mjc.model.GiftCertificateModel;
import com.epam.mjc.model.GiftCertificateModelForCreate;
import com.epam.mjc.model.sort.SortParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GiftCertificateController {

    @PostMapping("/certificate/create")
    public ResponseEntity<Boolean> certificateCreate(
            @RequestBody GiftCertificateModelForCreate giftCertificateModelForCreate
    ) {
        return null;
    }

    @GetMapping("/certificate/showAll")
    public ResponseEntity<List<GiftCertificate>> certificateShowAll() {
        return null;
    }

    @PutMapping("/certificate/update")
    public ResponseEntity<Boolean> certificateUpdate(@RequestBody GiftCertificateModel giftCertificateModel) {
        return null;
    }

    @DeleteMapping("/certificate/delete/{id}")
    public ResponseEntity<Boolean> certificateDelete(@PathVariable Integer id) {
        return null;
    }

    @GetMapping("/certificate/show-by-tag-name/{tagName}")
    public ResponseEntity<List<GiftCertificate>> certificateShowByTagName(@PathVariable String tagName) {
        return null;
    }

    @GetMapping("/certificate/show-by-part-name/{partName}/{partDescription}")
    public ResponseEntity<List<GiftCertificate>> certificateShowByPartName(
            @PathVariable String partName,
            @PathVariable String partDescription
    ) {
        return null;
    }

    @GetMapping("/certificate/sort-by")
    public ResponseEntity<List<GiftCertificate>> certificateSortBy(
            @RequestBody SortParams sortParams
    ) {
        return null;
    }

}
