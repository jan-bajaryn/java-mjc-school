package com.epam.mjc.api.domain.keys;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PurchaseCertificateKey implements Serializable {
    public static final long serialVersionUID = 1L;

//    @Column(name = "gift_certificate_id")
    private Long gift_certificate_id;


//    @Column(name = "order_id")
    private Long order_id;

    public PurchaseCertificateKey() {
    }

    public PurchaseCertificateKey(Long gift_certificate_id, Long order_id) {
        this.gift_certificate_id = gift_certificate_id;
        this.order_id = order_id;
    }

    public Long getGift_certificate_id() {
        return gift_certificate_id;
    }

    public void setGift_certificate_id(Long gift_certificate_id) {
        this.gift_certificate_id = gift_certificate_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseCertificateKey that = (PurchaseCertificateKey) o;

        if (getGift_certificate_id() != null ? !getGift_certificate_id().equals(that.getGift_certificate_id()) : that.getGift_certificate_id() != null)
            return false;
        return getOrder_id() != null ? getOrder_id().equals(that.getOrder_id()) : that.getOrder_id() == null;
    }

    @Override
    public int hashCode() {
        int result = getGift_certificate_id() != null ? getGift_certificate_id().hashCode() : 0;
        result = 31 * result + (getOrder_id() != null ? getOrder_id().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseCertificateKey{" +
                "gift_certificate_id=" + gift_certificate_id +
                ", order_id=" + order_id +
                '}';
    }
}
