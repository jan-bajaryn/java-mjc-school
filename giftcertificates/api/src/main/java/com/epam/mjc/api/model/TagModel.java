package com.epam.mjc.api.model;

public class TagModel {
    private String name;

    public TagModel() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagModel tagModel = (TagModel) o;

        return getName() != null ? getName().equals(tagModel.getName()) : tagModel.getName() == null;
    }

    @Override
    public int hashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TagModel(name=" + this.getName() + ")";
    }
}
