package com.epam.repo.rowMapper;

import com.epam.entity.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

    private static final String ID = "id";
    private static final String PRICE = "price";
    private static final String DESCRIPTION = "description";
    private static final String CREATE_DATE = "createDate";
    private static final String LAST_UPDATE_DATE = "lastUpdateDate";
    private static final String DURATION = "duration";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificate.builder()
                .id(rs.getLong(ID))
                .price(rs.getBigDecimal(PRICE))
                .description(rs.getString(DESCRIPTION))
                .createDate(rs.getTimestamp(CREATE_DATE).toLocalDateTime())
                .lastUpdateDate(rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime())
                .duration(rs.getInt(DURATION))
                .build();

    }
}
