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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GiftCertificate that = (GiftCertificate) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(that.getPrice()) : that.getPrice() != null) return false;
        if (getCreateDate() != null ? !getCreateDate().equals(that.getCreateDate()) : that.getCreateDate() != null)
            return false;
        if (getLastUpdateDate() != null ? !getLastUpdateDate().equals(that.getLastUpdateDate()) : that.getLastUpdateDate() != null)
            return false;
        if (getDuration() != null ? !getDuration().equals(that.getDuration()) : that.getDuration() != null)
            return false;
        return getTags() != null ? getTags().equals(that.getTags()) : that.getTags() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
        result = 31 * result + (getLastUpdateDate() != null ? getLastUpdateDate().hashCode() : 0);
        result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
        result = 31 * result + (getTags() != null ? getTags().hashCode() : 0);
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
