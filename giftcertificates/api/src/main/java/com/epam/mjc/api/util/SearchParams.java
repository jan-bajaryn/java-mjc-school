package com.epam.mjc.api.util;

import com.epam.mjc.api.model.sort.SortParams;

public class SearchParams {

    private String tagName;
    private String partName;
    private String partDescription;
    private SortParams sortParams;

    public SearchParams() {
    }

    public SearchParams(String tagName, String partName, String partDescription, SortParams sortParams) {
        this.tagName = tagName;
        this.partName = partName;
        this.partDescription = partDescription;
        this.sortParams = sortParams;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getPartDescription() {
        return partDescription;
    }

    public void setPartDescription(String partDescription) {
        this.partDescription = partDescription;
    }

    public SortParams getSortParams() {
        return sortParams;
    }

    public void setSortParams(SortParams sortParams) {
        this.sortParams = sortParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchParams that = (SearchParams) o;

        if (getTagName() != null ? !getTagName().equals(that.getTagName()) : that.getTagName() != null) return false;
        if (getPartName() != null ? !getPartName().equals(that.getPartName()) : that.getPartName() != null)
            return false;
        if (getPartDescription() != null ? !getPartDescription().equals(that.getPartDescription()) : that.getPartDescription() != null)
            return false;
        return getSortParams() != null ? getSortParams().equals(that.getSortParams()) : that.getSortParams() == null;
    }

    @Override
    public int hashCode() {
        int result = getTagName() != null ? getTagName().hashCode() : 0;
        result = 31 * result + (getPartName() != null ? getPartName().hashCode() : 0);
        result = 31 * result + (getPartDescription() != null ? getPartDescription().hashCode() : 0);
        result = 31 * result + (getSortParams() != null ? getSortParams().hashCode() : 0);
        return result;
    }
}
