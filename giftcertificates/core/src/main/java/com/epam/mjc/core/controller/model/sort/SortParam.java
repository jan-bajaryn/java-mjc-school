package com.epam.mjc.core.controller.model.sort;

import lombok.Data;

@Data
public class SortParam {

    private FieldName fieldName;
    private boolean asc;

}
