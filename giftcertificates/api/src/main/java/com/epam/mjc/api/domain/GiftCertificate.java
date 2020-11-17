package com.epam.mjc.api.domain;

import com.epam.mjc.api.dao.audit.AuditGiftCertificate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "gift_certificate")
@EntityListeners(AuditGiftCertificate.class)
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "createdate")
    private LocalDateTime createDate;
    @Column(name = "lastupdatedate")
    private LocalDateTime lastUpdateDate;
    @Column(name = "duration")
    private Integer duration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "giftCertificates")
    private List<Order> orders = new ArrayList<>();

    public GiftCertificate() {
    }

    public GiftCertificate(Long id, String name, String description, BigDecimal price, LocalDateTime createDate, LocalDateTime lastUpdateDate, Integer duration, List<Tag> tags, List<Order> orders) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
        this.tags = tags;
        this.orders = orders;
    }

    private GiftCertificate(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setDescription(builder.description);
        setPrice(builder.price);
        setCreateDate(builder.createDate);
        setLastUpdateDate(builder.lastUpdateDate);
        setDuration(builder.duration);
        setTags(builder.tags);
        setOrders(builder.orders);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(GiftCertificate copy) {
        Builder builder = new Builder();
        builder.id = copy.getId();
        builder.name = copy.getName();
        builder.description = copy.getDescription();
        builder.price = copy.getPrice();
        builder.createDate = copy.getCreateDate();
        builder.lastUpdateDate = copy.getLastUpdateDate();
        builder.duration = copy.getDuration();
        builder.tags = copy.getTags();
        builder.orders = copy.getOrders();
        return builder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
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
        return getDuration() != null ? getDuration().equals(that.getDuration()) : that.getDuration() == null;
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
        return result;
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", duration=" + duration +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private LocalDateTime createDate;
        private LocalDateTime lastUpdateDate;
        private Integer duration;
        private List<Tag> tags;
        private List<Order> orders;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder price(BigDecimal val) {
            price = val;
            return this;
        }

        public Builder createDate(LocalDateTime val) {
            createDate = val;
            return this;
        }

        public Builder lastUpdateDate(LocalDateTime val) {
            lastUpdateDate = val;
            return this;
        }

        public Builder duration(Integer val) {
            duration = val;
            return this;
        }

        public Builder tags(List<Tag> val) {
            tags = val;
            return this;
        }

        public Builder orders(List<Order> val) {
            orders = val;
            return this;
        }

        public GiftCertificate build() {
            return new GiftCertificate(this);
        }
    }
}
