package com.epam.mjc.api.util.sort;

public class SortParam {
    private FieldName fieldName;
    private boolean asc;

    public SortParam() {
    }

    public SortParam(FieldName fieldName, boolean asc) {
        this.fieldName = fieldName;
        this.asc = asc;
    }

    public FieldName getFieldName() {
        return this.fieldName;
    }

    public boolean isAsc() {
        return this.asc;
    }

    public void setFieldName(final FieldName fieldName) {
        this.fieldName = fieldName;
    }

    public void setAsc(final boolean asc) {
        this.asc = asc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SortParam sortParam = (SortParam) o;

        if (isAsc() != sortParam.isAsc()) return false;
        return getFieldName() == sortParam.getFieldName();
    }

    @Override
    public int hashCode() {
        int result = getFieldName() != null ? getFieldName().hashCode() : 0;
        result = 31 * result + (isAsc() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SortParam(fieldName=" + this.getFieldName() + ", asc=" + this.isAsc() + ")";
    }
}
