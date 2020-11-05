package com.epam.mjc.api.model;

import java.math.BigDecimal;
import java.util.List;

public class GiftCertificateModelForCreate {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private List<TagModel> tags;

    public GiftCertificateModelForCreate() {
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public List<TagModel> getTags() {
        return this.tags;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public void setTags(final List<TagModel> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GiftCertificateModelForCreate)) return false;
        final GiftCertificateModelForCreate other = (GiftCertificateModelForCreate) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$duration = this.getDuration();
        final Object other$duration = other.getDuration();
        if (this$duration == null ? other$duration != null : !this$duration.equals(other$duration)) return false;
        final Object this$tags = this.getTags();
        final Object other$tags = other.getTags();
        if (this$tags == null ? other$tags != null : !this$tags.equals(other$tags)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GiftCertificateModelForCreate;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $duration = this.getDuration();
        result = result * PRIME + ($duration == null ? 43 : $duration.hashCode());
        final Object $tags = this.getTags();
        result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificateModelForCreate(name=" + this.getName() + ", description=" + this.getDescription() + ", price=" + this.getPrice() + ", duration=" + this.getDuration() + ", tags=" + this.getTags() + ")";
    }
}
