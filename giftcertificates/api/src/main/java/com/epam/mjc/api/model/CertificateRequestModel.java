package com.epam.mjc.api.model;

public class CertificateRequestModel {
    private Long id;
    private String name;
    private Integer count = 1;

    public CertificateRequestModel() {
    }

    public CertificateRequestModel(Long id, String name, Integer count) {
        this.id = id;
        this.name = name;
        this.count = count;
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

        CertificateRequestModel that = (CertificateRequestModel) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getCount() != null ? getCount().equals(that.getCount()) : that.getCount() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getCount() != null ? getCount().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CertificateRequestModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
