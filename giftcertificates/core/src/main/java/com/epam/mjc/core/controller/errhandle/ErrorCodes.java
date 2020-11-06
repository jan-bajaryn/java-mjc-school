package com.epam.mjc.core.controller.errhandle;

public enum ErrorCodes {
    TAG_CODE("1"), GIFT_CODE("2"), REMAIN_CODE("0");
    private final String code;

    ErrorCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
