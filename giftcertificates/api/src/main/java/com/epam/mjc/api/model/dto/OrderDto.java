package com.epam.mjc.api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDate;
    private BigDecimal price;
    private UserDto user;
    private List<PurchaseDto> purchases;

    public OrderDto() {
    }

    public OrderDto(Long id, LocalDateTime createDate, BigDecimal price, UserDto user, List<PurchaseDto> purchases) {
        this.id = id;
        this.createDate = createDate;
        this.price = price;
        this.user = user;
        this.purchases = purchases;
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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<PurchaseDto> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseDto> purchases) {
        this.purchases = purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDto orderDto = (OrderDto) o;

        if (getId() != null ? !getId().equals(orderDto.getId()) : orderDto.getId() != null) return false;
        if (getCreateDate() != null ? !getCreateDate().equals(orderDto.getCreateDate()) : orderDto.getCreateDate() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(orderDto.getPrice()) : orderDto.getPrice() != null) return false;
        if (getUser() != null ? !getUser().equals(orderDto.getUser()) : orderDto.getUser() != null) return false;
        return getPurchases() != null ? getPurchases().equals(orderDto.getPurchases()) : orderDto.getPurchases() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getCreateDate() != null ? getCreateDate().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        result = 31 * result + (getPurchases() != null ? getPurchases().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", price=" + price +
                ", user=" + user +
                ", purchases=" + purchases +
                '}';
    }
}

