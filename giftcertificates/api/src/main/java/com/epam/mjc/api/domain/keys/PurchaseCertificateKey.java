package com.epam.mjc.api.domain.keys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PurchaseCertificateKey implements Serializable {
    public static final long serialVersionUID = 1L;

    @Column(name = "gift_certificate_id")
    private Long giftCertificateId;


    @Column(name = "order_id")
    private Long orderId;

    public PurchaseCertificateKey() {
    }

    public PurchaseCertificateKey(Long giftCertificateId, Long orderId) {
        this.giftCertificateId = giftCertificateId;
        this.orderId = orderId;
    }

    public Long getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(Long giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseCertificateKey that = (PurchaseCertificateKey) o;

        if (getGiftCertificateId() != null ? !getGiftCertificateId().equals(that.getGiftCertificateId()) : that.getGiftCertificateId() != null)
            return false;
        return getOrderId() != null ? getOrderId().equals(that.getOrderId()) : that.getOrderId() == null;
    }

    @Override
    public int hashCode() {
        int result = getGiftCertificateId() != null ? getGiftCertificateId().hashCode() : 0;
        result = 31 * result + (getOrderId() != null ? getOrderId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseCertificateKey{" +
                "giftCertificateId=" + giftCertificateId +
                ", orderId=" + orderId +
                '}';
    }
}
