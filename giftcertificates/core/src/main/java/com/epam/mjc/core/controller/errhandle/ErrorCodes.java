package com.epam.mjc.core.controller.errhandle;

public enum ErrorCodes {
    TAG_VALIDATOR("1"),
    GIFT_VALIDATOR("2"),
    REMAIN_CODE("0"),
    GIFT_DUPLICATE("3"),
    GIFT_NOT_FOUND("4"),
    TAG_DUPLICATE("5"),
    TAG_NOT_FOUND("6"),
    WRONG_SORT("7"),
    CERT_NAME_EXISTS("8"), METHOD_NOT_ALLOWED("9"), MEDIA_NOT_SUPPORTED("10"), MEDIA_NOT_ACCEPTABLE("11"), MISSING_PATH_VARIABLE("12"), MISSING_SERVLET_PARAMETER("13"), TYPE_MISMATCH("14"), MESSAGE_NOT_REPEATABLE("15"), METHOD_ARGUMENT_NOT_VALID("16"), USER_NOT_FOUND("17"), ORDER_VALIDATOR("18"), USER_VALIDATOR("19"), EMPTY_GIFT_CERTIFICATES("20"), ORDER_NOT_FOUNT("21"), PAGINATION("22");

    private final String code;

    ErrorCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
