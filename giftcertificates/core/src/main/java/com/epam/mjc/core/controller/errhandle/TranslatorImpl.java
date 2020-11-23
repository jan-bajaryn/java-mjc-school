package com.epam.mjc.core.controller.errhandle;

import com.epam.mjc.api.controller.errhandle.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TranslatorImpl implements Translator {


    private static final Logger log = LoggerFactory.getLogger(TranslatorImpl.class);
    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public TranslatorImpl(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @Override
    public String getString(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        log.debug("locale = {}", locale);
        return messageSource.getMessage(msgCode, null, locale);
    }

    @Override
    public String getString(String msgCode, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        log.debug("locale = {}", locale);
        log.debug("msgCode = {}", msgCode);
        log.debug("args = {}", args);
        return messageSource.getMessage(msgCode, args, locale);
    }
}