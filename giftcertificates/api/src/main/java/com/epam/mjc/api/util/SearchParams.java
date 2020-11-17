package com.epam.mjc.api.util;

import com.epam.mjc.api.util.sort.SortParams;

import java.util.List;

public class SearchParams {

    private List<String> tagNames;
    private String partName;
    private String partDescription;
    private SortParams sortParams;

    public SearchParams() {
    }

    public SearchParams(List<String> tagNames, String partName, String partDescription, SortParams sortParams) {
        this.tagNames = tagNames;
        this.partName = partName;
        this.partDescription = partDescription;
        this.sortParams = sortParams;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
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

        if (getTagNames() != null ? !getTagNames().equals(that.getTagNames()) : that.getTagNames() != null)
            return false;
        if (getPartName() != null ? !getPartName().equals(that.getPartName()) : that.getPartName() != null)
            return false;
        if (getPartDescription() != null ? !getPartDescription().equals(that.getPartDescription()) : that.getPartDescription() != null)
            return false;
        return getSortParams() != null ? getSortParams().equals(that.getSortParams()) : that.getSortParams() == null;
    }

    @Override
    public int hashCode() {
        int result = getTagNames() != null ? getTagNames().hashCode() : 0;
        result = 31 * result + (getPartName() != null ? getPartName().hashCode() : 0);
        result = 31 * result + (getPartDescription() != null ? getPartDescription().hashCode() : 0);
        result = 31 * result + (getSortParams() != null ? getSortParams().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SearchParams{" +
                "tagNames=" + tagNames +
                ", partName='" + partName + '\'' +
                ", partDescription='" + partDescription + '\'' +
                ", sortParams=" + sortParams +
                '}';
    }
}
