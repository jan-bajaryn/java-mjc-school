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
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Tag)) return false;
        final Tag other = (Tag) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Tag;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
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
