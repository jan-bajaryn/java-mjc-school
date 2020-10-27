package com.epam.controller;

import com.epam.entity.GiftCertificate;
import com.epam.model.GiftCertificateModel;
import com.epam.model.GiftCertificateModelForCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GiftCertificateController {

    @PutMapping("/certificate/create")
    public ResponseEntity<Boolean> certificateCreate(
            @RequestBody GiftCertificateModelForCreate giftCertificateModelForCreate
    ) {
        return null;
    }

    @GetMapping("/certificate/showAll")
    public ResponseEntity<List<GiftCertificate>> certificateShowAll() {
        return null;
    }

    @PostMapping("/certificate/update")
    public ResponseEntity<Boolean> certificateUpdate(@RequestBody GiftCertificateModel giftCertificateModel) {
        return null;
    }

    @DeleteMapping("/certificate/delete/{id}")
    public ResponseEntity<Boolean> certificateDelete(@PathVariable Integer id) {
        return null;
    }

}
