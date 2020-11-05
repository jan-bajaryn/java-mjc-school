package com.epam.mjc.api.model.sort;

import lombok.Data;

@Data
public class SortParam {

    private FieldName fieldName;
    private boolean asc;

}
