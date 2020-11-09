package com.epam.mjc.core.controller.errhandle;

import com.epam.mjc.api.controller.errhandle.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TranslatorImpl implements Translator {

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public TranslatorImpl(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    public  String getString(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, null, locale);
    }
}