package com.epam.mjc.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "createdate")
    private LocalDateTime createDate;

    @Column(name = "price")
    private BigDecimal price;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<PurchaseCertificate> purchaseCertificates = new ArrayList<>();

    public Order() {
    }

    public Order(Long id, LocalDateTime createDate, BigDecimal price, User user, List<PurchaseCertificate> purchaseCertificates) {
        this.id = id;
        this.createDate = createDate;
        this.price = price;
        this.user = user;
        this.purchaseCertificates = purchaseCertificates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PurchaseCertificate> getPurchaseCertificates() {
        return purchaseCertificates;
    }

    public void setPurchaseCertificates(List<PurchaseCertificate> purchaseCertificates) {
        this.purchaseCertificates = purchaseCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (getId() != null ? !getId().equals(order.getId()) : order.getId() != null) return false;
        if (getCreateDate() != null ? !getCreateDate().equals(order.getCreateDate()) : order.getCreateDate() != null)
            return false;
        return getPrice() != null ? getPrice().equals(order.getPrice()) : order.getPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", price=" + price +
                '}';
    }
}
