package com.epam.mjc.core.service.mapper;

import com.epam.mjc.api.service.exception.WrongQuerySortException;
import com.epam.mjc.api.service.mapper.SortMapper;
import com.epam.mjc.api.util.sort.FieldName;
import com.epam.mjc.api.util.sort.SortParam;
import com.epam.mjc.api.util.sort.SortParams;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SortMapperImpl implements SortMapper {

    private static final String ASC = "asc";
    private static final String DESC = "desc";
    private static final String COMMA = ",";
    private static final String COLON = ":";
    private static final int ARR_LENGTH = 2;

    @Override
    public SortParams toSortParams(String sort) {
        if (sort == null || sort.isEmpty()) {
            return null;
        }

        List<SortParam> collect = collectSortParams(sort);

        return new SortParams(collect);
    }

    private List<SortParam> collectSortParams(String sort) {
        return Arrays.stream(sort.split(COMMA))
                .map(s -> s.split(COLON))
                .peek(arr -> {
                    if (arr.length != ARR_LENGTH) {
                        throw new WrongQuerySortException("sort.wrong-sort", sort);
                    }
                })
                .map(arr -> {
                    try {
                        FieldName fieldName = FieldName.valueOf(arr[0]);
                        boolean isAsc = takeIsAsc(arr[1]);
                        return new SortParam(fieldName, isAsc);
                    } catch (IllegalArgumentException e) {
                        throw new WrongQuerySortException("sort.wrong-sort", sort);
                    }
                })
                .collect(Collectors.toList());
    }

    private boolean takeIsAsc(String s) {
        if (s.equals(ASC)) {
            return true;
        }
        if (s.equals(DESC)) {
            return false;
        }
        throw new WrongQuerySortException("sort.wrong-sort.asc", s);
    }

}
