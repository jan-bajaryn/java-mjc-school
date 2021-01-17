package com.epam.mjc.api.util.sort;

import com.epam.mjc.api.domain.GiftCertificate_;

public enum FieldName {
    NAME(GiftCertificate_.NAME), LAST_UPDATE(GiftCertificate_.LAST_UPDATE_DATE);

    private final String columnName;

    FieldName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
