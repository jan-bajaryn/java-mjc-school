package com.epam.mjc.core.controller;

import com.epam.mjc.api.controller.GiftCertificateController;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.service.GiftCertificateReturnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/certificates")
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
    public ResponseEntity<GiftCertificateDto> certificateCreate(
            @RequestBody GiftCertificateModelForCreate giftCertificateModelForCreate
    ) {
        GiftCertificateDto result = giftCertificateReturnService.create(giftCertificateModelForCreate);
        setSelfLinks(result);
        return new ResponseEntity<>(
                result,
                HttpStatus.CREATED
        );
    }

    @Override
    public ResponseEntity<GiftCertificateDto> showById(@PathVariable Long id) {
        GiftCertificateDto byId = giftCertificateReturnService.findById(id);
        setSelfLinks(byId);
        return ResponseEntity.ok(
                byId
        );
    }

    @Override
    public ResponseEntity<Void> certificateUpdate(@PathVariable Long id, @RequestBody GiftCertificateModel giftCertificateModel) {
        giftCertificateReturnService.update(id, giftCertificateModel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> certificateDelete(@PathVariable Long id) {
        giftCertificateReturnService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<CollectionModel<GiftCertificateDto>> certificateSearch(
            @RequestParam(required = false, name = "tagNames") String tagNames,
            @RequestParam(required = false) String partName,
            @RequestParam(required = false) String partDescription,
            @RequestParam(required = false, name = "sort") String sort,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer pageNumber,
            @RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize
    ) {
        log.debug("sort = {}", sort);
        List<GiftCertificateDto> result = giftCertificateReturnService.search(tagNames, partName, partDescription, sort, pageNumber, pageSize);

        for (GiftCertificateDto giftCertificateDto : result) {
            setSelfLinks(giftCertificateDto);
        }

        CollectionModel<GiftCertificateDto> model = new CollectionModel<>(result);

        model.add(linkTo(GiftCertificateControllerImpl.class).withSelfRel());
        model.add(
                linkTo(methodOn(GiftCertificateControllerImpl.class)
                        .certificateCreate(new GiftCertificateModelForCreate()))
                        .withRel("create")
        );

        return ResponseEntity.ok(
                model
        );
    }

    private void setSelfLinks(GiftCertificateDto byId) {
        Link selfLink = linkTo(methodOn(GiftCertificateControllerImpl.class)
                .showById(byId.getId())).withSelfRel();
        Link delete = linkTo(methodOn(GiftCertificateControllerImpl.class)
                .certificateDelete(byId.getId())).withRel("delete");
        Link update = linkTo(methodOn(GiftCertificateControllerImpl.class)
                .certificateUpdate(byId.getId(), new GiftCertificateModel())).withRel("update");
        byId.add(selfLink, delete, update);
    }

}
