package com.epam.mjc.api.model.sort;

import java.util.List;

public class SortParams {
    private List<SortParam> sortParams;

    public SortParams() {
    }

    public List<SortParam> getSortParams() {
        return this.sortParams;
    }

    public void setSortParams(final List<SortParam> sortParams) {
        this.sortParams = sortParams;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SortParams)) return false;
        final SortParams other = (SortParams) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$sortParams = this.getSortParams();
        final Object other$sortParams = other.getSortParams();
        if (this$sortParams == null ? other$sortParams != null : !this$sortParams.equals(other$sortParams)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SortParams;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $sortParams = this.getSortParams();
        result = result * PRIME + ($sortParams == null ? 43 : $sortParams.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "SortParams(sortParams=" + this.getSortParams() + ")";
    }
}
