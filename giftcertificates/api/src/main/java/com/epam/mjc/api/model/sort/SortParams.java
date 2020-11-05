package com.epam.mjc.api.model.sort;

import lombok.Data;

import java.util.List;

@Data
public class SortParams {
    private List<SortParam> sortParams;
}
