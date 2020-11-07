package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.GiftCertificateController;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.service.GiftCertificateReturnService;
import com.epam.mjc.api.util.SearchParams;
import com.epam.mjc.api.util.sort.SortParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/certificate")
public class GiftCertificateControllerImpl implements GiftCertificateController {

//    private final GiftCertificateService giftCertificateService;
//    private final GiftCertificateMapperImpl giftCertificateMapper;
//    private final GiftCertificateDtoMapper giftCertificateDtoMapper;

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
            @RequestParam(required = false) SortParams sortParams
    ) {
        //
        return ResponseEntity.ok(
                giftCertificateReturnService.search(new SearchParams(tagName, partName, partDescription, sortParams))
        );
    }

}
