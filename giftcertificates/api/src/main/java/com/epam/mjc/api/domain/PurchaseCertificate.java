package com.epam.mjc.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity(name = "gift_certificate_order")
public class PurchaseCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate giftCertificate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "price_for_one")
    private BigDecimal priceForOne;
    @Column(name = "old_name")
    private String oldName;
    @Column(name = "count")
    private Integer count;

    public PurchaseCertificate() {
    }

    public PurchaseCertificate(Long id, GiftCertificate giftCertificate, Order order, BigDecimal priceForOne, String oldName, Integer count) {
        this.id = id;
        this.giftCertificate = giftCertificate;
        this.order = order;
        this.priceForOne = priceForOne;
        this.oldName = oldName;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getPriceForOne() {
        return priceForOne;
    }

    public void setPriceForOne(BigDecimal priceForOne) {
        this.priceForOne = priceForOne;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseCertificate that = (PurchaseCertificate) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getGiftCertificate() != null ? !getGiftCertificate().equals(that.getGiftCertificate()) : that.getGiftCertificate() != null)
            return false;
        if (getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null) return false;
        if (getPriceForOne() != null ? !getPriceForOne().equals(that.getPriceForOne()) : that.getPriceForOne() != null)
            return false;
        if (getOldName() != null ? !getOldName().equals(that.getOldName()) : that.getOldName() != null) return false;
        return getCount() != null ? getCount().equals(that.getCount()) : that.getCount() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getGiftCertificate() != null ? getGiftCertificate().hashCode() : 0);
        result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
        result = 31 * result + (getPriceForOne() != null ? getPriceForOne().hashCode() : 0);
        result = 31 * result + (getOldName() != null ? getOldName().hashCode() : 0);
        result = 31 * result + (getCount() != null ? getCount().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseCertificate{" +
                "id=" + id +
                ", giftCertificate=" + giftCertificate +
                ", order=" + order +
                ", priceForOne=" + priceForOne +
                ", oldName='" + oldName + '\'' +
                ", count=" + count +
                '}';
    }
}

