package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.GiftCertificateController;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.AscDescDto;
import com.epam.mjc.api.model.dto.FieldNameDto;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.service.GiftCertificateReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/certificate")
public class GiftCertificateControllerImpl implements GiftCertificateController {

    private static final Logger log = LoggerFactory.getLogger(GiftCertificateControllerImpl.class);
    private final GiftCertificateReturnService giftCertificateReturnService;


    @Autowired
    public GiftCertificateControllerImpl(GiftCertificateReturnService giftCertificateReturnService) {
        this.giftCertificateReturnService = giftCertificateReturnService;
    }

    @Override
    public ResponseEntity<GiftCertificateDto> certificateCreate(
            @RequestBody GiftCertificateModelForCreate giftCertificateModelForCreate
    ) {
        //
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
    public ResponseEntity<Boolean> certificateUpdate(@RequestBody GiftCertificateModel giftCertificateModel) {
        //
        giftCertificateReturnService.update(giftCertificateModel);
        return ResponseEntity.ok(
                true
        );
    }

    @Override
    public ResponseEntity<Boolean> certificateDelete(@PathVariable Long id) {
        giftCertificateReturnService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<List<GiftCertificateDto>> certificateSearch(
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) String partName,
            @RequestParam(required = false) String partDescription,
            @RequestParam(required = false, name = "sort") String sort
    ) {
        log.debug("sort = {}", sort);
        return ResponseEntity.ok(
                giftCertificateReturnService.search(tagName, partName, partDescription, sort)
        );
    }

}
