package com.epam.mjc.core.util;

import com.epam.mjc.api.controller.UserController;
import com.epam.mjc.api.model.GiftCertificateModel;
import com.epam.mjc.api.model.GiftCertificateModelForCreate;
import com.epam.mjc.api.model.TagForCreate;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.model.dto.TagDto;
import com.epam.mjc.api.model.dto.UserDto;
import com.epam.mjc.api.util.HateoasManager;
import com.epam.mjc.core.controller.GiftCertificateControllerImpl;
import com.epam.mjc.core.controller.TagControllerImpl;
import com.epam.mjc.core.controller.UserControllerImpl;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasManagerImpl implements HateoasManager {

    @Override
    public void setSelfLinksAdmin(UserDto byId) {
        Link selfLink = linkTo(methodOn(UserControllerImpl.class)
                .findById(byId.getId())).withSelfRel();
        byId.add(selfLink);
    }

    @Override
    public void setSelfLinksUser(UserDto byId) {
        Link selfLink = linkTo(methodOn(UserController.class)
                .findAll(null, null, null)).withSelfRel();
        byId.add(selfLink);
    }

    @Override
    public void setCollectionLinksAdmin(CollectionModel<UserDto> model) {
        for (UserDto userDto : model) {
            setSelfLinksAdmin(userDto);
        }
        model.add(linkTo(UserControllerImpl.class).withSelfRel());
    }

    @Override
    public void setSelfLinksAdmin(TagDto byId) {
        Link selfLink = linkTo(methodOn(TagControllerImpl.class)
                .showById(byId.getId(), null)).withSelfRel();
        byId.add(selfLink);
        Link delete = linkTo(methodOn(TagControllerImpl.class)
                .tagDelete(byId.getId())).withRel("delete");
        byId.add(delete);
    }


    @Override
    public void tagCollectionLinksNotAdmin(CollectionModel<TagDto> model) {

        for (TagDto tagDto : model) {
            selfLinksNotAdmin(tagDto);
        }

        Link self = linkTo(TagControllerImpl.class).withRel("tags");
        Link rich = linkTo(methodOn(TagControllerImpl.class)
                .getRich(null)).withRel("popular");
        model.add(self, rich);
    }

    @Override
    public void selfLinksNotAdmin(TagDto tagDto) {
        Link selfLink = linkTo(methodOn(TagControllerImpl.class)
                .showById(tagDto.getId(), null)).withSelfRel();
        tagDto.add(selfLink);
    }

    @Override
    public void collectionLinksAdmin(CollectionModel<TagDto> model) {

        for (TagDto tagDto : model) {
            setSelfLinksAdmin(tagDto);
        }

        Link create = linkTo(methodOn(TagControllerImpl.class)
                .tagCreate(new TagForCreate())).withRel("create");
        Link self = linkTo(TagControllerImpl.class).withRel("tags");
        Link rich = linkTo(methodOn(TagControllerImpl.class)
                .getRich(null)).withRel("popular");
        model.add(self, rich, create);
    }

    @Override
    public void setSelfLinksNotAdmin(GiftCertificateDto byId) {
        for (TagDto tag : byId.getTags()) {
            selfLinksNotAdmin(tag);
        }
        Link selfLink = linkTo(methodOn(GiftCertificateControllerImpl.class)
                .showById(byId.getId(), null)).withSelfRel();
        byId.add(selfLink);
    }

    @Override
    public void setSelfLinksAdmin(GiftCertificateDto byId) {
        for (TagDto tag : byId.getTags()) {
            selfLinksNotAdmin(tag);
        }
        Link selfLink = linkTo(methodOn(GiftCertificateControllerImpl.class)
                .showById(byId.getId(), null)).withSelfRel();
        Link delete = linkTo(methodOn(GiftCertificateControllerImpl.class)
                .certificateDelete(byId.getId())).withRel("delete");
        Link update = linkTo(methodOn(GiftCertificateControllerImpl.class)
                .certificateUpdate(byId.getId(), new GiftCertificateModel())).withRel("update");
        byId.add(selfLink, delete, update);
    }

    @Override
    public void certificateCollectionLinksAdmin(CollectionModel<GiftCertificateDto> model) {
        for (GiftCertificateDto giftCertificateDto : model) {
            setSelfLinksAdmin(giftCertificateDto);
        }
        model.add(linkTo(GiftCertificateControllerImpl.class).withSelfRel());
        model.add(
                linkTo(methodOn(GiftCertificateControllerImpl.class)
                        .certificateCreate(new GiftCertificateModelForCreate()))
                        .withRel("create")
        );
    }

    @Override
    public void certificateCollectionLinksNotAdmin(CollectionModel<GiftCertificateDto> model) {
        for (GiftCertificateDto giftCertificateDto : model) {
            setSelfLinksNotAdmin(giftCertificateDto);
        }
        model.add(linkTo(GiftCertificateControllerImpl.class).withSelfRel());
    }
}
