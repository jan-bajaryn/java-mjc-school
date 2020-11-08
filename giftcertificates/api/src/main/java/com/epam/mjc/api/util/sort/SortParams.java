package com.epam.mjc.api.util.sort;

import java.util.ArrayList;
import java.util.List;

public class SortParams {
    private List<SortParam> sortParams = new ArrayList<>();

    public SortParams() {
    }

    public SortParams(List<SortParam> sortParams) {
        this.sortParams = sortParams;
    }

    public List<SortParam> getSortParams() {
        return this.sortParams;
    }

    public void setSortParams(final List<SortParam> sortParams) {
        this.sortParams = sortParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SortParams that = (SortParams) o;

        return getSortParams() != null ? getSortParams().equals(that.getSortParams()) : that.getSortParams() == null;
    }

    @Override
    public int hashCode() {
        return getSortParams() != null ? getSortParams().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SortParams(sortParams=" + this.getSortParams() + ")";
    }
}
