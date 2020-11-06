package com.epam.mjc.api.domain;

public class Tag {
    private Long id;
    private String name;


    public static class TagBuilder {
        private Long id;
        private String name;

        TagBuilder() {
        }

        public TagBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public TagBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public Tag build() {
            return new Tag(this.id, this.name);
        }

        @Override
        public String toString() {
            return "Tag.TagBuilder(id=" + this.id + ", name=" + this.name + ")";
        }
    }

    public static TagBuilder builder() {
        return new TagBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (getId() != null ? !getId().equals(tag.getId()) : tag.getId() != null) return false;
        return getName() != null ? getName().equals(tag.getName()) : tag.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tag(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public Tag() {
    }

    public Tag(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }
}
