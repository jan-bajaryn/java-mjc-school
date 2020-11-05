package com.epam.mjc.api.model.sort;

public class SortParam {
    private FieldName fieldName;
    private boolean asc;

    public SortParam() {
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
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SortParam)) return false;
        final SortParam other = (SortParam) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$fieldName = this.getFieldName();
        final Object other$fieldName = other.getFieldName();
        if (this$fieldName == null ? other$fieldName != null : !this$fieldName.equals(other$fieldName)) return false;
        if (this.isAsc() != other.isAsc()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SortParam;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $fieldName = this.getFieldName();
        result = result * PRIME + ($fieldName == null ? 43 : $fieldName.hashCode());
        result = result * PRIME + (this.isAsc() ? 79 : 97);
        return result;
    }

    @Override
    public String toString() {
        return "SortParam(fieldName=" + this.getFieldName() + ", asc=" + this.isAsc() + ")";
    }
}
