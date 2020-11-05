package com.epam.mjc.core.controller.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class GiftCertificateModelForCreate {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    private List<TagModel> tags;

}
