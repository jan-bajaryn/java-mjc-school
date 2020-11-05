package com.epam.mjc.api.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class GiftCertificate {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Integer duration;
    private List<Tag> tags;


    public static class GiftCertificateBuilder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private LocalDateTime createDate;
        private LocalDateTime lastUpdateDate;
        private Integer duration;
        private List<Tag> tags;

        GiftCertificateBuilder() {
        }

        public GiftCertificateBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public GiftCertificateBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public GiftCertificateBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public GiftCertificateBuilder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public GiftCertificateBuilder createDate(final LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public GiftCertificateBuilder lastUpdateDate(final LocalDateTime lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
            return this;
        }

        public GiftCertificateBuilder duration(final Integer duration) {
            this.duration = duration;
            return this;
        }

        public GiftCertificateBuilder tags(final List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public GiftCertificate build() {
            return new GiftCertificate(this.id, this.name, this.description, this.price, this.createDate, this.lastUpdateDate, this.duration, this.tags);
        }

        @Override
        public String toString() {
            return "GiftCertificate.GiftCertificateBuilder(id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", price=" + this.price + ", createDate=" + this.createDate + ", lastUpdateDate=" + this.lastUpdateDate + ", duration=" + this.duration + ", tags=" + this.tags + ")";
        }
    }

    public static GiftCertificateBuilder builder() {
        return new GiftCertificateBuilder();
    }

    public Long getId() {
        return this.id;
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

    public LocalDateTime getCreateDate() {
        return this.createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public List<Tag> getTags() {
        return this.tags;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public void setCreateDate(final LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setLastUpdateDate(final LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public void setTags(final List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GiftCertificate)) return false;
        final GiftCertificate other = (GiftCertificate) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$createDate = this.getCreateDate();
        final Object other$createDate = other.getCreateDate();
        if (this$createDate == null ? other$createDate != null : !this$createDate.equals(other$createDate)) return false;
        final Object this$lastUpdateDate = this.getLastUpdateDate();
        final Object other$lastUpdateDate = other.getLastUpdateDate();
        if (this$lastUpdateDate == null ? other$lastUpdateDate != null : !this$lastUpdateDate.equals(other$lastUpdateDate)) return false;
        final Object this$duration = this.getDuration();
        final Object other$duration = other.getDuration();
        if (this$duration == null ? other$duration != null : !this$duration.equals(other$duration)) return false;
        final Object this$tags = this.getTags();
        final Object other$tags = other.getTags();
        if (this$tags == null ? other$tags != null : !this$tags.equals(other$tags)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GiftCertificate;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $createDate = this.getCreateDate();
        result = result * PRIME + ($createDate == null ? 43 : $createDate.hashCode());
        final Object $lastUpdateDate = this.getLastUpdateDate();
        result = result * PRIME + ($lastUpdateDate == null ? 43 : $lastUpdateDate.hashCode());
        final Object $duration = this.getDuration();
        result = result * PRIME + ($duration == null ? 43 : $duration.hashCode());
        final Object $tags = this.getTags();
        result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificate(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", price=" + this.getPrice() + ", createDate=" + this.getCreateDate() + ", lastUpdateDate=" + this.getLastUpdateDate() + ", duration=" + this.getDuration() + ", tags=" + this.getTags() + ")";
    }

    public GiftCertificate() {
    }

    public GiftCertificate(final Long id, final String name, final String description, final BigDecimal price, final LocalDateTime createDate, final LocalDateTime lastUpdateDate, final Integer duration, final List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
        this.tags = tags;
    }
}
