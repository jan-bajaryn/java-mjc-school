package com.epam.model;

import com.epam.model.sort.FieldName;
import lombok.Data;

@Data
public class SortParam {

    private FieldName fieldName;
    private boolean asc;

}
