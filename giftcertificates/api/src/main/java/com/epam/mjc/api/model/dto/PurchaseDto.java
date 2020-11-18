package com.epam.mjc.api.model.dto;

import java.math.BigDecimal;

public class PurchaseDto {
    private Long certificateId;
    private BigDecimal priceForOne;
    private String name;
    private String count;

    public PurchaseDto() {
    }

    public PurchaseDto(Long certificateId, BigDecimal priceForOne, String name, String count) {
        this.certificateId = certificateId;
        this.priceForOne = priceForOne;
        this.name = name;
        this.count = count;
    }

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }

    public BigDecimal getPriceForOne() {
        return priceForOne;
    }

    public void setPriceForOne(BigDecimal priceForOne) {
        this.priceForOne = priceForOne;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseDto that = (PurchaseDto) o;

        if (getCertificateId() != null ? !getCertificateId().equals(that.getCertificateId()) : that.getCertificateId() != null)
            return false;
        if (getPriceForOne() != null ? !getPriceForOne().equals(that.getPriceForOne()) : that.getPriceForOne() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getCount() != null ? getCount().equals(that.getCount()) : that.getCount() == null;
    }

    @Override
    public int hashCode() {
        int result = getCertificateId() != null ? getCertificateId().hashCode() : 0;
        result = 31 * result + (getPriceForOne() != null ? getPriceForOne().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCount() != null ? getCount().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PurchaseDto{" +
                "certificateId=" + certificateId +
                ", priceForOne=" + priceForOne +
                ", name='" + name + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
