package com.epam.mjc.api.model.dto;

import org.springframework.hateoas.RepresentationModel;

import java.util.Collection;

public class CollectionDto<T> extends RepresentationModel<CollectionDto<T>> {

    private Integer totalPageCount;
    private Collection<T> items;

    public CollectionDto() {
    }

    public CollectionDto(Integer totalPageCount, Collection<T> items) {
        this.totalPageCount = totalPageCount;
        this.items = items;
    }

    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public Collection<T> getItems() {
        return items;
    }

    public void setItems(Collection<T> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CollectionDto<?> that = (CollectionDto<?>) o;

        if (getTotalPageCount() != null ? !getTotalPageCount().equals(that.getTotalPageCount()) : that.getTotalPageCount() != null)
            return false;
        return getItems() != null ? getItems().equals(that.getItems()) : that.getItems() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getTotalPageCount() != null ? getTotalPageCount().hashCode() : 0);
        result = 31 * result + (getItems() != null ? getItems().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CollectionDto{" +
                "totalPageCount=" + totalPageCount +
                ", items=" + items +
                '}';
    }
}
