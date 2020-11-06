package com.epam.mjc.api.util.sort;

public enum FieldName {
    NAME("name"), LAST_UPDATE("lastUpdateDate");

    private final String columnName;

    FieldName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }
}
