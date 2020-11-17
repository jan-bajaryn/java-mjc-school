package com.epam.mjc.api.model;

import java.util.List;

public class OrderForCreate {
    private Long userId;
    private List<Long> giftCertificatesIds;


    public OrderForCreate() {
    }

    public OrderForCreate(Long userId, List<Long> giftCertificatesIds) {
        this.userId = userId;
        this.giftCertificatesIds = giftCertificatesIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getGiftCertificatesIds() {
        return giftCertificatesIds;
    }

    public void setGiftCertificatesIds(List<Long> giftCertificatesIds) {
        this.giftCertificatesIds = giftCertificatesIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderForCreate that = (OrderForCreate) o;

        if (getUserId() != null ? !getUserId().equals(that.getUserId()) : that.getUserId() != null) return false;
        return getGiftCertificatesIds() != null ? getGiftCertificatesIds().equals(that.getGiftCertificatesIds()) : that.getGiftCertificatesIds() == null;
    }

    @Override
    public int hashCode() {
        int result = getUserId() != null ? getUserId().hashCode() : 0;
        result = 31 * result + (getGiftCertificatesIds() != null ? getGiftCertificatesIds().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderForCreate{" +
                "userId=" + userId +
                ", giftCertificatesIds=" + giftCertificatesIds +
                '}';
    }
}
