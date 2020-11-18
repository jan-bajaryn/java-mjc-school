package com.epam.mjc.api.model;

import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

public class OrderForCreate {
    private Long userId;
    private List<CertificateRequestModel> giftCertificates;


    public OrderForCreate() {
    }

    public OrderForCreate(Long userId, List<CertificateRequestModel> giftCertificates) {
        this.userId = userId;
        this.giftCertificates = giftCertificates;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CertificateRequestModel> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(List<CertificateRequestModel> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderForCreate that = (OrderForCreate) o;

        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        return getGiftCertificates() != null ? getGiftCertificates().equals(that.getGiftCertificates()) : that.getGiftCertificates() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + (getGiftCertificates() != null ? getGiftCertificates().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderForCreate{" +
                "userId=" + userId +
                ", giftCertificates=" + giftCertificates +
                '}';
    }
}
