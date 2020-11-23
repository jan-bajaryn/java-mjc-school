package com.epam.mjc.api.controller.errhandle;

public interface Translator {
    String getString(String msgCode);

    String getString(String msgCode, Object... args);
}
