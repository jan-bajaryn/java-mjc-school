package com.epam.mjc.api.util;

import com.epam.mjc.api.model.dto.GiftCertificateCollectionDto;
import com.epam.mjc.api.model.dto.GiftCertificateDto;
import com.epam.mjc.api.model.dto.OrderDto;
import com.epam.mjc.api.model.dto.TagDto;
import com.epam.mjc.api.model.dto.UserDto;
import org.springframework.hateoas.CollectionModel;

public interface HateoasManager {
    void setSelfLinksAdmin(UserDto byId);

    void setSelfLinksUser(UserDto byId);

    void setCollectionLinksUserAdmin(CollectionModel<UserDto> model);

    void setSelfLinksAdmin(TagDto byId);

    void tagCollectionLinksNotAdmin(CollectionModel<TagDto> model);

    void selfLinksNotAdmin(TagDto tagDto);

    void collectionLinksAdmin(CollectionModel<TagDto> model);

    void setSelfLinksNotAdmin(GiftCertificateDto byId);

    void setSelfLinksAdmin(GiftCertificateDto byId);

    void certificateCollectionLinksAdmin(GiftCertificateCollectionDto model);

    void certificateCollectionLinksNotAdmin(GiftCertificateCollectionDto model);

    void setSelfLinks(OrderDto byId);

    void setCollectionLinksUser(CollectionModel<OrderDto> model);

    void setCollectionLinksOrderAdmin(CollectionModel<OrderDto> model);
}
