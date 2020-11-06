package com.epam.mjc.core.controller.errhandle;

public enum ErrorCodes {
    TAG_VALIDATOR("1"), GIFT_VALIDATOR("2"), REMAIN_CODE("0"), GIFT_DUPLICATE("3"), GIFT_NOT_FOUND("4"), TAG_DUPLICATE("5"), TAG_NOT_FOUND("6");
    private final String code;

    ErrorCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
